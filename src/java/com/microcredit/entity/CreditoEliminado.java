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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author a462101
 */
@Entity
@Table(name = "CREDITO_ELIMINADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CreditoEliminado.findAll", query = "SELECT c FROM CreditoEliminado c"),
    @NamedQuery(name = "CreditoEliminado.findByIdCreditoEliminado", query = "SELECT c FROM CreditoEliminado c WHERE c.idCreditoEliminado = :idCreditoEliminado"),
    @NamedQuery(name = "CreditoEliminado.findByIdCredito", query = "SELECT c FROM CreditoEliminado c WHERE c.idCredito = :idCredito"),
    @NamedQuery(name = "CreditoEliminado.findByFechaEliminado", query = "SELECT c FROM CreditoEliminado c WHERE c.fechaEliminado = :fechaEliminado"),
    @NamedQuery(name = "CreditoEliminado.findByFechaDesembolso", query = "SELECT c FROM CreditoEliminado c WHERE c.fechaDesembolso = :fechaDesembolso"),
    @NamedQuery(name = "CreditoEliminado.findByMonto", query = "SELECT c FROM CreditoEliminado c WHERE c.monto = :monto"),
    @NamedQuery(name = "CreditoEliminado.findByIdCliente", query = "SELECT c FROM CreditoEliminado c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "CreditoEliminado.findByIdRuta", query = "SELECT c FROM CreditoEliminado c WHERE c.idRuta = :idRuta")})
public class CreditoEliminado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(generator = "CREDITO_ELIMINADO_SEQ")
    @SequenceGenerator(name = "CREDITO_ELIMINADO_SEQ", sequenceName = "CREDITO_ELIMINADO_SEQ", allocationSize = 1, initialValue = 1)
    @Column(name = "ID_CREDITO_ELIMINADO")
    private Integer idCreditoEliminado;
    @Column(name = "ID_CREDITO")
    private BigDecimal idCredito;
    @Column(name = "FECHA_ELIMINADO")
    @Temporal(TemporalType.DATE)
    private Date fechaEliminado;
    @Column(name = "FECHA_DESEMBOLSO")
    @Temporal(TemporalType.DATE)
    private Date fechaDesembolso;
    @Column(name = "MONTO")
    private BigDecimal monto;
    @Column(name = "ID_CLIENTE")
    private Short idCliente;
    @Column(name = "ID_RUTA")
    private Short idRuta;

    public CreditoEliminado() {
    }

    public CreditoEliminado(Integer idCreditoEliminado) {
        this.idCreditoEliminado = idCreditoEliminado;
    }

    public Integer getIdCreditoEliminado() {
        return idCreditoEliminado;
    }

    public void setIdCreditoEliminado(Integer idCreditoEliminado) {
        this.idCreditoEliminado = idCreditoEliminado;
    }

    public BigDecimal getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(BigDecimal idCredito) {
        this.idCredito = idCredito;
    }

    public Date getFechaEliminado() {
        return fechaEliminado;
    }

    public void setFechaEliminado(Date fechaEliminado) {
        this.fechaEliminado = fechaEliminado;
    }

    public Date getFechaDesembolso() {
        return fechaDesembolso;
    }

    public void setFechaDesembolso(Date fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Short getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Short idCliente) {
        this.idCliente = idCliente;
    }

    public Short getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Short idRuta) {
        this.idRuta = idRuta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCreditoEliminado != null ? idCreditoEliminado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CreditoEliminado)) {
            return false;
        }
        CreditoEliminado other = (CreditoEliminado) object;
        if ((this.idCreditoEliminado == null && other.idCreditoEliminado != null) || (this.idCreditoEliminado != null && !this.idCreditoEliminado.equals(other.idCreditoEliminado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.microcredit.entity.CreditoEliminado[ idCreditoEliminado=" + idCreditoEliminado + " ]";
    }
    
}
