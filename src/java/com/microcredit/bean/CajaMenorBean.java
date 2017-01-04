/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.bean;

import com.microcredit.bll.JPA;
import com.microcredit.entity.CajaMenor;
import com.microcredit.entity.CajaMenorTipoTransaccion;
import com.microcredit.entity.Cartera;
import com.microcredit.entity.Cuadre;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author a462101
 */
@ManagedBean(name = "cajaMenor")
@ViewScoped
public class CajaMenorBean implements Serializable {

    private BigDecimal saldoActual;
    private BigDecimal saldoFinal;
    private BigDecimal ingreso;
    private BigDecimal egreso;
    private String descripcion;
//    private BigDecimal efectivoUltimoCuadre;

    /**
     * Creates a new instance of CajaMenorBean
     */
    public CajaMenorBean() {
    }

    public void nuevaTransaccion(EntityManager em, Cartera cartera, int idTipoTransaccion, BigDecimal valor, String descripcion, Date fecha) {
        CajaMenor trx = new CajaMenor();
        trx.setIdCartera(cartera);
        trx.setIdTipoTransaccion(new CajaMenorTipoTransaccion(idTipoTransaccion));
        trx.setValor(valor);
        trx.setDescripcion(descripcion);
        trx.setFechaCreacion(fecha);
        em.persist(trx);
    }

    public void nuevoEgreso(Cartera cartera, CajaMenor cm) {

    }

    public void calcularSaldoCajaMenor(EntityManager em, Cartera cartera, BigDecimal base) {
        BigDecimal efectivoUltimoCuadre = getUltimoEfectivo(em, cartera);
        ingreso = new BigDecimal(0);
        egreso = new BigDecimal(0);
        saldoActual = getSaldoActualCajaMenor(em, cartera);
        if (efectivoUltimoCuadre.compareTo(base) == 0) {
            saldoFinal = saldoActual;
        } else if (efectivoUltimoCuadre.compareTo(base) > 0) {
            ingreso = efectivoUltimoCuadre.subtract(base).setScale(0, RoundingMode.HALF_EVEN);
            saldoFinal = saldoActual.add(ingreso).setScale(0, RoundingMode.HALF_EVEN);
        } else {
            egreso = base.subtract(efectivoUltimoCuadre).setScale(0, RoundingMode.HALF_EVEN);
            saldoFinal = saldoActual.subtract(egreso).setScale(0, RoundingMode.HALF_EVEN);
        }
    }

    private BigDecimal getSaldoActualCajaMenor(EntityManager em, Cartera cartera) {
        BigDecimal ingresos = getCajaMenorTotalIngresos(em, cartera);
        BigDecimal egresos = getCajaMenorTotalEgresos(em, cartera);
        BigDecimal saldo = ingresos.subtract(egresos).setScale(0, RoundingMode.HALF_EVEN);
        return saldo;
    }

    private BigDecimal getUltimoEfectivo(EntityManager em, Cartera cartera) {
        Query query = em.createNamedQuery("Cuadre.findLastCuadre", Cuadre.class);
        query.setParameter("idCartera", cartera.getIdCartera());
        query.setMaxResults(1);
        List<Cuadre> cuadreList = query.getResultList();
        if (!cuadreList.isEmpty()) {
            return cuadreList.get(0).getEfectivo();
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal getUltimoEfectivoCuadre(Cartera cartera) {
        EntityManager em = JPA.getEntityManager();
        Query query = em.createNamedQuery("Cuadre.findLastCuadre", Cuadre.class);
        query.setParameter("idCartera", cartera.getIdCartera());
        query.setMaxResults(1);
        List<Cuadre> cuadreList = query.getResultList();
        if (!cuadreList.isEmpty()) {
            return cuadreList.get(0).getEfectivo();
        }
        em.close();
        return BigDecimal.ZERO;
    }

    private BigDecimal getCajaMenorTotalIngresos(EntityManager em, Cartera cartera) {
        Query query = em.createQuery("select sum(cm.valor) FROM CajaMenor cm JOIN cm.idCartera c JOIN cm.idTipoTransaccion tt"
                + " WHERE c.idCartera = :idCartera AND tt.tipo = :tipoTransaccion");
        query.setParameter("idCartera", cartera.getIdCartera());
        query.setParameter("tipoTransaccion", "Ingreso");

        BigDecimal result = (BigDecimal) query.getSingleResult();
        return result != null ? result : BigDecimal.ZERO;
    }

    private BigDecimal getCajaMenorTotalEgresos(EntityManager em, Cartera cartera) {
        Query query = em.createQuery("select sum(cm.valor) FROM CajaMenor cm JOIN cm.idCartera c JOIN cm.idTipoTransaccion tt"
                + " WHERE c.idCartera = :idCartera AND tt.tipo = :tipoTransaccion");
        query.setParameter("idCartera", cartera.getIdCartera());
        query.setParameter("tipoTransaccion", "Egreso");
        BigDecimal result = (BigDecimal) query.getSingleResult();
        return result != null ? result : BigDecimal.ZERO;
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
    }

    public BigDecimal getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(BigDecimal saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public BigDecimal getIngreso() {
        return ingreso;
    }

    public void setIngreso(BigDecimal ingreso) {
        this.ingreso = ingreso;
    }

    public BigDecimal getEgreso() {
        return egreso;
    }

    public void setEgreso(BigDecimal egreso) {
        this.egreso = egreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
