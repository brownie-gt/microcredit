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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 30178037
 */
@Entity
@Table(name = "REFERENCIA_CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReferenciaCliente.findAll", query = "SELECT r FROM ReferenciaCliente r"),
    @NamedQuery(name = "ReferenciaCliente.findByIdReferencia", query = "SELECT r FROM ReferenciaCliente r WHERE r.idReferencia = :idReferencia"),
    @NamedQuery(name = "ReferenciaCliente.findByNombre", query = "SELECT r FROM ReferenciaCliente r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "ReferenciaCliente.findByApellido", query = "SELECT r FROM ReferenciaCliente r WHERE r.apellido = :apellido"),
    @NamedQuery(name = "ReferenciaCliente.findByTelefono", query = "SELECT r FROM ReferenciaCliente r WHERE r.telefono = :telefono")})
public class ReferenciaCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_REFERENCIA")
    private String idReferencia;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "APELLIDO")
    private String apellido;
    @Column(name = "TELEFONO")
    private Integer telefono;
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE")
    @ManyToOne(optional = false)
    private Cliente idCliente;

    public ReferenciaCliente() {
    }

    public ReferenciaCliente(String idReferencia) {
        this.idReferencia = idReferencia;
    }

    public String getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(String idReferencia) {
        this.idReferencia = idReferencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
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
        hash += (idReferencia != null ? idReferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReferenciaCliente)) {
            return false;
        }
        ReferenciaCliente other = (ReferenciaCliente) object;
        if ((this.idReferencia == null && other.idReferencia != null) || (this.idReferencia != null && !this.idReferencia.equals(other.idReferencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.microcredit.entity.ReferenciaCliente[ idReferencia=" + idReferencia + " ]";
    }
    
}
