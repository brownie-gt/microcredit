/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.bean;

import com.microcredit.bll.JPA;
import com.microcredit.entity.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 30178037
 */
@ManagedBean(name="clienteService", eager = true)
@ApplicationScoped
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
   private List<Cliente> clientes;
    public ClienteService() {
    }
    
 @PostConstruct
    public void init() {
        logger.debug("ClienteService init()");
        clientes = new ArrayList<Cliente>();
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        clientes = em.createNamedQuery("Cliente.findAll", Cliente.class).getResultList();
        em.close();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
    
    
}
