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

/**
 *
 * @author 30178037
 */
public class GastoBean {

    public static void ingresarGasto(Cartera cartera, Date fecha, BigDecimal montoGasto, TipoGasto tipoGasto) {
        Gasto gasto = new Gasto();
        gasto.setIdCartera(cartera);
        gasto.setFechaCreacion(Utils.parsearFecha(fecha));
        gasto.setMonto(montoGasto);
        gasto.setIdTipoGasto(tipoGasto);

        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        em.persist(gasto);
        em.getTransaction().commit();
        em.close();
    }

    public static List<Gasto> getGastosByDate(Date fecha) {
        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        /**
         * Improve CarteraID filter needs to be added and move to GastosBean
         */
        List<Gasto> gastos = em.createNamedQuery("Gasto.findByFechaCreacion", Gasto.class)
                .setParameter("fechaCreacion", Utils.parsearFecha(fecha)).getResultList();
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
