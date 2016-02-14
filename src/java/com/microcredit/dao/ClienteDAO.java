/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.dao;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author 30178037
 */
public class ClienteDAO {
    private int idCliente;
    private long dpi;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String nit;
    private int telefono;
    private String direccion;
    private String tipoNegocio;
    private Date fechaInicio;
    private List<ReferenciaDAO> referencias;
     
}
