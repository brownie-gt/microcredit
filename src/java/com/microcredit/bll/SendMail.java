/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.bll;

import com.microcredit.bean.CajaMenorBean;
import com.microcredit.bean.CreditoBean;
import com.microcredit.bean.CuadreBean;
import com.microcredit.entity.Cartera;
import com.microcredit.entity.Cliente;
import com.microcredit.entity.Credito;
import com.microcredit.entity.Gasto;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author a462101
 */
public class SendMail {

    private static final Logger logger = LoggerFactory.getLogger(SendMail.class);
    private static final DecimalFormat df = new DecimalFormat("###,###");

    public void sendCuadre(CuadreBean cuadre) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("microcredit.gt@gmail.com", "fullon111");
            }
        });

        String strFecha = new SimpleDateFormat("EEE, dd/MM/yy").format(cuadre.getCuadre().getFechaCuadre());
        String bodyText = ("Resumen de cuadre,"
                //////////////////////////////////////////////////////
                + "\n\nFecha: " + strFecha
                + "\nCartera: " + cuadre.getCartera().getNombre()
                //////////////////////////////////////////////////////
                + "\n\n (+) Base: " + df.format(cuadre.getCuadre().getBaseDia())
                + "\n (+) Cobrado: " + df.format(cuadre.getCuadre().getCobrado())
                + "\n (+) Multas: " + df.format(cuadre.getCuadre().getMulta())
                //////////////////////////////////////////////////////
                + "\n\n (-) Gastos: " + df.format(cuadre.getTotalGastos())
                + "\n (-) Prestado: " + df.format(cuadre.getTotalPrestado())
                //////////////////////////////////////////////////////
                + "\n\n (+) Efectivo: " + df.format(cuadre.getCuadre().getEfectivo())
                //////////////////////////////////////////////////////
                + "\n\n*******************************"
                + "\n\n Base día siguiente: " + df.format(cuadre.getCuadre().getBaseDiaSiguiente())
                + getCajaMenor(cuadre.cajaMenor)
                //////////////////////////////////////////////////////
                + "\n\n Cobro diario: " + df.format(cuadre.getCuadre().getCobroDia())
                + "\n Cobrado:        " + df.format(cuadre.getCuadre().getCobrado())
                + "\n Cumplimiento: " + cuadre.getPorcentajeCumplimiento() + "%"
                + "\n "+cuadre.getCountFallas() +" Fallas | Total: " + df.format(cuadre.getTotalFallas())
                + "\n\n*******************************"
                //////////////////////////////////////////////////////
                + getGastos(cuadre.getGastosDelDia())
                + getCreditosNuevos(cuadre.getCartera(), cuadre.getCuadre().getFechaCuadre()));

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("microcredit.gt@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("karinemedina1990@gmail.com"));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("microcredit.gt@gmail.com"));
            message.setSubject("Cuadre - " + cuadre.getCartera().getNombre() + " - " + strFecha);
            message.setText(bodyText);

            Transport.send(message);

        } catch (MessagingException e) {
            logger.error("EMAIL TEXT:\n" + bodyText + "\n\nError enviando email: ", e);
//            throw new RuntimeException(e);
        }
    }

    private String getCajaMenor(CajaMenorBean cajaMenor) {
        String str = "\n\n Caja menor actual: " + df.format(cajaMenor.getSaldoActual());

        if (cajaMenor.getIngreso().intValue() > 0) {
            str += "\n (+) Ingreso: " + df.format(cajaMenor.getIngreso());
        } else if (cajaMenor.getEgreso().intValue() > 0) {
            str += "\n (-) Egreso: " + df.format(cajaMenor.getEgreso());
        }
        str += "\n Caja menor final:    " + df.format(cajaMenor.getSaldoFinal());
        return str;
    }

    private String getCreditosNuevos(Cartera cart, Date fechaCuadre) {
        List<Credito> creditos = CreditoBean.getCreditosByDate(cart, fechaCuadre);
        if (creditos != null && creditos.size() > 0) {
            String listaCreditos = "\n\nCreditos nuevos:\n";
            int i = 0;
            for (Credito c : creditos) {
                i++;
                listaCreditos += "\n " + i + ".- " + c.getIdRuta().getNombre() + " - " + df.format(c.getMonto())
                        + " - " + formatClient(c.getIdCliente());
            }
            return listaCreditos;
        }
        return "";
    }

    private String formatClient(Cliente c) {
        String str = "";
        if (c.getPrimerNombre() != null) {
            str += c.getPrimerNombre();
        }

        if (c.getSegundoNombre() != null) {
            str += " " + c.getSegundoNombre();
        }

        if (c.getPrimerApellido() != null) {
            str += " " + c.getPrimerApellido();
        }

        if (c.getSegundoApellido() != null) {
            str += " " + c.getSegundoApellido();
        }
        return str;
    }

    private String getGastos(List<Gasto> gastos) {
        if (gastos != null && gastos.size() > 0) {
            String listaGastos = "\n\nGastos:\n";
            int i = 0;
            for (Gasto g : gastos) {
                i++;
                listaGastos += "\n " + i + ".- " + g.getIdTipoGasto().getDescripcion() + " - " + g.getMonto();
                if (g.getDescripcion() != null) {
                    listaGastos += " - " + g.getDescripcion();
                }
            }
            return listaGastos;
        }
        return "";
    }

}
