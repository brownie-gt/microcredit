/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microcredit.dao;

import com.microcredit.entity.Abono;
import com.microcredit.entity.Credito;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 30178037
 */
public class DetalleCredito implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(DetalleCredito.class);
    private BigDecimal interes;
    private BigDecimal cuota;
    private BigDecimal cuotaRequerida;
    private int numeroAbonos;
    private BigDecimal abonado;
    private BigDecimal saldoPorPagar;
    private BigDecimal totalAPagar;
    private Credito credito;
    private boolean pagado;
    private boolean falla;

//    private final BigDecimal tasa = new BigDecimal(0.15);
    private final BigDecimal tasa = BigDecimal.valueOf(0.15);
    private static final int NUMERO_DE_CUOTAS = 23;

    public DetalleCredito() {
    }
    
    public DetalleCredito(Credito credito) {
        this.credito = credito;
    }

    public void calcularDetalleCredito() {
        calcularTotalAPagar();
        calcularAbonado();
        calcularSaldoPorPagar();
        calcularCuota();
    }

    private void calcularTotalAPagar() {
        if (credito.getMonto() != null && credito.getMonto().intValue() > 0) {
            interes = credito.getMonto().multiply(tasa).setScale(0, RoundingMode.HALF_EVEN);
            totalAPagar = credito.getMonto().add(interes).setScale(0, RoundingMode.HALF_EVEN);
        }
    }

    private void calcularAbonado() {
        abonado = new BigDecimal(0);
        if (credito.getAbonoList() != null) {
            credito.getAbonoList().size();//just to instatiate list

            for (Abono a : credito.getAbonoList()) {
                numeroAbonos++;
                abonado = abonado.add(a.getMonto()).setScale(0, RoundingMode.HALF_EVEN);
            }
        }
    }

    private void calcularSaldoPorPagar() {
        if (abonado.intValue() > 0) {
            saldoPorPagar = totalAPagar.subtract(abonado).setScale(0, RoundingMode.HALF_EVEN);
        } else {
            saldoPorPagar = totalAPagar;
        }
    }

    private void calcularCuota() {
        if (totalAPagar.compareTo(abonado) <= 0) {
            cuota = new BigDecimal(0);
            cuotaRequerida = new BigDecimal(0);
            pagado = true;
        } else {
            cuotaRequerida = cuota = totalAPagar.divide(new BigDecimal(NUMERO_DE_CUOTAS)).setScale(0, RoundingMode.HALF_EVEN);
            pagado = false;
        }
    }

    public void limpiar() {
        credito = new Credito();
        interes = null;
        cuota = null;
        abonado = null;
        numeroAbonos = 0;
        saldoPorPagar = null;
        totalAPagar = null;
        pagado = false;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public BigDecimal getCuota() {
        return cuota;
    }

    public void setCuota(BigDecimal cuota) {
        this.cuota = cuota;
    }

    public BigDecimal getAbonado() {
        return abonado;
    }

    public void setAbonado(BigDecimal abonado) {
        this.abonado = abonado;
    }

    public BigDecimal getSaldoPorPagar() {
        return saldoPorPagar;
    }

    public void setSaldoPorPagar(BigDecimal saldoPorPagar) {
        this.saldoPorPagar = saldoPorPagar;
    }

    public BigDecimal getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(BigDecimal totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public int getNumeroAbonos() {
        return numeroAbonos;
    }

    public void setNumeroAbonos(int numeroAbonos) {
        this.numeroAbonos = numeroAbonos;
    }

    public boolean isFalla() {
        return falla;
    }

    public void setFalla(boolean falla) {
        this.falla = falla;
    }

    public BigDecimal getCuotaRequerida() {
        return cuotaRequerida;
    }

    public void setCuotaRequerida(BigDecimal cuotaRequerida) {
        this.cuotaRequerida = cuotaRequerida;
    }
}
