package org.egreen.opensms.server.models;

import java.sql.Timestamp;

/**
 * Created by ruwan on 2/17/16.
 */
public class GeneralSummaryReceiptModel {

    private String centerId;
    private String individualId;
    private String envelopeId;
    private Timestamp date;
    private Integer type;
    private double balance;
    private double pd;

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

    public String getEnvelopeId() {
        return envelopeId;
    }

    public void setEnvelopeId(String envelopeId) {
        this.envelopeId = envelopeId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "GeneralSummaryReceiptModel{" +
                "centerId='" + centerId + '\'' +
                ", individualId='" + individualId + '\'' +
                ", envelopeId='" + envelopeId + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", balance=" + balance +
                ", pd=" + pd +
                '}';
    }

    public double getPd() {
        return pd;
    }

    public void setPd(double pd) {
        this.pd = pd;
    }
}
