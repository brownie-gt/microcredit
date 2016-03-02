/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.bll;

import com.microcredit.bll.JPA;
import com.microcredit.entity.Cliente;
import com.microcredit.entity.TipoGasto;
import java.math.BigDecimal;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("tipoGastoConverter")
public class TipoGastoConverter implements Converter{
    
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {

        if (value != null && value.trim().length() > 0) {
            try {
                EntityManager em = JPA.getEntityManager();
                em.getTransaction().begin();
                TipoGasto tg = em.find(TipoGasto.class, new BigDecimal(value));
                em.close();
                return tg;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Tipo gasto invalido"));
            }
        } else {
            return null;
        }
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
         if (object == null) {
            return "";
        }

        if (object instanceof TipoGasto) {
            return String.valueOf(((TipoGasto) object).getIdTipoGasto());
        } else {
            throw new ConverterException(new FacesMessage(String.format("%s is not a valid Tipo", object)));
        }
    }
    
}
