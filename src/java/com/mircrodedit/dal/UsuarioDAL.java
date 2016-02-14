/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mircrodedit.dal;

import com.microcredit.dao.UsuarioDAO;
import com.microcredit.db.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 30178037
 */
public class UsuarioDAL {

    private static final Logger log = LoggerFactory.getLogger(UsuarioDAL.class);

    public static int insert(UsuarioDAO usuario) {
        try {
            String sql = "insert into usuario "
                    + "(ID_USUARIO,"
                    + "CLAVE,"
                    + "ID_PERFIL,"
                    + "PRIMER_NOMBRE,"
                    + "SEGUNDO_NOMBRE,"
                    + "PRIMER_APELLIDO,"
                    + "SEGUNDO_APELLIDO,"
                    + "CORREO,"
                    + "TELEFONO,"
                    + "DPI)"
                    + " values (?,?,?,?,?,?,?,?,?,?)";

            Connection conn = DBConnection.getConnection();
            QueryRunner qr = new QueryRunner();
            Object[] params = { usuario.getUsuario(),
                usuario.getClave(), usuario.getIdPerfil(), usuario.getPrimerNombre(), usuario.getSegundoNombre(),
                usuario.getPrimerApellido(), usuario.getSegundoApellido(), usuario.getCorreo(), usuario.getTelefono(),
                usuario.getDpi()};

            return qr.update(conn, sql, params);
        } catch (SQLException ex) {
            log.error("", ex);

            return 0;
        }

    }

}
