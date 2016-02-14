/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.bll;

import com.microcredit.dao.UsuarioDAO;
import com.microcredit.entity.PerfilUsuario;
import com.mircrodedit.dal.UsuarioDAL;
import java.math.BigDecimal;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 30178037
 */
@ManagedBean
public class Usuario {

    private static final Logger logger = LoggerFactory.getLogger(Usuario.class);
    private UsuarioDAO user = new UsuarioDAO();
    private static String dataSourceName = "jdbc/mc";
    private JPA entity = new JPA();
    private EntityManager em;
    private com.microcredit.entity.Usuario usuario;

    public Usuario() {
    }

    public String insertUsuario() {
        int result = UsuarioDAL.insert(this.user);
        if (result > 0) {
            return "/usuario_confirm.xhtml";
        } else {
            return "";
        }
    }

    public String insert() {
        em = entity.getEntityManager();
        em.getTransaction().begin();
        usuario = new com.microcredit.entity.Usuario();
        PerfilUsuario perfil = new PerfilUsuario();
        perfil.setIdPerfil(BigDecimal.valueOf(user.getIdPerfil()));
        usuario.setIdPerfil(perfil);
        usuario.setPrimerNombre(user.getPrimerNombre());
        usuario.setIdUsuario(user.getUsuario());
        usuario.setClave(user.getClave());
        usuario.setFechaCreacion(new Date());
        
        
        em.persist(usuario);
        em.getTransaction().commit();
        return "/usuario_confirm.xhtml";
    }

    public String log() {
        System.out.print("hello");
        return "";
    }

    public UsuarioDAO getUser() {
        return user;
    }

    public void setUser(UsuarioDAO user) {
        this.user = user;
    }

}
