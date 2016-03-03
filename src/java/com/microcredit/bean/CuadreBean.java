package com.microcredit.bean;

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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "cuadreView")
@ViewScoped
public class CuadreBean implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(CuadreBean.class);
    private List<Abono> abonos;
    private List<DetalleCredito> creditosCuadre;

    private Cuadre cuadre;
    private Short idCartera;
    private Cartera cartera;

    private BigDecimal faltante;
    private BigDecimal sobrante;
    private BigDecimal totalPrestado;

    private BigDecimal totalGastos;
    private List<Gasto> gastos;
    private List<TipoGasto> listTipoGasto;
    private TipoGasto tipoGastoSeleccionado;

    private List<Gasto> gastosDelDia;
    private BigDecimal montoGasto;

    public CuadreBean() {
        cuadre = new Cuadre();
        gastos = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        listTipoGasto = GastoBean.getTiposDeGasto();
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
                creditosCuadre.add(dc);
                cuadre.setCobroDia(cuadre.getCobroDia().add(dc.getCuota()).setScale(0, RoundingMode.HALF_EVEN));
                cuadre.setCobrado(cuadre.getCobrado().add(dc.getCuota()).setScale(0, RoundingMode.HALF_EVEN));
            } else if (!calcularCobros) {
                creditosCuadre.add(dc);
            }
        }
    }

    public void cargarGastosDelDia() {
        gastosDelDia = GastoBean.getGastosByDate(cartera, cuadre.getFechaCreacion());
    }

    public void agregarGasto() {
        Gasto g = new Gasto();
        g.setIdCartera(cartera);
        g.setFechaCreacion(cuadre.getFechaCreacion());
        g.setIdTipoGasto(tipoGastoSeleccionado);
        g.setMonto(montoGasto);
        if (gastosDelDia == null) {
            gastosDelDia = new ArrayList<>();
        }
        gastosDelDia.add(g);
        montoGasto = null;
        tipoGastoSeleccionado = null;
    }

    private void ingresarGastos() {
        for (Gasto g : gastosDelDia) {
            GastoBean.ingresarGasto(cartera, cuadre.getFechaCreacion(), g.getMonto(), g.getIdTipoGasto());
        }
    }

    private void ingresarAbonos() {
        for (DetalleCredito dc : creditosCuadre) {
            if (dc.getCuota().intValue() > 0) {
                AbonoBean.ingresarAbono(dc.getCredito(), dc.getCuota(), cuadre.getFechaCreacion());
            }
        }
    }

    public String ingresarCuadre() {
        ingresarAbonos();
        ingresarGastos();
        cuadre.setIdCartera(cartera);
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        em.persist(cuadre);
        em.getTransaction().commit();
        return "/index";
    }

    public void calcular() {
        List<Credito> creditos = CreditoBean.getCreditosByDate(cartera, cuadre.getFechaCreacion());
        totalPrestado = new BigDecimal(0);
        BigDecimal ingresos;
        BigDecimal egresos;
        BigDecimal efectivo;

        calcularCobrado();
        calcularTotalGastos();

        for (Credito c : creditos) {
            totalPrestado = totalPrestado.add(c.getMonto()).setScale(0, RoundingMode.HALF_EVEN);
        }

        ingresos = cuadre.getBaseDia().add(cuadre.getCobrado()).setScale(0, RoundingMode.HALF_EVEN);
        egresos = totalPrestado.add(totalGastos).setScale(0, RoundingMode.HALF_EVEN);
        efectivo = ingresos.subtract(egresos).setScale(0, RoundingMode.HALF_EVEN);
        cuadre.setEfectivo(efectivo);
        calcularDiferencia();
    }

    public void calcularDiferencia() {
        faltante = new BigDecimal(0);
        sobrante = new BigDecimal(0);
        if (cuadre.getCobrado().intValue() > cuadre.getCobroCobrador().intValue()) {
            faltante = cuadre.getCobrado().subtract(cuadre.getCobroCobrador()).setScale(0, RoundingMode.HALF_EVEN);
        } else if (cuadre.getCobrado().intValue() < cuadre.getCobroCobrador().intValue()) {
            sobrante = cuadre.getCobroCobrador().subtract(cuadre.getCobrado()).setScale(0, RoundingMode.HALF_EVEN);
        }
    }

    private void calcularCobrado() {
        cuadre.setCobrado(new BigDecimal(0));
        for (DetalleCredito dc : creditosCuadre) {
            cuadre.setCobrado(cuadre.getCobrado().add(dc.getCuota()).setScale(0, RoundingMode.HALF_EVEN));
        }
    }

    private void calcularTotalGastos() {
        totalGastos = new BigDecimal(0);
        for (Gasto g : gastosDelDia) {
            totalGastos = totalGastos.add(g.getMonto()).setScale(0, RoundingMode.HALF_EVEN);
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

    public void onRowEdit(RowEditEvent event) {
        DetalleCredito creditoEditado = (DetalleCredito) event.getObject();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Cuota editada", "Tarjeta: " + creditoEditado.getCredito().getIdCredito()
                + " Cuota: " + creditoEditado.getCuota().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada", ((DetalleCredito) event.getObject()).getCuota().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
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

}
