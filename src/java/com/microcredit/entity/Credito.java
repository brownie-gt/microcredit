/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigDecimal;
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
    @NamedQuery(name = "Credito.findByIdCredito", query = "SELECT c FROM Credito c WHERE c.idCredito = :idCredito"),
    @NamedQuery(name = "Credito.findByMonto", query = "SELECT c FROM Credito c WHERE c.monto = :monto"),
    @NamedQuery(name = "Credito.findByFechaDesembolso", query = "SELECT c FROM Credito c WHERE c.fechaDesembolso = :fechaDesembolso")})
public class Credito implements Serializable {
    @Basic(optional = false)
    @Column(name = "MONTO")
    private BigDecimal monto;
    @JoinColumn(name = "ID_RUTA", referencedColumnName = "ID_RUTA")
    @ManyToOne(optional = false)
    private Ruta idRuta;
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE")
    @ManyToOne(optional = false)
    private Cliente idCliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCredito")
    private List<Mora> moraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCredito")
    private List<Abono> abonoList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CREDITO")
    private BigDecimal idCredito;
    @Basic(optional = false)
    @Column(name = "FECHA_DESEMBOLSO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDesembolso;
//    @JoinColumn(name = "ID_CARTERA", referencedColumnName = "ID_CARTERA")
//    @ManyToOne(optional = false)
//    private Cartera idCartera;

    public Credito() {
    }

    public Credito(BigDecimal idCredito) {
        this.idCredito = idCredito;
    }

    public Credito(BigDecimal idCredito, BigDecimal monto, Date fechaDesembolso) {
        this.idCredito = idCredito;
        this.monto = monto;
        this.fechaDesembolso = fechaDesembolso;
    }

    public BigDecimal getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(BigDecimal idCredito) {
        this.idCredito = idCredito;
    }


    public Date getFechaDesembolso() {
        return fechaDesembolso;
    }

    public void setFechaDesembolso(Date fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

//    public Cartera getIdCartera() {
//        return idCartera;
//    }
//
//    public void setIdCartera(Cartera idCartera) {
//        this.idCartera = idCartera;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCredito != null ? idCredito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Credito)) {
            return false;
        }
        Credito other = (Credito) object;
        if ((this.idCredito == null && other.idCredito != null) || (this.idCredito != null && !this.idCredito.equals(other.idCredito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.microcredit.entity.Credito[ idCredito=" + idCredito + " ]";
    }

    @XmlTransient
    public List<Mora> getMoraList() {
        return moraList;
    }

    public void setMoraList(List<Mora> moraList) {
        this.moraList = moraList;
    }

    @XmlTransient
    public List<Abono> getAbonoList() {
        return abonoList;
    }

    public void setAbonoList(List<Abono> abonoList) {
        this.abonoList = abonoList;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Ruta getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Ruta idRuta) {
        this.idRuta = idRuta;
    }
    
}
