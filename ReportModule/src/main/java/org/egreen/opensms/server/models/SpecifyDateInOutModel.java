package org.egreen.opensms.server.models;

import java.math.BigDecimal;

/**
 * Created by ruwan on 5/12/16.
 */
public class SpecifyDateInOutModel {

    private BigDecimal inValue;
    private BigDecimal outValue;

    public BigDecimal getInValue() {
        return inValue;
    }

    public void setInValue(BigDecimal inValue) {
        this.inValue = inValue;
    }

    public BigDecimal getOutValue() {
        return outValue;
    }

    public void setOutValue(BigDecimal outValue) {
        this.outValue = outValue;
    }

    @Override
    public String toString() {
        return "SpecifyDateInOutModel{" +
                "inValue=" + inValue +
                ", outValue=" + outValue +
                '}';
    }
}
