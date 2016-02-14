/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.microcredit.dao.UsuarioDAO;
import com.microcredit.db.DBConnection;
import com.mircrodedit.dal.UsuarioDAL;
import java.sql.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 30178037
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    
    public static void main(String[] args) {

        Connection conn = DBConnection.getConnection();
        
        UsuarioDAO usuario = new UsuarioDAO();
        usuario.setClave("123abc");
        usuario.setPrimerNombre("Brwayam");
        usuario.setPrimerApellido("Molina");
        usuario.setIdPerfil(1);
        usuario.setDpi(new Long("2626195040101"));
        usuario.setCorreo("brownie.gt@gmail.com");
        
        UsuarioDAL u = new UsuarioDAL();
        int result = u.insert(usuario);
        logger.info("insert: "+result);

    }
    
    
    
}
