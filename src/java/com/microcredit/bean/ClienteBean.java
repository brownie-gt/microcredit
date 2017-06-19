/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.bean;

import com.microcredit.bll.ClienteService;
import com.microcredit.bll.JPA;
import com.microcredit.entity.Cliente;
import com.microcredit.entity.Credito;
import com.microcredit.entity.ReferenciaCliente;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 30178037
 */
@ManagedBean(name = "cliente")
@ViewScoped
public class ClienteBean implements Serializable {

    @ManagedProperty("#{clienteService}")
    private ClienteService service;
    private List<Cliente> clientes;
    private Cliente cliente = new Cliente();
    private ReferenciaCliente ref1;
    private ReferenciaCliente ref2;
    private BigDecimal idCredito;

    private static final Logger logger = LoggerFactory.getLogger(ClienteBean.class);

    public ClienteBean() {
        ref1 = new ReferenciaCliente();
        ref2 = new ReferenciaCliente();
    }

    public void ingresarCliente() {
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        cliente.setFechaCreacion(new Date());
        em.persist(cliente);

        if (ref1 != null && ref1.getTelefono() != null) {
            ref1.setIdCliente(cliente);
            em.persist(ref1);
        }
        if (ref2 != null && ref2.getTelefono() != null) {
            ref1.setIdCliente(cliente);
            em.persist(ref2);
        }

        em.getTransaction().commit();
        em.close();
        limpiar();
        service.init();
    }

    public List<Cliente> getClientes() {
        if (clientes == null) {
            service.init();
            clientes = service.getClientes();
        }
        return clientes;
    }

    public void findClienteByCreditoId() {
        if (idCredito == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ingrese un codigo de tarjeta."));
            return;
        }
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        Credito cre = em.find(Credito.class, idCredito);
        em.close();
        if (cre == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Codigo de tarjeta invalido."));
        } else {
            cliente = cre.getIdCliente();
        }
    }

    public void editarCliente() {
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        Cliente c = em.find(Cliente.class, cliente.getIdCliente());
        c.setPrimerNombre(cliente.getPrimerNombre());
        c.setSegundoNombre(cliente.getSegundoNombre());
        c.setPrimerApellido(cliente.getPrimerApellido());
        c.setSegundoApellido(cliente.getSegundoApellido());
        c.setDpi(cliente.getDpi());
        c.setContador(cliente.getContador());
        c.setTelefono(cliente.getTelefono());
        c.setTipoNegocio(cliente.getTipoNegocio());
        c.setNit(cliente.getNit());
        c.setDireccion(cliente.getDireccion());
        c.setFechaModificacion(new Date());
        em.getTransaction().commit();
        em.close();
        limpiar();
        service.init();
        idCredito = null;
    }

    public void limpiar() {
        cliente = new Cliente();
        ref1 = new ReferenciaCliente();
        ref2 = new ReferenciaCliente();
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ClienteService getService() {
        return service;
    }

    public void setService(ClienteService service) {
        this.service = service;
    }

    public ReferenciaCliente getRef1() {
        return ref1;
    }

    public void setRef1(ReferenciaCliente ref1) {
        this.ref1 = ref1;
    }

    public ReferenciaCliente getRef2() {
        return ref2;
    }

    public void setRef2(ReferenciaCliente ref2) {
        this.ref2 = ref2;
    }

    public BigDecimal getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(BigDecimal idCredito) {
        this.idCredito = idCredito;
    }
}
