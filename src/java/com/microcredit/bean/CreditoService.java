/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.bean;

import com.microcredit.bll.JPA;
import com.microcredit.entity.Cliente;
import com.microcredit.entity.Credito;
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
@ManagedBean(name = "creditoService", eager = true)
@ApplicationScoped
public class CreditoService {

    private static final Logger logger = LoggerFactory.getLogger(CreditoService.class);
    private List<Credito> creditos;

    public CreditoService() {
    }

    @PostConstruct
    public void init() {
        logger.debug("CreditoService init()");
        creditos = new ArrayList<Credito>();
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        creditos = em.createNamedQuery("Credito.findAll", Credito.class).getResultList();
        em.close();
    }

    public List<Credito> getCreditos() {
        return creditos;
    }

}
