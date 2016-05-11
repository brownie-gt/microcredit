package com.microcredit.bean;

import com.microcredit.bll.CobroPorRuta;
import com.microcredit.bll.JPA;
import com.microcredit.dao.DetalleCredito;
import com.microcredit.entity.Abono;
import com.microcredit.entity.Cartera;
import com.microcredit.entity.Credito;
import com.microcredit.entity.Cuadre;
import com.microcredit.entity.Gasto;
import com.microcredit.entity.TipoGasto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

@ManagedBean(name = "cuadreView")
@ViewScoped
public class CuadreBean implements Serializable {

//    private static final Logger logger = LoggerFactory.getLogger(CuadreBean.class);
    private List<Abono> abonos;
    private List<DetalleCredito> creditosCuadre;
    private List<DetalleCredito> filteredCreditosCuadre;

    private Cuadre cuadre;
    private Short idCartera;
    private Cartera cartera;

    private BigDecimal faltante;
    private BigDecimal sobrante;
    private BigDecimal totalPrestado;
    private List<CobroPorRuta> cobrosPorRuta;

    private BigDecimal totalGastos;
    private List<Gasto> gastos;
    private List<TipoGasto> listTipoGasto;
    private TipoGasto tipoGastoSeleccionado;

    private List<Gasto> gastosDelDia;
    private BigDecimal montoGasto;
    private String descripcionGasto;

    private List<Credito> creditosSeleccionados;
    private int countCreditosSeleccionados;
    private int countCreditos;

    public CuadreBean() {
        cuadre = new Cuadre();
        gastos = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        listTipoGasto = GastoBean.getTiposDeGasto();
//        cargarCarteraById();
    }

    public void cargarCarteraById() {
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        cartera = em.find(Cartera.class, idCartera);
        em.close();
        if (cartera == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Codigo de cartera invalida."));
        }
    }

    public void cargarCreditos(boolean calcularCobros) {
        creditosCuadre = new ArrayList<>();
        totalPrestado = new BigDecimal(0);
        cuadre.setCobroDia(new BigDecimal(0));
        cuadre.setCobrado(new BigDecimal(0));

        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        List<Credito> creditos = em.createNamedQuery("Credito.findAll", Credito.class).getResultList();
        em.close();

        DetalleCredito dc;

        for (Credito c : creditos) {
            dc = new DetalleCredito();
            dc.setCredito(c);
            dc.calcularDetalleCredito();
            if (calcularCobros && !dc.isPagado()) {
                if (CreditoBean.isNuevoCredito(dc.getCredito().getFechaDesembolso(), cuadre.getFechaCreacion())) {
                    dc.setCuota(new BigDecimal(0));
                }
                creditosCuadre.add(dc);
                cuadre.setCobroDia(cuadre.getCobroDia().add(dc.getCuota()).setScale(0, RoundingMode.HALF_EVEN));
                cuadre.setCobrado(cuadre.getCobrado().add(dc.getCuota()).setScale(0, RoundingMode.HALF_EVEN));
                countCreditos++;
            } else if (!calcularCobros) {
                creditosCuadre.add(dc);
            }
        }
    }

    public void agregarGasto() {
        if (montoGasto != null) {
            Gasto g = new Gasto();
            g.setIdCartera(cartera);
            g.setFechaCreacion(cuadre.getFechaCreacion());
            g.setIdTipoGasto(tipoGastoSeleccionado);
            g.setMonto(montoGasto);
            g.setDescripcion(descripcionGasto);
            if (gastosDelDia == null) {
                gastosDelDia = new ArrayList<>();
            }
            gastosDelDia.add(g);
            montoGasto = null;
            tipoGastoSeleccionado = null;
            descripcionGasto = null;
        }
    }

    private void ingresarGastos(EntityManager em) {
        if (gastosDelDia != null) {
            for (Gasto g : gastosDelDia) {
                GastoBean.ingresarGasto(em, cartera, cuadre.getFechaCreacion(), g.getMonto(), g.getIdTipoGasto());
            }
        }
    }

    private void ingresarAbonos(EntityManager em) {
        for (DetalleCredito dc : creditosCuadre) {
            if (dc.getCuota() != null && dc.getCuota().intValue() > 0) {
                AbonoBean.ingresarAbono(em, dc.getCredito(), dc.getCuota(), cuadre.getFechaCreacion());
            }
        }
    }

    public String ingresarCuadre() {
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        ingresarAbonos(em);
        ingresarGastos(em);
        cuadre.setIdCartera(cartera);
        em.persist(cuadre);
        em.getTransaction().commit();
        em.close();
        return "/index";
    }

    public void calcular() {
        List<Credito> creditos = CreditoBean.getCreditosByDate(cartera, cuadre.getFechaCreacion());
        totalPrestado = new BigDecimal(0);
        calcularCobrado();
        calcularTotalGastos();

        for (Credito c : creditos) {
            totalPrestado = totalPrestado.add(c.getMonto()).setScale(0, RoundingMode.HALF_EVEN);
        }

        calcularEfectivo();
        calcularDiferencia();
    }

    public void calcularEfectivo() {
        BigDecimal ingresos;
        BigDecimal egresos;
        BigDecimal efectivo;

        if (cuadre.getBaseDia() == null) {
            cuadre.setBaseDia(new BigDecimal(0));
        }
        if (cuadre.getMulta() == null) {
            cuadre.setMulta(new BigDecimal(0));
        }

        ingresos = cuadre.getBaseDia().add(cuadre.getCobrado()).setScale(0, RoundingMode.HALF_EVEN);
        ingresos = ingresos.add(cuadre.getMulta()).setScale(0, RoundingMode.HALF_EVEN);
        egresos = totalPrestado.add(totalGastos).setScale(0, RoundingMode.HALF_EVEN);
        efectivo = ingresos.subtract(egresos).setScale(0, RoundingMode.HALF_EVEN);
        cuadre.setEfectivo(efectivo);

    }

    public void calcularDiferencia() {
        faltante = new BigDecimal(0);
        sobrante = new BigDecimal(0);
        if (cuadre.getCobroCobrador() == null) {
            cuadre.setCobroCobrador(new BigDecimal(0));
        }
        if (cuadre.getCobrado().intValue() > cuadre.getCobroCobrador().intValue()) {
            faltante = cuadre.getCobrado().subtract(cuadre.getCobroCobrador()).setScale(0, RoundingMode.HALF_EVEN);
        } else if (cuadre.getCobrado().intValue() < cuadre.getCobroCobrador().intValue()) {
            sobrante = cuadre.getCobroCobrador().subtract(cuadre.getCobrado()).setScale(0, RoundingMode.HALF_EVEN);
        }
    }

    private void calcularCobrado() {
        inicializarCobroPorRutas();
        cuadre.setCobrado(new BigDecimal(0));
        for (DetalleCredito dc : creditosCuadre) {
            if (dc.getCuota() != null && dc.getCuota().intValue() > 0) {
                cuadre.setCobrado(cuadre.getCobrado().add(dc.getCuota()).setScale(0, RoundingMode.HALF_EVEN));
                calcularCobradoPorRuta(dc);
            }
        }
    }

    private void calcularCobradoPorRuta(DetalleCredito dc) {
        if (dc.getCredito().getIdRuta() == null) {
            return;
        }
        int i;
        for (i = 0; i < cobrosPorRuta.size(); i++) {
            if (cobrosPorRuta.get(i).getRuta().getIdRuta().compareTo(dc.getCredito().getIdRuta().getIdRuta()) == 0) {
                cobrosPorRuta.get(i).setCobro(cobrosPorRuta.get(i).getCobro().add(dc.getCuota()).setScale(0, RoundingMode.HALF_EVEN));
                break;
            }
        }
    }

    private void inicializarCobroPorRutas() {
        int i;
        cobrosPorRuta = new ArrayList<>();
        CobroPorRuta o;
        for (i = 0; i < cartera.getRutaList().size(); i++) {
            o = new CobroPorRuta();
            o.setRuta(cartera.getRutaList().get(i));
            o.setCobro(new BigDecimal(0));
            cobrosPorRuta.add(o);
        }
    }

    private void calcularTotalGastos() {
        totalGastos = new BigDecimal(0);
        if (gastosDelDia != null) {
            for (Gasto g : gastosDelDia) {
                totalGastos = totalGastos.add(g.getMonto()).setScale(0, RoundingMode.HALF_EVEN);
            }
        }
    }

    public String onFlowProcess(FlowEvent event) {
        if (event.getNewStep().equalsIgnoreCase("abonos") && event.getOldStep().equalsIgnoreCase("cartera")) {
            if (creditosCuadre == null) {
                cargarCreditos(true);
            }
        } else if (event.getNewStep().equalsIgnoreCase("confirmacion")) {
            calcular();
            calcularDiferencia();
        }
        return event.getNewStep();
    }

    public void onRowSelect(SelectEvent event) {
        countCreditosSeleccionados++;
    }

    public void onRowUnselect(UnselectEvent event) {
        countCreditosSeleccionados--;
    }

    public void onRowEdit(RowEditEvent event) {
//        DetalleCredito creditoEditado = (DetalleCredito) event.getObject();
//        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
//                "Cuota editada", "Tarjeta: " + creditoEditado.getCredito().getIdCredito()
//                + " Cuota: " + creditoEditado.getCuota().toString());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
//        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada", ((DetalleCredito) event.getObject()).getCuota().toString());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<Abono> getAbonos() {
        return abonos;
    }

    public void setAbonos(List<Abono> abonos) {
        this.abonos = abonos;
    }

    public List<DetalleCredito> getCreditosCuadre() {
        return creditosCuadre;
    }

    public void setCreditosCuadre(List<DetalleCredito> creditosCuadre) {
        this.creditosCuadre = creditosCuadre;
    }

    public Cuadre getCuadre() {
        return cuadre;
    }

    public void setCuadre(Cuadre cuadre) {
        this.cuadre = cuadre;
    }

    public Short getIdCartera() {
        return idCartera;
    }

    public void setIdCartera(Short idCartera) {
        this.idCartera = idCartera;
    }

    public Cartera getCartera() {
        return cartera;
    }

    public void setCartera(Cartera cartera) {
        this.cartera = cartera;
    }

    public List<Gasto> getGastosDelDia() {
        return gastosDelDia;
    }

//    public void setGastosDelDia(List<Gasto> gastosDelDia) {
//        this.gastosDelDia = gastosDelDia;
//    }
    public BigDecimal getMontoGasto() {
        return montoGasto;
    }

    public void setMontoGasto(BigDecimal montoGasto) {
        this.montoGasto = montoGasto;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public List<TipoGasto> getListTipoGasto() {
        return listTipoGasto;
    }

    public TipoGasto getTipoGastoSeleccionado() {
        return tipoGastoSeleccionado;
    }

    public void setTipoGastoSeleccionado(TipoGasto tipoGastoSeleccionado) {
        this.tipoGastoSeleccionado = tipoGastoSeleccionado;
    }

    public BigDecimal getFaltante() {
        return faltante;
    }

//    public void setFaltante(BigDecimal faltante) {
//        this.faltante = faltante;
//    }
    public BigDecimal getSobrante() {
        return sobrante;
    }
//
//    public void setSobrante(BigDecimal sobrante) {
//        this.sobrante = sobrante;
//    }

    public BigDecimal getTotalGastos() {
        return totalGastos;
    }

    public BigDecimal getTotalPrestado() {
        return totalPrestado;
    }

    public int getCountCreditos() {
        return countCreditos;
    }

    public void setCountCreditos(int countCreditos) {
        this.countCreditos = countCreditos;
    }

    public List<Credito> getCreditosSeleccionados() {
        return creditosSeleccionados;
    }

    public void setCreditosSeleccionados(List<Credito> creditosSeleccionados) {
        this.creditosSeleccionados = creditosSeleccionados;
    }

    public int getCountCreditosSeleccionados() {
        return countCreditosSeleccionados;
    }

    public void setCountCreditosSeleccionados(int countCreditosSeleccionados) {
        this.countCreditosSeleccionados = countCreditosSeleccionados;
    }

    public List<DetalleCredito> getFilteredCreditosCuadre() {
        return filteredCreditosCuadre;
    }

    public void setFilteredCreditosCuadre(List<DetalleCredito> filteredCreditosCuadre) {
        this.filteredCreditosCuadre = filteredCreditosCuadre;
    }

    public List<CobroPorRuta> getCobrosPorRuta() {
        return cobrosPorRuta;
    }

    public String getDescripcionGasto() {
        return descripcionGasto;
    }

    public void setDescripcionGasto(String descripcionGasto) {
        this.descripcionGasto = descripcionGasto;
    }

}
