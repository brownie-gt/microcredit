/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuario.findByClave", query = "SELECT u FROM Usuario u WHERE u.clave = :clave"),
    @NamedQuery(name = "Usuario.findByPrimerNombre", query = "SELECT u FROM Usuario u WHERE u.primerNombre = :primerNombre"),
    @NamedQuery(name = "Usuario.findBySegundoNombre", query = "SELECT u FROM Usuario u WHERE u.segundoNombre = :segundoNombre"),
    @NamedQuery(name = "Usuario.findByPrimerApellido", query = "SELECT u FROM Usuario u WHERE u.primerApellido = :primerApellido"),
    @NamedQuery(name = "Usuario.findBySegundoApellido", query = "SELECT u FROM Usuario u WHERE u.segundoApellido = :segundoApellido"),
    @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE u.correo = :correo"),
    @NamedQuery(name = "Usuario.findByTelefono", query = "SELECT u FROM Usuario u WHERE u.telefono = :telefono"),
    @NamedQuery(name = "Usuario.findByFechaCreacion", query = "SELECT u FROM Usuario u WHERE u.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Usuario.findByFechaModificacion", query = "SELECT u FROM Usuario u WHERE u.fechaModificacion = :fechaModificacion"),
    @NamedQuery(name = "Usuario.findByEstado", query = "SELECT u FROM Usuario u WHERE u.estado = :estado")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private String idUsuario;
    @JoinColumn(name = "ID_PERFIL", referencedColumnName = "ID_PERFIL")
    @ManyToOne(optional = false)
    private PerfilUsuario idPerfil;
    @Basic(optional = false)
    @Column(name = "CLAVE")
    private String clave;
    @Column(name = "PRIMER_NOMBRE")
    private String primerNombre;
    @Column(name = "SEGUNDO_NOMBRE")
    private String segundoNombre;
    @Column(name = "PRIMER_APELLIDO")
    private String primerApellido;
    @Column(name = "SEGUNDO_APELLIDO")
    private String segundoApellido;
    @Column(name = "CORREO")
    private String correo;
    @Column(name = "TELEFONO")
    private BigInteger telefono;
    @Lob
    @Column(name = "DPI")
    private String dpi;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.DATE)
    private Date fechaModificacion;
    @Column(name = "ESTADO")
    private Short estado;
//    @OneToMany(mappedBy = "idUsuario")
//    private List<Cartera> carteraList;

    public Usuario() {
    }

    public Usuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(String idUsuario, String clave, Date fechaCreacion) {
        this.idUsuario = idUsuario;
        this.clave = clave;
        this.fechaCreacion = fechaCreacion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public BigInteger getTelefono() {
        return telefono;
    }

    public void setTelefono(BigInteger telefono) {
        this.telefono = telefono;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }
//
//    @XmlTransient
//    public List<Cartera> getCarteraList() {
//        return carteraList;
//    }
//
//    public void setCarteraList(List<Cartera> carteraList) {
//        this.carteraList = carteraList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.microcredit.entity.Usuario[ idUsuario=" + idUsuario + " ]";
    }

    public PerfilUsuario getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(PerfilUsuario idPerfil) {
        this.idPerfil = idPerfil;
    }
    
}
