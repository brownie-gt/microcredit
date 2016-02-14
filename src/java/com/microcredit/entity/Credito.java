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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 30178037
 */
@Entity
@Table(name = "CREDITO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Credito.findAll", query = "SELECT c FROM Credito c"),
    @NamedQuery(name = "Credito.findByIdPrestamo", query = "SELECT c FROM Credito c WHERE c.idPrestamo = :idPrestamo"),
    @NamedQuery(name = "Credito.findByMonto", query = "SELECT c FROM Credito c WHERE c.monto = :monto"),
    @NamedQuery(name = "Credito.findByFechaDesembolso", query = "SELECT c FROM Credito c WHERE c.fechaDesembolso = :fechaDesembolso")})
public class Credito implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PRESTAMO")
    private BigDecimal idPrestamo;
    @Basic(optional = false)
    @Column(name = "MONTO")
    private BigInteger monto;
    @Basic(optional = false)
    @Column(name = "FECHA_DESEMBOLSO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDesembolso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPrestamo")
    private List<Mora> moraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPrestamo")
    private List<AbonoPrestamo> abonoPrestamoList;
    @JoinColumn(name = "ID_RUTA", referencedColumnName = "ID_RUTA")
    @ManyToOne(optional = false)
    private Ruta idRuta;
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE")
    @ManyToOne(optional = false)
    private Cliente idCliente;

    public Credito() {
    }

    public Credito(BigDecimal idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Credito(BigDecimal idPrestamo, BigInteger monto, Date fechaDesembolso) {
        this.idPrestamo = idPrestamo;
        this.monto = monto;
        this.fechaDesembolso = fechaDesembolso;
    }

    public BigDecimal getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(BigDecimal idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public BigInteger getMonto() {
        return monto;
    }

    public void setMonto(BigInteger monto) {
        this.monto = monto;
    }

    public Date getFechaDesembolso() {
        return fechaDesembolso;
    }

    public void setFechaDesembolso(Date fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    @XmlTransient
    public List<Mora> getMoraList() {
        return moraList;
    }

    public void setMoraList(List<Mora> moraList) {
        this.moraList = moraList;
    }

    @XmlTransient
    public List<AbonoPrestamo> getAbonoPrestamoList() {
        return abonoPrestamoList;
    }

    public void setAbonoPrestamoList(List<AbonoPrestamo> abonoPrestamoList) {
        this.abonoPrestamoList = abonoPrestamoList;
    }

    public Ruta getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Ruta idRuta) {
        this.idRuta = idRuta;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrestamo != null ? idPrestamo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Credito)) {
            return false;
        }
        Credito other = (Credito) object;
        if ((this.idPrestamo == null && other.idPrestamo != null) || (this.idPrestamo != null && !this.idPrestamo.equals(other.idPrestamo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.microcredit.entity.Credito[ idPrestamo=" + idPrestamo + " ]";
    }
    
}
