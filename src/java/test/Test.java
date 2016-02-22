/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 30178037
 */
public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    static BigDecimal total;

    public static void main(String[] args) {
        BigDecimal monto = new BigDecimal(3500);
        BigDecimal tasa = new BigDecimal(0.15);
        BigDecimal interes = monto.multiply(tasa);
        interes = interes.setScale(0, RoundingMode.HALF_EVEN);
        logger.debug("interes: " + interes);
        logger.debug("total: " + monto.add(interes));
    }

}
