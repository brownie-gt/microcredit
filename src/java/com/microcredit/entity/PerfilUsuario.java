/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "PERFIL_USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PerfilUsuario.findAll", query = "SELECT p FROM PerfilUsuario p"),
    @NamedQuery(name = "PerfilUsuario.findByIdPerfil", query = "SELECT p FROM PerfilUsuario p WHERE p.idPerfil = :idPerfil"),
    @NamedQuery(name = "PerfilUsuario.findByNombre", query = "SELECT p FROM PerfilUsuario p WHERE p.nombre = :nombre")})
public class PerfilUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PERFIL")
    private Short idPerfil;
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPerfil")
    private List<Usuario> usuarioList;

    public PerfilUsuario() {
    }

    public PerfilUsuario(Short idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Short getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Short idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerfil != null ? idPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerfilUsuario)) {
            return false;
        }
        PerfilUsuario other = (PerfilUsuario) object;
        if ((this.idPerfil == null && other.idPerfil != null) || (this.idPerfil != null && !this.idPerfil.equals(other.idPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.microcredit.entity.PerfilUsuario[ idPerfil=" + idPerfil + " ]";
    }
    
}
