/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.bll;

import com.microcredit.bean.CreditoBean;
import com.microcredit.bean.CuadreBean;
import com.microcredit.entity.Cartera;
import com.microcredit.entity.Cliente;
import com.microcredit.entity.Credito;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author a462101
 */
public class SendMail {

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

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("microcredit.gt@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("karinemedina1990@gmail.com"));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("brownie.gt@gmail.com"));
            message.setSubject("Resumen de cuadre - " + cuadre.getCartera().getNombre());

            String strFecha = new SimpleDateFormat("EEE, dd/MM/yy").format(cuadre.getCuadre().getFechaCuadre());

            message.setText("Resumen de cuadre,"
                    + "\n\nFecha: " + strFecha
                    + "\nCartera: " + cuadre.getCartera().getNombre()
                    + "\n\n (+) Base: " + cuadre.getCuadre().getBaseDia()
                    + "\n (+) Cobrado: " + cuadre.getCuadre().getCobrado()
                    + "\n (+) Multas: " + cuadre.getCuadre().getMulta()
                    //////////////////////////////////////////////////////
                    + "\n\n (-) Gastos: " + cuadre.getTotalGastos()
                    + "\n (-) Prestado: " + cuadre.getTotalPrestado()
                    //////////////////////////////////////////////////////
                    + "\n\n (+) Efectivo: " + cuadre.getCuadre().getEfectivo()
                    //////////////////////////////////////////////////////
                    + "\n Cobro diario: " + cuadre.getCuadre().getCobroDia()
                    + "\n Cobrado: " + cuadre.getCuadre().getCobrado()
                    + "\n\n Cumplimiento: " + cuadre.getPorcentajeCumplimiento() + "%"
                    //////////////////////////////////////////////////////
                    + "\n\n Caja menor actual: " + cuadre.cajaMenor.getSaldoActual()
                    + "\n Caja menor final: " + cuadre.cajaMenor.getSaldoFinal()
                    + getCreditosNuevos(cuadre.getCartera(), cuadre.getCuadre().getFechaCuadre()));

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getCreditosNuevos(Cartera cart, Date fechaCuadre) {
        List<Credito> creditos = CreditoBean.getCreditosByDate(cart, fechaCuadre);
        if (creditos != null && creditos.size() > 0) {
            String listaCreditos = "\n\nCreditos nuevos:\n";
            int i = 0;
            for (Credito c : creditos) {
                i++;
                listaCreditos += "\n " + i + ".- " + c.getIdRuta().getNombre() + " - " + c.getMonto()
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

}
