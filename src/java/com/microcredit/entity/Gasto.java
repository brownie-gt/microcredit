/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 30178037
 */
@Entity
@Table(name = "GASTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gasto.findAll", query = "SELECT g FROM Gasto g"),
    @NamedQuery(name = "Gasto.findByIdGasto", query = "SELECT g FROM Gasto g WHERE g.idGasto = :idGasto"),
    @NamedQuery(name = "Gasto.findByFechaCreacion", query = "SELECT g FROM Gasto g WHERE g.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Gasto.findByMonto", query = "SELECT g FROM Gasto g WHERE g.monto = :monto")})
public class Gasto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_GASTO")
    private String idGasto;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Basic(optional = false)
    @Column(name = "MONTO")
    private double monto;
    @JoinColumn(name = "ID_TIPO_GASTO", referencedColumnName = "ID_TIPO_GASTO")
    @ManyToOne(optional = false)
    private TipoGasto idTipoGasto;
    @JoinColumn(name = "ID_CARTERA", referencedColumnName = "ID_CARTERA")
    @ManyToOne
    private Cartera idCartera;

    public Gasto() {
    }

    public Gasto(String idGasto) {
        this.idGasto = idGasto;
    }

    public Gasto(String idGasto, Date fechaCreacion, double monto) {
        this.idGasto = idGasto;
        this.fechaCreacion = fechaCreacion;
        this.monto = monto;
    }

    public String getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(String idGasto) {
        this.idGasto = idGasto;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public TipoGasto getIdTipoGasto() {
        return idTipoGasto;
    }

    public void setIdTipoGasto(TipoGasto idTipoGasto) {
        this.idTipoGasto = idTipoGasto;
    }

    public Cartera getIdCartera() {
        return idCartera;
    }

    public void setIdCartera(Cartera idCartera) {
        this.idCartera = idCartera;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGasto != null ? idGasto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gasto)) {
            return false;
        }
        Gasto other = (Gasto) object;
        if ((this.idGasto == null && other.idGasto != null) || (this.idGasto != null && !this.idGasto.equals(other.idGasto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.microcredit.entity.Gasto[ idGasto=" + idGasto + " ]";
    }
    
}
