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
@Table(name = "CUADRE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuadre.findAll", query = "SELECT c FROM Cuadre c"),
    @NamedQuery(name = "Cuadre.findByIdCuadre", query = "SELECT c FROM Cuadre c WHERE c.idCuadre = :idCuadre"),
    @NamedQuery(name = "Cuadre.findByFechaCreacion", query = "SELECT c FROM Cuadre c WHERE c.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Cuadre.findByCobroDia", query = "SELECT c FROM Cuadre c WHERE c.cobroDia = :cobroDia"),
    @NamedQuery(name = "Cuadre.findByCobrado", query = "SELECT c FROM Cuadre c WHERE c.cobrado = :cobrado"),
    @NamedQuery(name = "Cuadre.findByBaseDia", query = "SELECT c FROM Cuadre c WHERE c.baseDia = :baseDia"),
    @NamedQuery(name = "Cuadre.findByEfectivo", query = "SELECT c FROM Cuadre c WHERE c.efectivo = :efectivo")})
public class Cuadre implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CUADRE")
    private BigDecimal idCuadre;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "COBRO_DIA")
    private BigDecimal cobroDia;
    @Column(name = "COBRADO")
    private BigDecimal cobrado;
    @Column(name = "COBRO_COBRADOR")
    private BigDecimal cobroCobrador;
    @Column(name = "BASE_DIA")
    private BigDecimal baseDia;
    @Column(name = "EFECTIVO")
    private BigDecimal efectivo;
    @JoinColumn(name = "ID_CARTERA", referencedColumnName = "ID_CARTERA")
    @ManyToOne
    private Cartera idCartera;

    public Cuadre() {
    }

    public Cuadre(BigDecimal idCuadre) {
        this.idCuadre = idCuadre;
    }

    public BigDecimal getIdCuadre() {
        return idCuadre;
    }

    public void setIdCuadre(BigDecimal idCuadre) {
        this.idCuadre = idCuadre;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getCobroDia() {
        return cobroDia;
    }

    public void setCobroDia(BigDecimal cobroDia) {
        this.cobroDia = cobroDia;
    }

    public BigDecimal getCobrado() {
        return cobrado;
    }

    public void setCobrado(BigDecimal cobrado) {
        this.cobrado = cobrado;
    }

    public BigDecimal getCobroCobrador() {
        return cobroCobrador;
    }

    public void setCobroCobrador(BigDecimal cobroCobrador) {
        this.cobroCobrador = cobroCobrador;
    }
    
    public BigDecimal getBaseDia() {
        return baseDia;
    }

    public void setBaseDia(BigDecimal baseDia) {
        this.baseDia = baseDia;
    }

    public BigDecimal getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(BigDecimal efectivo) {
        this.efectivo = efectivo;
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
        hash += (idCuadre != null ? idCuadre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuadre)) {
            return false;
        }
        Cuadre other = (Cuadre) object;
        if ((this.idCuadre == null && other.idCuadre != null) || (this.idCuadre != null && !this.idCuadre.equals(other.idCuadre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.microcredit.entity.Cuadre[ idCuadre=" + idCuadre + " ]";
    }

}
