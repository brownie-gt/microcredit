/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "MORA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mora.findAll", query = "SELECT m FROM Mora m"),
    @NamedQuery(name = "Mora.findByIdMora", query = "SELECT m FROM Mora m WHERE m.idMora = :idMora"),
    @NamedQuery(name = "Mora.findByMonto", query = "SELECT m FROM Mora m WHERE m.monto = :monto"),
    @NamedQuery(name = "Mora.findByFechaCreacion", query = "SELECT m FROM Mora m WHERE m.fechaCreacion = :fechaCreacion")})
public class Mora implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MORA")
    private BigDecimal idMora;
    @Basic(optional = false)
    @Column(name = "MONTO")
    private short monto;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "ID_CREDITO", referencedColumnName = "ID_CREDITO")
    @ManyToOne(optional = false)
    private Credito idCredito;

    public Mora() {
    }

    public Mora(BigDecimal idMora) {
        this.idMora = idMora;
    }

    public Mora(BigDecimal idMora, short monto, Date fechaCreacion) {
        this.idMora = idMora;
        this.monto = monto;
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getIdMora() {
        return idMora;
    }

    public void setIdMora(BigDecimal idMora) {
        this.idMora = idMora;
    }

    public short getMonto() {
        return monto;
    }

    public void setMonto(short monto) {
        this.monto = monto;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Credito getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(Credito idCredito) {
        this.idCredito = idCredito;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMora != null ? idMora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mora)) {
            return false;
        }
        Mora other = (Mora) object;
        if ((this.idMora == null && other.idMora != null) || (this.idMora != null && !this.idMora.equals(other.idMora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.microcredit.entity.Mora[ idMora=" + idMora + " ]";
    }
    
}
