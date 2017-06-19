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
import javax.persistence.FetchType;
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

/**
 *
 * @author a462101
 */
@Entity
@Table(name = "CREDITO_FALLA")
@NamedQueries({
    @NamedQuery(name = "CreditoFalla.findAll", query = "SELECT c FROM CreditoFalla c")})
public class CreditoFalla implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "CREDITO_FALLA_SEQ", sequenceName = "CREDITO_FALLA_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "CREDITO_FALLA_SEQ")
    @Column(name = "ID_FALLA")
    private BigDecimal idFalla;

    @Column(name = "CUOTA")
    private BigDecimal cuota;

    @Basic(optional = false)
    @Column(name = "FECHA_FALLA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFalla;

    @Basic(optional = false)
    @Column(name = "FECHA_CREACION", insertable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @JoinColumn(name = "ID_CREDITO_FK", referencedColumnName = "ID_CREDITO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Credito idCreditoFk;

    public CreditoFalla() {
    }

    public CreditoFalla(BigDecimal idFalla) {
        this.idFalla = idFalla;
    }

    public CreditoFalla(BigDecimal idFalla, Date fechaFalla, Date fechaCreacion) {
        this.idFalla = idFalla;
        this.fechaFalla = fechaFalla;
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getIdFalla() {
        return idFalla;
    }

    public void setIdFalla(BigDecimal idFalla) {
        this.idFalla = idFalla;
    }

    public BigDecimal getCuota() {
        return cuota;
    }

    public void setCuota(BigDecimal cuota) {
        this.cuota = cuota;
    }

    public Date getFechaFalla() {
        return fechaFalla;
    }

    public void setFechaFalla(Date fechaFalla) {
        this.fechaFalla = fechaFalla;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Credito getIdCreditoFk() {
        return idCreditoFk;
    }

    public void setIdCreditoFk(Credito idCreditoFk) {
        this.idCreditoFk = idCreditoFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFalla != null ? idFalla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CreditoFalla)) {
            return false;
        }
        CreditoFalla other = (CreditoFalla) object;
        if ((this.idFalla == null && other.idFalla != null) || (this.idFalla != null && !this.idFalla.equals(other.idFalla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.microcredit.bll.CreditoFalla[ idFalla=" + idFalla + " ]";
    }

}
