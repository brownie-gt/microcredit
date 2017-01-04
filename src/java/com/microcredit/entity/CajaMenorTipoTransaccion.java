/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author a462101
 */
@Entity
@Table(name = "CAJA_MENOR_TIPO_TRANSACCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CajaMenorTipoTransaccion.findAll", query = "SELECT c FROM CajaMenorTipoTransaccion c")})
public class CajaMenorTipoTransaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int INGRESO_CAPITAL = 1;
    public static final int INGRESO_EFECTIVO_CUADRE = 2;
    public static final int EGRESO_DEVOLUCION_SOCIOS = 3;
    public static final int EGRESO_BASE_CUADRE = 4;
    
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO")
    private int idTipo;
    
    @Column(name = "TIPO")
    private String tipo;
    
    public CajaMenorTipoTransaccion() {
    }

    public CajaMenorTipoTransaccion(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idTipo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CajaMenorTipoTransaccion)) {
            return false;
        }
        CajaMenorTipoTransaccion other = (CajaMenorTipoTransaccion) object;
        if (this.idTipo != other.idTipo) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "com.microcredit.entity.CajaMenorTipoTransaccion[ idTipo=" + idTipo + " ]";
    }
    
}
