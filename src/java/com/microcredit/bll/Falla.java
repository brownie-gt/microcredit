/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.bll;

import com.microcredit.entity.Credito;
import com.microcredit.entity.CreditoFalla;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.EntityManager;

/**
 *
 * @author a462101
 */
public class Falla {
    
    public static void ingresarFalla(EntityManager em, Credito c, BigDecimal cuota, Date fechaFalla) {
        CreditoFalla falla = new CreditoFalla();
        falla.setIdCreditoFk(c);
        falla.setCuota(cuota);
        falla.setFechaFalla(fechaFalla);
        c.getCreditoFallaList().size();//just to instatiate
        c.getCreditoFallaList().add(falla);
        em.merge(c);
    }
    
}
