/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.bean;

import com.microcredit.entity.Abono;
import com.microcredit.entity.Credito;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

public class AbonoBean implements Serializable {

    public List<Abono> getAbonos() {
        return null;
    }

    public static void ingresarAbono(EntityManager em, Credito c, BigDecimal montoAbonar, Date fechaAbono) {
        Abono a = new Abono();
        a.setIdCredito(c);
        a.setMonto(montoAbonar);
        a.setFechaAbono(fechaAbono);
        c.getAbonoList().size();//just to instatiate
        c.getAbonoList().add(a);

//        EntityManager em = JPA.getEntityManager();
//        em.getTransaction().begin();
        em.merge(c);
//        em.getTransaction().commit();
//        em.close();
    }

}
