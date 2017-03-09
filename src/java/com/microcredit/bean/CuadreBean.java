package com.microcredit.bean;

import com.microcredit.bll.CobroPorRuta;
import com.microcredit.bll.JPA;
import com.microcredit.bll.SendMail;
import com.microcredit.dao.DetalleCredito;
import com.microcredit.entity.Abono;
import com.microcredit.entity.CajaMenorTipoTransaccion;
import com.microcredit.entity.Cartera;
import com.microcredit.entity.Credito;
import com.microcredit.entity.Cuadre;
import com.microcredit.entity.Gasto;
import com.microcredit.entity.TipoGasto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private BigDecimal saldoCajaMenor;

    private BigDecimal totalGastos;
//    private List<Gasto> gastos;
    private Gasto gastoSeleccionado;
    private List<TipoGasto> listTipoGasto;
    private TipoGasto tipoGastoSeleccionado;

    private List<Gasto> gastosDelDia;
    private BigDecimal montoGasto;
    private String descripcionGasto;

    private String filterIdCredito;
    private List<DetalleCredito> creditosSeleccionados;
    private int countCreditosSeleccionados;
    private int countCreditos;

    private boolean incluirCuentasPorCobrar;
    
    private static final Logger logger = LoggerFactory.getLogger(CreditoBean.class);

    public CuadreBean() {
        cuadre = new Cuadre();
//        gastos = new ArrayList<>();
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

        List<Credito> creditos = CreditoBean.getAllCreditos(cartera);

        DetalleCredito dc;

        for (Credito c : creditos) {
            dc = new DetalleCredito();
            dc.setCredito(c);
            dc.calcularDetalleCredito();
            if (calcularCobros && !dc.isPagado()) {
                if (CreditoBean.isNuevoCredito(dc.getCredito().getFechaDesembolso(), cuadre.getFechaCuadre())) {
                    dc.setCuota(new BigDecimal(0));
                }
                creditosCuadre.add(dc);
                cuadre.setCobroDia(cuadre.getCobroDia().add(dc.getCuota()).setScale(0, RoundingMode.HALF_EVEN));
                cuadre.setCobrado(cuadre.getCobrado().add(dc.getCuota()).setScale(0, RoundingMode.HALF_EVEN));
                countCreditos++;
            } else if (!calcularCobros) {
                if (!incluirCuentasPorCobrar && dc.isPagado()) {
                    creditosCuadre.add(dc);
                    countCreditos++;
                } else if (incluirCuentasPorCobrar) {
                    creditosCuadre.add(dc);
                    countCreditos++;
                }
            }
        }
    }

    private void ingresarGastos(EntityManager em) {
        if (gastosDelDia != null) {
            for (Gasto g : gastosDelDia) {
                GastoBean.ingresarGasto(em, cartera, cuadre.getFechaCuadre(), g);
            }
        }
    }

    private void ingresarAbonos(EntityManager em) {
        for (DetalleCredito dc : creditosCuadre) {
            if (dc.getCuota() != null && dc.getCuota().intValue() > 0) {
                AbonoBean.ingresarAbono(em, dc.getCredito(), dc.getCuota(), cuadre.getFechaCuadre());
            }
        }
    }

    private void ingresarCajaMenor(EntityManager em) {
        if (cajaMenor.getIngreso() != null && cajaMenor.getIngreso().intValue() > 0) {
            cajaMenor.nuevaTransaccion(em, cartera, CajaMenorTipoTransaccion.INGRESO_EFECTIVO_CUADRE,
                    cajaMenor.getIngreso(), cajaMenor.getDescripcion(), cuadre.getFechaCuadre());
        } else if (cajaMenor.getEgreso() != null && cajaMenor.getEgreso().intValue() > 0) {
            cajaMenor.nuevaTransaccion(em, cartera, CajaMenorTipoTransaccion.EGRESO_BASE_CUADRE,
                    cajaMenor.getEgreso(), cajaMenor.getDescripcion(), cuadre.getFechaCuadre());
        }
    }

    public String ingresarCuadre() {
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        ingresarGastos(em);
        ingresarCajaMenor(em);
        ingresarAbonos(em);
        cuadre.setIdCartera(cartera);
        em.persist(cuadre);
        em.getTransaction().commit();
        em.close();
        new SendMail().sendCuadre(this);
        return "/index";
    }

    public void calcular() {
        List<Credito> creditos = CreditoBean.getCreditosByDate(cartera, cuadre.getFechaCuadre());
        totalPrestado = new BigDecimal(0);
        calcularCobrado();
        calcularTotalGastos();

        for (Credito c : creditos) {
            totalPrestado = totalPrestado.add(c.getMonto()).setScale(0, RoundingMode.HALF_EVEN);
        }

        calcularEfectivo();
        calcularDiferencia();
        calcularSaldoCajaMenor();
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

    public void marcarAbono() {
        if (filterIdCredito != null && filterIdCredito.length() > 0) {
            try {
                for (DetalleCredito dc : creditosCuadre) {
                    if (dc.getCredito().getIdCredito().intValue() == Integer.valueOf(filterIdCredito)) {
                        getCreditosSeleccionados().add(dc);
                        filterIdCredito = "";
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                logger.warn("Id filtro incorrecto", e);
            }
        }
    }

    public String onFlowProcess(FlowEvent event) {
        if (event.getOldStep().equalsIgnoreCase("tabGastos") && event.getNewStep().equalsIgnoreCase("tabAbonos")) {
            if (creditosCuadre == null) {
                cargarCreditos(true);
            }
        } else if (event.getOldStep().equalsIgnoreCase("tabAbonos") && event.getNewStep().equalsIgnoreCase("tabResumen")) {
            if (cuadre.getBaseDia() == null) {
                cuadre.setBaseDia(CajaMenorBean.getUltimoEfectivoCuadre(cartera));
            }
            calcular();
        } else if (event.getNewStep().equalsIgnoreCase("tabConfirmacion")) {
            calcular();
        }
        return event.getNewStep();
    }

    @ManagedProperty(value = "#{cajaMenor}")
    public CajaMenorBean cajaMenor;

    public void calcularSaldoCajaMenor() {
        if (cuadre.getBaseDia() != null) {
            EntityManager em = JPA.getEntityManager();
            em.getTransaction().begin();
            cajaMenor.calcularSaldoCajaMenor(em, cartera, cuadre.getBaseDia());
            em.close();
        }
    }
    
    public String getTotalPrestamos() {
        int total = 0;
        if (creditosCuadre != null && creditosCuadre.size() > 0) {
            for (DetalleCredito credito : creditosCuadre) {
                total += credito.getCredito().getMonto().intValue();
            }
        }
        return new DecimalFormat("###,###").format(total);
    }

    public String getTotalInteres() {
        int total = 0;
        if (creditosCuadre != null && creditosCuadre.size() > 0) {
            for (DetalleCredito credito : creditosCuadre) {
                total += credito.getInteres().intValue();
            }
        }
        return new DecimalFormat("###,###").format(total);
    }

    public String getTotalAbonado() {
        int total = 0;
        if (creditosCuadre != null && creditosCuadre.size() > 0) {
            for (DetalleCredito credito : creditosCuadre) {
                total += credito.getAbonado().intValue();
            }
        }
        return new DecimalFormat("###,###").format(total);
    }

    public String getTotalPorCobrar() {
        int total = 0;
        if (creditosCuadre != null && creditosCuadre.size() > 0) {
            for (DetalleCredito credito : creditosCuadre) {
                total += credito.getSaldoPorPagar().intValue();
            }
        }
        return new DecimalFormat("###,###").format(total);
    }

    public void agregarGasto() {
        if (montoGasto != null || tipoGastoSeleccionado != null) {
            Gasto g = new Gasto();
            g.setIdCartera(cartera);
            g.setFechaCreacion(cuadre.getFechaCuadre());
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

    public void borrarGastos() {
        gastosDelDia = null;
    }

    public void onRowSelect(SelectEvent event) {
//        countCreditosSeleccionados++;
    }

    public void onRowUnselect(UnselectEvent event) {
//        countCreditosSeleccionados--;
    }

    public void onRowEdit(RowEditEvent event) {
    }

    public void onRowCancel(RowEditEvent event) {
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

//    public List<Gasto> getGastos() {
//        return gastos;
//    }
//
//    public void setGastos(List<Gasto> gastos) {
//        this.gastos = gastos;
//    }
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

    public List<DetalleCredito> getCreditosSeleccionados() {
        return creditosSeleccionados;
    }

    public void setCreditosSeleccionados(List<DetalleCredito> creditosSeleccionados) {
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

    public String getFilterIdCredito() {
        return filterIdCredito;
    }

    public void setFilterIdCredito(String filterIdCredito) {
        this.filterIdCredito = filterIdCredito;
    }

    public boolean isIncluirCuentasPorCobrar() {
        return incluirCuentasPorCobrar;
    }

    public void setIncluirCuentasPorCobrar(boolean incluirCuentasPorCobrar) {
        this.incluirCuentasPorCobrar = incluirCuentasPorCobrar;
    }

    public BigDecimal getSaldoCajaMenor() {
        return saldoCajaMenor;
    }

    public void setSaldoCajaMenor(BigDecimal saldoCajaMenor) {
        this.saldoCajaMenor = saldoCajaMenor;
    }

    public void setCajaMenor(CajaMenorBean cajaMenor) {
        this.cajaMenor = cajaMenor;
    }

    public Gasto getGastoSeleccionado() {
        return gastoSeleccionado;
    }

    public void setGastoSeleccionado(Gasto gastoSeleccionado) {
        this.gastoSeleccionado = gastoSeleccionado;
    }

    public BigDecimal getPorcentajeCumplimiento() {
        return (cuadre.getCobrado().divide(cuadre.getCobroDia(),2, RoundingMode.HALF_UP)).multiply(new BigDecimal(100));
    }   
    
}
