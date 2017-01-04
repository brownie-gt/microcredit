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
 * @author a462101
 */
@Entity
@Table(name = "CAJA_MENOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CajaMenor.findAll", query = "SELECT c FROM CajaMenor c")})
public class CajaMenor implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "CAJA_MENOR_SEQ", sequenceName = "CAJA_MENOR_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "CAJA_MENOR_SEQ")
    @Column(name = "ID_CAJA_MENOR")
    private int idCajaMenor;

    @Basic(optional = false)
    @Column(name = "VALOR")
    private BigDecimal valor;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Basic(optional = false)
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @JoinColumn(name = "ID_TIPO_TRANSACCION", referencedColumnName = "ID_TIPO")
    @ManyToOne(optional = false)
    private CajaMenorTipoTransaccion idTipoTransaccion;

    @JoinColumn(name = "ID_CARTERA", referencedColumnName = "ID_CARTERA")
    @ManyToOne
    private Cartera idCartera;

    public CajaMenor() {
    }

    public CajaMenor(int idCajaMenor) {
        this.idCajaMenor = idCajaMenor;
    }

    public CajaMenor(int idCajaMenor, BigDecimal valor, Date fechaCreacion) {
        this.idCajaMenor = idCajaMenor;
        this.valor = valor;
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdCajaMenor() {
        return idCajaMenor;
    }

    public void setIdCajaMenor(int idCajaMenor) {
        this.idCajaMenor = idCajaMenor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public CajaMenorTipoTransaccion getIdTipoTransaccion() {
        return idTipoTransaccion;
    }

    public void setIdTipoTransaccion(CajaMenorTipoTransaccion idTipoTransaccion) {
        this.idTipoTransaccion = idTipoTransaccion;
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
        hash += (int) idCajaMenor;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CajaMenor)) {
            return false;
        }
        CajaMenor other = (CajaMenor) object;
        if (this.idCajaMenor != other.idCajaMenor) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.microcredit.entity.CajaMenor[ idCajaMenor=" + idCajaMenor + " ]";
    }

}
