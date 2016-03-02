/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.bll;

import com.microcredit.entity.Cliente;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesConverter("clienteConverter")
public class ClienteConverter implements Converter {

    private static final Logger logger = LoggerFactory.getLogger(ClienteConverter.class);

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        logger.debug("getAsObject() - value: " + value);

        if (value != null && value.trim().length() > 0) {
            try {
                EntityManager em = JPA.getEntityManager();
                em.getTransaction().begin();
                Cliente cliente = em.find(Cliente.class, Short.valueOf(value));
                em.close();
                return cliente;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Cliente invalido."));
            }
        } else {
            return null;
        }
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((Cliente) object).getIdCliente());
        } else {
            return null;
        }
    }
}
