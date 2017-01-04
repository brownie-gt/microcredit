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
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

@ManagedBean(name = "gastoView")
@ViewScoped
public class GastoBean implements Serializable {

    private String idCartera;
    private List<Gasto> gastosList;
    private Date fechaInicio;
    private Date fechaFin;
    private List<TipoGasto> listTipoGasto;
    private TipoGasto tipoGastoSeleccionado;

    @PostConstruct
    public void init() {
        listTipoGasto = getTiposDeGasto();
    }

    public static void ingresarGasto(EntityManager em, Cartera cartera, Date fecha, Gasto g) {
        Gasto gasto = new Gasto();
        gasto.setIdCartera(cartera);
        gasto.setFechaCreacion(Utils.parsearFecha(fecha));
        gasto.setMonto(g.getMonto());
        gasto.setIdTipoGasto(g.getIdTipoGasto());
        gasto.setDescripcion(g.getDescripcion());
        em.persist(gasto);
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

    public void cargarGastos() {
        String sql = "Select g FROM Gasto g JOIN g.idCartera c WHERE c.idCartera = :idCartera";
        if (tipoGastoSeleccionado != null) {
            sql += " AND g.idTipoGasto = :tipoGasto";
        }
        if (fechaInicio != null && fechaFin != null) {
            sql += " AND g.fechaCreacion BETWEEN :fechaInicio AND :fechaFin";
        }

        EntityManager em = JPA.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(sql);
        query.setParameter("idCartera", Short.valueOf(idCartera));
        if (tipoGastoSeleccionado != null) {
            query.setParameter("tipoGasto", tipoGastoSeleccionado);
        }
        if (fechaInicio != null && fechaFin != null) {
            query.setParameter("fechaInicio", fechaInicio, TemporalType.DATE);
            query.setParameter("fechaFin", fechaFin, TemporalType.DATE);
        }

        gastosList = query.getResultList();
        em.close();
    }

    public String getTotalGastos() {
        double total = 0;
        if (gastosList != null && gastosList.size() > 0) {
            for (Gasto g : gastosList) {
                total += g.getMonto().doubleValue();
            }
        }
        return new DecimalFormat("###,###").format(total);
    }

    public String getIdCartera() {
        return idCartera;
    }

    public void setIdCartera(String idCartera) {
        this.idCartera = idCartera;
    }

    public List<Gasto> getGastosList() {
        return gastosList;
    }

    public void setGastosList(List<Gasto> gastosList) {
        this.gastosList = gastosList;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<TipoGasto> getListTipoGasto() {
        return listTipoGasto;
    }

    public void setListTipoGasto(List<TipoGasto> listTipoGasto) {
        this.listTipoGasto = listTipoGasto;
    }

    public TipoGasto getTipoGastoSeleccionado() {
        return tipoGastoSeleccionado;
    }

    public void setTipoGastoSeleccionado(TipoGasto tipoGastoSeleccionado) {
        this.tipoGastoSeleccionado = tipoGastoSeleccionado;
    }

}
