/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "BASE_DIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BaseDia.findAll", query = "SELECT b FROM BaseDia b"),
    @NamedQuery(name = "BaseDia.findByMonto", query = "SELECT b FROM BaseDia b WHERE b.monto = :monto"),
    @NamedQuery(name = "BaseDia.findByFechaCreacion", query = "SELECT b FROM BaseDia b WHERE b.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "BaseDia.findByIdBaseDia", query = "SELECT b FROM BaseDia b WHERE b.idBaseDia = :idBaseDia")})
public class BaseDia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "MONTO")
    private BigInteger monto;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_BASE_DIA")
    private BigDecimal idBaseDia;
    @JoinColumn(name = "ID_CARTERA", referencedColumnName = "ID_CARTERA")
    @ManyToOne(optional = false)
    private Cartera idCartera;

    public BaseDia() {
    }

    public BaseDia(BigDecimal idBaseDia) {
        this.idBaseDia = idBaseDia;
    }

    public BaseDia(BigDecimal idBaseDia, BigInteger monto, Date fechaCreacion) {
        this.idBaseDia = idBaseDia;
        this.monto = monto;
        this.fechaCreacion = fechaCreacion;
    }

    public BigInteger getMonto() {
        return monto;
    }

    public void setMonto(BigInteger monto) {
        this.monto = monto;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getIdBaseDia() {
        return idBaseDia;
    }

    public void setIdBaseDia(BigDecimal idBaseDia) {
        this.idBaseDia = idBaseDia;
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
        hash += (idBaseDia != null ? idBaseDia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BaseDia)) {
            return false;
        }
        BaseDia other = (BaseDia) object;
        if ((this.idBaseDia == null && other.idBaseDia != null) || (this.idBaseDia != null && !this.idBaseDia.equals(other.idBaseDia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.microcredit.entity.BaseDia[ idBaseDia=" + idBaseDia + " ]";
    }
    
}
