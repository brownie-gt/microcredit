package com.microcredit.bean;

import com.microcredit.bll.ClienteService;
import com.microcredit.bll.JPA;
import com.microcredit.bll.Utils;
import com.microcredit.dao.DetalleCredito;
import com.microcredit.entity.Cartera;
import com.microcredit.entity.Cliente;
import com.microcredit.entity.Credito;
import com.microcredit.entity.Ruta;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "creditoView")
@RequestScoped
public class CreditoBean extends DetalleCredito implements Serializable {

    private Short idCartera;
    private List<Credito> creditos;
    private BigDecimal idCredito;
//    private List<Mora> moraList;
    private Ruta ruta;
    private List<Ruta> rutas;
    private Cliente cliente;
    private BigDecimal montoAbonar;
    private Date fechaAbono;

    @ManagedProperty("#{clienteService}")
    private ClienteService service;

    private static final Logger logger = LoggerFactory.getLogger(CreditoBean.class);

    public CreditoBean() {
        logger.debug("CreditoBean()");
    }

    @PostConstruct
    public void init() {
        cargarRutas();
        setCredito(new Credito());
    }

    public void ingresarCredito() {
        logger.debug("ingresarCredito()");

        Credito c = new Credito();
        c.setIdCredito(getCredito().getIdCredito());
        c.setIdCliente(cliente);
        c.setIdRuta(ruta);
        c.setMonto(getCredito().getMonto());
        c.setFechaDesembolso(Utils.parsearFecha(getCredito().getFechaDesembolso()));
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        em.close();
//        creditoService.init();

        limpiar();
    }
    
    public void cargarCreditos(){
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        creditos = em.createNamedQuery("Credito.findAll", Credito.class).getResultList();
        em.close();
    }

    @Override
    public void limpiar() {
        super.limpiar();
        cliente = null;
        ruta = null;
        montoAbonar = null;
        idCredito = null;
    }

    public void cargarCreditoById() {
        logger.debug("cargarCredito()");
        logger.debug("idCredito: " + idCredito);

        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        Credito c = em.find(Credito.class, idCredito);
        em.close();
        if (c != null) {
            setCredito(c);
            calcularDetalleCredito();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Credito invalido."));
        }
    }

    public static List<Credito> getCreditosByDate(Cartera cartera, Date fecha) {
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        /**
         * Improve CarteraID filter needs to be added
         */
        List<Credito> result = em.createNamedQuery("Credito.findByFechaDesembolso", Credito.class).
                setParameter("fechaDesembolso", Utils.parsearFecha(fecha)).getResultList();
        em.close();
        List<Credito> creditos = new ArrayList<>();
        for (Credito c : result) {
            if (c.getIdRuta().getIdCartera().getIdCartera().compareTo(cartera.getIdCartera()) == 0) {
                creditos.add(c);
            }
        }
        return creditos;
    }

    public void ingresarAbono() {
        cargarCreditoById();
        AbonoBean.ingresarAbono(getCredito(), montoAbonar, fechaAbono);
        cargarCreditoById();
        montoAbonar = null;
        fechaAbono = null;
    }

    public List<Cliente> completarCliente(String query) {
        List<Cliente> allClientes = service.getClientes();
        List<Cliente> filteredClientes = new ArrayList<>();
        for (Cliente c : allClientes) {
            if ((c.getPrimerNombre() != null && c.getPrimerNombre().toLowerCase().contains(query))
                    || (c.getPrimerApellido() != null && c.getPrimerApellido().toLowerCase().contains(query))
                    || c.getIdCliente().toString().startsWith(query)) {
                filteredClientes.add(c);
            }
        }
        return filteredClientes;
    }

    public List<Ruta> completarRuta(String query) {
        List<Ruta> filteredRutas = new ArrayList<>();
        for (Ruta r : rutas) {
            if (r.getNombre() != null && r.getNombre().toLowerCase().contains(query)) {
                filteredRutas.add(r);
            }
        }
        return filteredRutas;
    }

    private void cargarRutas() {
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        rutas = em.createNamedQuery("Ruta.findAll", Ruta.class).getResultList();
        em.close();
    }

    public BigDecimal getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(BigDecimal idCredito) {
        this.idCredito = idCredito;
    }

//    public List<Mora> getMoraList() {
//        return moraList;
//    }
//
//    public void setMoraList(List<Mora> moraList) {
//        this.moraList = moraList;
//    }
    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setService(ClienteService service) {
        this.service = service;
    }

    public BigDecimal getMontoAbonar() {
        return montoAbonar;
    }

    public void setMontoAbonar(BigDecimal montoAbonar) {
        this.montoAbonar = montoAbonar;
    }

    public List<Ruta> getRutas() {
        return rutas;
    }

    public void setRutas(List<Ruta> rutas) {
        this.rutas = rutas;
    }

    public Date getFechaAbono() {
        return fechaAbono;
    }

    public void setFechaAbono(Date fechaAbono) {
        this.fechaAbono = fechaAbono;
    }

    public Short getIdCartera() {
        return idCartera;
    }

    public void setIdCartera(Short idCartera) {
        this.idCartera = idCartera;
    }

    public List<Credito> getCreditos() {
        return creditos;
    }

    public void setCreditos(List<Credito> creditos) {
        this.creditos = creditos;
    }
}
