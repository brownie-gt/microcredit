/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.bean;

import com.microcredit.bll.JPA;
import com.microcredit.entity.Abono;
import com.microcredit.entity.Credito;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 30178037
 */
@ManagedBean(name = "abonoView")
@RequestScoped
public class AbonoBean implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(AbonoBean.class);
    private Abono abono;
    private Credito credito;
    private BigDecimal idCredito;
    private BigDecimal abonado;
    private BigDecimal saldoPorPagar;
    private int cuotas;

    public AbonoBean() {
        logger.debug("AbonoBean() -  constructor");
        abono = new Abono();
        credito = new Credito();
    }
    
    public void ingresarAbono() {
        logger.debug("ingresarAbono()");

        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        em.persist(abono);
        em.getTransaction().commit();
        em.close();
    }
    
    public BigDecimal getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(BigDecimal idCredito) {
        this.idCredito = idCredito;
    }

    public Abono getAbono() {
        return abono;
    }

    public void setAbono(Abono abono) {
        this.abono = abono;
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }
    
    
}
