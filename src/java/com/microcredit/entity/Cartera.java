/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 30178037
 */
@Entity
@Table(name = "CARTERA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cartera.findAll", query = "SELECT c FROM Cartera c"),
    @NamedQuery(name = "Cartera.findByIdCartera", query = "SELECT c FROM Cartera c WHERE c.idCartera = :idCartera"),
    @NamedQuery(name = "Cartera.findByNombre", query = "SELECT c FROM Cartera c WHERE c.nombre = :nombre")})
public class Cartera implements Serializable {
    @OneToMany(mappedBy = "idCartera")
    private List<Cuadre> cuadreList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCartera")
    private List<BaseDia> baseDiaList;
    @OneToMany(mappedBy = "idCartera")
    private List<Gasto> gastoList;
    @OneToMany(mappedBy = "idCartera")
    private List<Ruta> rutaList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CARTERA")
    private Short idCartera;
    @Column(name = "NOMBRE")
    private String nombre;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCartera")
//    private List<Credito> creditoList;
//    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
//    @ManyToOne
//    private Usuario idUsuario;

    public Cartera() {
    }

    public Cartera(Short idCartera) {
        this.idCartera = idCartera;
    }

    public Short getIdCartera() {
        return idCartera;
    }

    public void setIdCartera(Short idCartera) {
        this.idCartera = idCartera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

//    @XmlTransient
//    public List<Credito> getCreditoList() {
//        return creditoList;
//    }
//
//    public void setCreditoList(List<Credito> creditoList) {
//        this.creditoList = creditoList;
//    }
//
//    public Usuario getIdUsuario() {
//        return idUsuario;
//    }
//
//    public void setIdUsuario(Usuario idUsuario) {
//        this.idUsuario = idUsuario;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCartera != null ? idCartera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cartera)) {
            return false;
        }
        Cartera other = (Cartera) object;
        if ((this.idCartera == null && other.idCartera != null) || (this.idCartera != null && !this.idCartera.equals(other.idCartera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.microcredit.entity.Cartera[ idCartera=" + idCartera + " ]";
    }

    @XmlTransient
    public List<BaseDia> getBaseDiaList() {
        return baseDiaList;
    }

    public void setBaseDiaList(List<BaseDia> baseDiaList) {
        this.baseDiaList = baseDiaList;
    }

    @XmlTransient
    public List<Gasto> getGastoList() {
        return gastoList;
    }

    public void setGastoList(List<Gasto> gastoList) {
        this.gastoList = gastoList;
    }

    @XmlTransient
    public List<Ruta> getRutaList() {
        return rutaList;
    }

    public void setRutaList(List<Ruta> rutaList) {
        this.rutaList = rutaList;
    }

    @XmlTransient
    public List<Cuadre> getCuadreList() {
        return cuadreList;
    }

    public void setCuadreList(List<Cuadre> cuadreList) {
        this.cuadreList = cuadreList;
    }
    
}
