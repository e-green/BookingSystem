package org.egreen.opensms.server.models;

import java.math.BigDecimal;

/**
 * Created by ruwan on 6/5/16.
 */
public class ReportChitModel {
    private String chitNumber;
    private String wT;
    private BigDecimal invesment;
    private BigDecimal amount;
    private String ncOLCS;

    public String getChitNumber() {
        return chitNumber;
    }

    public void setChitNumber(String chitNumber) {
        this.chitNumber = chitNumber;
    }

    public String getwT() {
        return wT;
    }

    public void setwT(String wT) {
        this.wT = wT;
    }

    public BigDecimal getInvesment() {
        return invesment;
    }

    public void setInvesment(BigDecimal invesment) {
        this.invesment = invesment;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNcOLCS() {
        return ncOLCS;
    }

    public void setNcOLCS(String ncOLCS) {
        this.ncOLCS = ncOLCS;
    }
}
