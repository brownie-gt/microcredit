package com.microcredit.bll;

import com.microcredit.entity.Ruta;
import java.math.BigDecimal;

public class CobroPorRuta {
    
    private Ruta ruta;
    private BigDecimal cobro;
    
    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public BigDecimal getCobro() {
        return cobro;
    }

    public void setCobro(BigDecimal cobro) {
        this.cobro = cobro;
    }
    
    
    
}
