/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.bean;

import com.microcredit.bll.JPA;
import com.microcredit.bll.Utils;
import com.microcredit.entity.Cartera;
import com.microcredit.entity.Gasto;
import com.microcredit.entity.TipoGasto;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author 30178037
 */
public class GastoBean {

    public static void ingresarGasto(EntityManager em, Cartera cartera, Date fecha, Gasto g) {
        Gasto gasto = new Gasto();
        gasto.setIdCartera(cartera);
        gasto.setFechaCreacion(Utils.parsearFecha(fecha));
        gasto.setMonto(g.getMonto());
        gasto.setIdTipoGasto(g.getIdTipoGasto());
        gasto.setDescripcion(g.getDescripcion());

//        EntityManager em = JPA.getEntityManager();
//        em.getTransaction().begin();
        em.persist(gasto);
//        em.getTransaction().commit();
//        em.close();
    }

    public static List<Gasto> getGastosByDate(Cartera cartera, Date fecha) {
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("Select g FROM Gasto g JOIN g.idCartera c WHERE c.idCartera = :idCartera AND g.fechaCreacion = :fechaCreacion");
        query.setParameter("idCartera", cartera.getIdCartera());
        query.setParameter("fechaCreacion", Utils.parsearFecha(fecha));
        List<Gasto> gastos = query.getResultList();
        em.close();
        return gastos;
    }

    public static List<TipoGasto> getTiposDeGasto() {
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        List<TipoGasto> listTipoGasto = em.createNamedQuery("TipoGasto.findAll", TipoGasto.class).getResultList();
        em.close();
        return listTipoGasto;
    }

}
