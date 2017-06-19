/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = "CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByIdCliente", query = "SELECT c FROM Cliente c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Cliente.findByPrimerNombre", query = "SELECT c FROM Cliente c WHERE c.primerNombre = :primerNombre"),
    @NamedQuery(name = "Cliente.findBySegundoNombre", query = "SELECT c FROM Cliente c WHERE c.segundoNombre = :segundoNombre"),
    @NamedQuery(name = "Cliente.findByPrimerApellido", query = "SELECT c FROM Cliente c WHERE c.primerApellido = :primerApellido"),
    @NamedQuery(name = "Cliente.findBySegundoApellido", query = "SELECT c FROM Cliente c WHERE c.segundoApellido = :segundoApellido"),
    @NamedQuery(name = "Cliente.findByDpi", query = "SELECT c FROM Cliente c WHERE c.dpi = :dpi"),
    @NamedQuery(name = "Cliente.findByNit", query = "SELECT c FROM Cliente c WHERE c.nit = :nit"),
    @NamedQuery(name = "Cliente.findByTelefono", query = "SELECT c FROM Cliente c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Cliente.findByDireccion", query = "SELECT c FROM Cliente c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Cliente.findByTipoNegocio", query = "SELECT c FROM Cliente c WHERE c.tipoNegocio = :tipoNegocio"),
    @NamedQuery(name = "Cliente.findByFechaCreacion", query = "SELECT c FROM Cliente c WHERE c.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Cliente.findByFechaModificacion", query = "SELECT c FROM Cliente c WHERE c.fechaModificacion = :fechaModificacion"),
    @NamedQuery(name = "Cliente.findByEstado", query = "SELECT c FROM Cliente c WHERE c.estado = :estado")})
@SequenceGenerator(name = "CLIENTE_SEQ", sequenceName = "CLIENTE_SEQ", allocationSize = 1)
public class Cliente implements Serializable {

    @Column(name = "ESTADO")
    private Integer estado;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(generator = "CLIENTE_SEQ")
    @Column(name = "ID_CLIENTE")
    private Short idCliente;
    @Column(name = "PRIMER_NOMBRE")
    private String primerNombre;
    @Column(name = "SEGUNDO_NOMBRE")
    private String segundoNombre;
    @Column(name = "PRIMER_APELLIDO")
    private String primerApellido;
    @Column(name = "SEGUNDO_APELLIDO")
    private String segundoApellido;
    @Column(name = "DPI")
    private String dpi;
    @Column(name = "NIT")
    private String nit;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TIPO_NEGOCIO")
    private String tipoNegocio;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.DATE)
    private Date fechaModificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente")
    private List<Credito> creditoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente")
    private List<ReferenciaCliente> referenciaClienteList;
    
    @Column(name = "CONTADOR")
    private String contador;

    public Cliente() {
    }

    public Cliente(Short idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente(Short idCliente, Date fechaCreacion) {
        this.idCliente = idCliente;
        this.fechaCreacion = fechaCreacion;
    }

    public Short getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Short idCliente) {
        this.idCliente = idCliente;
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

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoNegocio() {
        return tipoNegocio;
    }

    public void setTipoNegocio(String tipoNegocio) {
        this.tipoNegocio = tipoNegocio;
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


    public String getContador() {
        return contador;
    }

    public void setContador(String contador) {
        this.contador = contador;
    }
    
    @XmlTransient
    public List<Credito> getCreditoList() {
        return creditoList;
    }

    public void setCreditoList(List<Credito> creditoList) {
        this.creditoList = creditoList;
    }

    @XmlTransient
    public List<ReferenciaCliente> getReferenciaClienteList() {
        return referenciaClienteList;
    }

    public void setReferenciaClienteList(List<ReferenciaCliente> referenciaClienteList) {
        this.referenciaClienteList = referenciaClienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.microcredit.entity.Cliente[ idCliente=" + idCliente + " ]";
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    
}
