/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.bean;

import com.microcredit.bll.ClienteService;
import com.microcredit.bll.JPA;
import com.microcredit.entity.Cliente;
import com.microcredit.entity.ReferenciaCliente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 30178037
 */
@ManagedBean(name = "cliente")
@RequestScoped
public class ClienteBean implements Serializable {

    @ManagedProperty("#{clienteService}")
    private ClienteService service;
    private List<Cliente> clientes;
    private Cliente cliente = new Cliente();
    private ReferenciaCliente ref1;
    private ReferenciaCliente ref2;

    private static final Logger logger = LoggerFactory.getLogger(ClienteBean.class);

    public ClienteBean() {
        logger.debug("ClienteBean() - constructor");
        ref1 = new ReferenciaCliente();
        ref2 = new ReferenciaCliente();
    }

    public String ingresarCliente() {
        List<ReferenciaCliente> referencias = new ArrayList<>();
        ref1.setIdCliente(cliente);
        ref2.setIdCliente(cliente);
        referencias.add(ref1);
        referencias.add(ref2);

        cliente.setReferenciaClienteList(referencias);
        cliente.setFechaCreacion(new Date());

        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
        em.close();
        cliente = new Cliente();
        service.init();
        clientes = null;
        return "/index";
    }

    public List<Cliente> getClientes() {
        if (clientes == null) {
            clientes = service.getClientes();
        }
        return clientes;
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

}
