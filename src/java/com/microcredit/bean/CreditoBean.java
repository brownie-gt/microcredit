package com.microcredit.bean;

import com.microcredit.bll.JPA;
import com.microcredit.entity.Abono;
import com.microcredit.entity.Cartera;
import com.microcredit.entity.Cliente;
import com.microcredit.entity.Credito;
import com.microcredit.entity.Mora;
import com.microcredit.entity.Ruta;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "creditoView")
@SessionScoped
public class CreditoBean implements Serializable {

    private Credito credito;
    private BigDecimal idCredito;
    private BigDecimal monto;
    private final BigDecimal tasa;
    private BigDecimal interes;
    private BigDecimal montoAPagar;
    private List<Mora> moraList;
    private Ruta idRuta;
    private Cliente cliente;

    private List<Abono> abonoList;
    private int abonoCount;
    private BigDecimal abonado;
    private BigDecimal saldoPorPagar;

    @ManagedProperty("#{clienteService}")
    private ClienteService service;
    @ManagedProperty("#{creditoService}")
    private CreditoService creditoService;
    private static final Logger logger = LoggerFactory.getLogger(CreditoBean.class);

    public CreditoBean() {
        logger.debug("CreditoBean()");
        this.tasa = new BigDecimal(0.15);
        this.credito = new Credito();
    }

    public void ingresarCredito() {
        logger.debug("ingresarCredito()");
        Cartera cartera = new Cartera();
        cartera.setIdCartera(Short.valueOf("1"));
        Credito c = new Credito();
//        c.setIdCredito(idCredito);
//        c.setIdCartera(cartera);
//        c.setIdCliente(cliente);
//        c.setMonto(monto);
//        c.setFechaDesembolso(fechaDesembolso);

        c.setIdCredito(credito.getIdCredito());
        c.setIdCartera(cartera);
        c.setIdCliente(cliente);
        c.setMonto(credito.getIdCredito());
        c.setFechaDesembolso(credito.getFechaDesembolso());

        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        em.close();

        creditoService.init();
    }

    public void calcularMontoAPagar() {
        if (monto != null && monto.intValue() > 0) {
            interes = monto.multiply(tasa).setScale(0, RoundingMode.HALF_EVEN);
            montoAPagar = monto.add(interes).setScale(0, RoundingMode.HALF_EVEN);
            logger.debug("monto: " + monto);
            logger.debug("interes: " + interes);
            logger.debug("montoPagar: " + montoAPagar);
        }
    }

    public void creditoById() {
        logger.debug("creditoById()");
        logger.debug("idCredito: " + idCredito);
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        credito = em.find(Credito.class, idCredito);
        logger.debug("c.getAbonoList().size(): " + credito.getAbonoList().size());
        logger.debug("c.getAbonoList(): " + credito.getAbonoList());
        em.close();
        abonoCount = credito.getAbonoList().size();
        for(Abono a : credito.getAbonoList()){
        }
    }

    public List<Cliente> completarCliente(String query) {
        List<Cliente> allClientes = service.getClientes();
        List<Cliente> filteredClientes = new ArrayList<>();
        for (Cliente cliente : allClientes) {
            if ((cliente.getPrimerNombre() != null && cliente.getPrimerNombre().toLowerCase().contains(query)
                    || cliente.getPrimerApellido().toLowerCase().contains(query))
                    || cliente.getIdCliente().toString().startsWith(query)) {
                filteredClientes.add(cliente);
            }
        }
        return filteredClientes;
    }

    public List<Credito> completarCredito(String query) {
        List<Credito> allCreditos = creditoService.getCreditos();
        List<Credito> filteredCreditos = new ArrayList<>();
        for (Credito c : allCreditos) {
            if (c.getIdCredito().toString().startsWith(query)) {
                filteredCreditos.add(c);
            }
        }
        return filteredCreditos;
    }

    public BigDecimal getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(BigDecimal idCredito) {
        this.idCredito = idCredito;
    }

    public List<Mora> getMoraList() {
        return moraList;
    }

    public void setMoraList(List<Mora> moraList) {
        this.moraList = moraList;
    }

    public Ruta getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Ruta idRuta) {
        this.idRuta = idRuta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Abono> getAbonoList() {
        return abonoList;
    }

    public void setAbonoList(List<Abono> abonoList) {
        this.abonoList = abonoList;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public BigDecimal getMontoAPagar() {
        return montoAPagar;
    }

    public void setMontoAPagar(BigDecimal montoAPagar) {
        this.montoAPagar = montoAPagar;
    }

    public void setService(ClienteService service) {
        this.service = service;
    }

    public void setCreditoService(CreditoService creditoService) {
        this.creditoService = creditoService;
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    public int getAbonoCount() {
        return abonoCount;
    }

    public void setAbonoCount(int abonoCount) {
        this.abonoCount = abonoCount;
    }

    public BigDecimal getAbonado() {
        return abonado;
    }

    public void setAbonado(BigDecimal abonado) {
        this.abonado = abonado;
    }

    public BigDecimal getSaldoPorPagar() {
        return saldoPorPagar;
    }

    public void setSaldoPorPagar(BigDecimal saldoPorPagar) {
        this.saldoPorPagar = saldoPorPagar;
    }

}
