/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.bean;

import com.microcredit.bll.ClienteService;
import com.microcredit.bll.JPA;
import com.microcredit.entity.Cliente;
import java.io.Serializable;
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
    private Cliente cliente = new Cliente();

    private static final Logger logger = LoggerFactory.getLogger(ClienteBean.class);

    public ClienteBean() {
        logger.debug("ClienteBean() - constructor");
    }

    public void ingresarCliente() {
        logger.debug("ingresarCliente()*********");
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        cliente.setFechaCreacion(new Date());
        em.persist(cliente);
        em.getTransaction().commit();
        em.close();
        cliente = new Cliente();
        service.init();
        clientes = null;
    }

    private List<Cliente> clientes;

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

}
