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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 30178037
 */
@Entity
@Table(name = "ABONO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Abono.findAll", query = "SELECT a FROM Abono a"),
    @NamedQuery(name = "Abono.findByIdAbono", query = "SELECT a FROM Abono a WHERE a.idAbono = :idAbono"),
    @NamedQuery(name = "Abono.findByMonto", query = "SELECT a FROM Abono a WHERE a.monto = :monto"),
    @NamedQuery(name = "Abono.findByFechaAbono", query = "SELECT a FROM Abono a WHERE a.fechaAbono = :fechaAbono")})
@SequenceGenerator(name = "ABONO_SEQ", sequenceName = "ABONO_SEQ", allocationSize = 1)
public class Abono implements Serializable {
    @Basic(optional = false)
    @Column(name = "MONTO")
    private BigDecimal monto;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(generator = "ABONO_SEQ")
    @Column(name = "ID_ABONO")
    private BigDecimal idAbono;
    @Basic(optional = false)
    @Column(name = "FECHA_ABONO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAbono;
    @JoinColumn(name = "ID_CREDITO", referencedColumnName = "ID_CREDITO")
    @ManyToOne(optional = false)
    private Credito idCredito;

    public Abono() {
    }

    public Abono(BigDecimal idAbono) {
        this.idAbono = idAbono;
    }

    public Abono(BigDecimal idAbono, BigDecimal monto, Date fechaAbono) {
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


    public Date getFechaAbono() {
        return fechaAbono;
    }

    public void setFechaAbono(Date fechaAbono) {
        this.fechaAbono = fechaAbono;
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
        hash += (idAbono != null ? idAbono.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Abono)) {
            return false;
        }
        Abono other = (Abono) object;
        if ((this.idAbono == null && other.idAbono != null) || (this.idAbono != null && !this.idAbono.equals(other.idAbono))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.microcredit.entity.Abono[ idAbono=" + idAbono + " ]";
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

}
