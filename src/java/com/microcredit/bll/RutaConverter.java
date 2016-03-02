
package com.microcredit.bll;

import com.microcredit.entity.Ruta;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("rutaConverter")
public class RutaConverter implements Converter{

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {

        if (value != null && value.trim().length() > 0) {
            try {
                EntityManager em = JPA.getEntityManager();
                em.getTransaction().begin();
                Ruta r = em.find(Ruta.class, Short.valueOf(value));
                em.close();
                return r;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Ruta invalida."));
            }
        } else {
            return null;
        }
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((Ruta) object).getIdRuta());
        } else {
            return null;
        }
    }
}