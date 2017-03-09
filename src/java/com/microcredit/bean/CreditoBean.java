package com.microcredit.bean;

import com.microcredit.bll.ClienteService;
import com.microcredit.bll.JPA;
import com.microcredit.bll.Utils;
import com.microcredit.dao.DetalleCredito;
import com.microcredit.entity.Cartera;
import com.microcredit.entity.Cliente;
import com.microcredit.entity.Credito;
import com.microcredit.entity.CreditoEliminado;
import com.microcredit.entity.Ruta;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "creditoView")
@RequestScoped
public class CreditoBean extends DetalleCredito implements Serializable {

    private Short idCartera;
    private List<Credito> creditos;
    private BigDecimal idCredito;
    private Ruta ruta;
    private List<Ruta> rutas;
    private Cliente cliente;
    private BigDecimal montoAbonar;
    private Date fechaAbono;

    @ManagedProperty("#{clienteService}")
    private ClienteService service;

    public CreditoBean() {
    }

    @PostConstruct
    public void init() {
        cargarRutas();
        setCredito(new Credito());
    }

    public static List<Credito> getAllCreditos(Cartera cartera) {
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("Select cre FROM Credito cre JOIN cre.idRuta.idCartera c WHERE c.idCartera = :idCartera");
        query.setParameter("idCartera", cartera.getIdCartera());
        List<Credito> lista = query.getResultList();
        em.close();
        return lista;
    }

    public void ingresarCredito() {
        if (!checkIfExists(getCredito().getIdCredito())) {
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
            limpiar();
        } else {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El numero de tarjeta ingresado ya existe", ""));
        }
    }

    public void cargarCreditos() {
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

    public void eliminarCredito() {
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        Credito c = em.find(Credito.class, idCredito);

        CreditoEliminado ce = new CreditoEliminado();
        ce.setIdCredito(c.getIdCredito());
        ce.setIdCliente(c.getIdCliente().getIdCliente());
        ce.setIdRuta(c.getIdRuta().getIdRuta());
        ce.setMonto(c.getMonto());
        ce.setFechaDesembolso(c.getFechaDesembolso());
        ce.setFechaEliminado(new Date());
        em.persist(ce);
        em.remove(c);
        em.getTransaction().commit();
        em.close();
    }

    public void onRowEdit(RowEditEvent event) {
        DetalleCredito dc = (DetalleCredito) event.getObject();
        updateCredito(dc.getCredito());
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Credito editado", "Tarjeta: " + dc.getCredito().getIdCredito());
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private void updateCredito(Credito c) {
        EntityManager em = JPA.getEntityManager();
        Credito temp = em.find(Credito.class, c.getIdCredito());
        em.getTransaction().begin();
        temp.setFechaDesembolso(c.getFechaDesembolso());
        em.getTransaction().commit();
    }

    public static List<Credito> getCreditosByDate(Cartera cartera, Date fecha) {
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT c FROM Credito c JOIN c.idRuta.idCartera cart "
                + "WHERE cart.idCartera = :idCartera AND c.fechaDesembolso = :fechaDesembolso");
        query.setParameter("idCartera", cartera.getIdCartera());
        query.setParameter("fechaDesembolso", Utils.parsearFecha(fecha));
        List<Credito> creditos = query.getResultList();
        em.close();
        return creditos;
    }

    public void ingresarAbono() {
        cargarCreditoById();
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        AbonoBean.ingresarAbono(em, getCredito(), montoAbonar, fechaAbono);
        em.getTransaction().commit();
        em.close();
        cargarCreditoById();
        montoAbonar = null;
        fechaAbono = null;
        idCredito = null;
    }

    public List<Cliente> completarCliente(String query) {
        List<Cliente> allClientes = service.getClientes();
        List<Cliente> filteredClientes = new ArrayList<>();
        for (Cliente c : allClientes) {
            if ((c.getPrimerNombre() != null && c.getPrimerNombre().toLowerCase().contains(query))
                    || (c.getSegundoNombre() != null && c.getSegundoNombre().toLowerCase().contains(query))
                    || (c.getPrimerApellido() != null && c.getPrimerApellido().toLowerCase().contains(query))
                    || (c.getSegundoApellido() != null && c.getSegundoApellido().toLowerCase().contains(query))) {
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

    public static boolean isNuevoCredito(Date fechaDesembolso, Date fechaCuadre) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(fechaDesembolso);
        c2.setTime(fechaCuadre);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    public void cargarNuevoIdTarjeta() {
        if (ruta != null) {
            EntityManager em = JPA.getEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT max(c.idCredito) FROM Credito c JOIN c.idRuta.idCartera cart "
                    + "WHERE cart.idCartera = :idCartera");
            query.setParameter("idCartera", ruta.getIdCartera().getIdCartera());
            BigDecimal idCred = (BigDecimal) query.getSingleResult();
            if (idCred != null) {
                getCredito().setIdCredito(idCred.add(new BigDecimal(1)));
            }
            em.close();
        }
    }

    private boolean checkIfExists(BigDecimal idCredito) {
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        Credito c = em.find(Credito.class, idCredito);
        em.close();
        return c != null;
    }
    
    public BigDecimal getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(BigDecimal idCredito) {
        this.idCredito = idCredito;
    }

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
