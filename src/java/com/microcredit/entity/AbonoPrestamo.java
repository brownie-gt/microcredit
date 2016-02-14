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
@Table(name = "ABONO_PRESTAMO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AbonoPrestamo.findAll", query = "SELECT a FROM AbonoPrestamo a"),
    @NamedQuery(name = "AbonoPrestamo.findByIdAbono", query = "SELECT a FROM AbonoPrestamo a WHERE a.idAbono = :idAbono"),
    @NamedQuery(name = "AbonoPrestamo.findByMonto", query = "SELECT a FROM AbonoPrestamo a WHERE a.monto = :monto"),
    @NamedQuery(name = "AbonoPrestamo.findByFechaAbono", query = "SELECT a FROM AbonoPrestamo a WHERE a.fechaAbono = :fechaAbono")})
public class AbonoPrestamo implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ABONO")
    private BigDecimal idAbono;
    @Basic(optional = false)
    @Column(name = "MONTO")
    private BigInteger monto;
    @Basic(optional = false)
    @Column(name = "FECHA_ABONO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAbono;
    @JoinColumn(name = "ID_PRESTAMO", referencedColumnName = "ID_PRESTAMO")
    @ManyToOne(optional = false)
    private Credito idPrestamo;

    public AbonoPrestamo() {
    }

    public AbonoPrestamo(BigDecimal idAbono) {
        this.idAbono = idAbono;
    }

    public AbonoPrestamo(BigDecimal idAbono, BigInteger monto, Date fechaAbono) {
        this.idAbono = idAbono;
        this.monto = monto;
        this.fechaAbono = fechaAbono;
    }

    public BigDecimal getIdAbono() {
        return idAbono;
    }

    public void setIdAbono(BigDecimal idAbono) {
        this.idAbono = idAbono;
    }

    public BigInteger getMonto() {
        return monto;
    }

    public void setMonto(BigInteger monto) {
        this.monto = monto;
    }

    public Date getFechaAbono() {
        return fechaAbono;
    }

    public void setFechaAbono(Date fechaAbono) {
        this.fechaAbono = fechaAbono;
    }

    public Credito getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Credito idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAbono != null ? idAbono.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AbonoPrestamo)) {
            return false;
        }
        AbonoPrestamo other = (AbonoPrestamo) object;
        if ((this.idAbono == null && other.idAbono != null) || (this.idAbono != null && !this.idAbono.equals(other.idAbono))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.microcredit.entity.AbonoPrestamo[ idAbono=" + idAbono + " ]";
    }
    
}
