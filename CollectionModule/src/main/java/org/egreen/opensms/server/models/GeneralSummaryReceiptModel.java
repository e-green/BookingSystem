package org.egreen.opensms.server.models;

import java.sql.Timestamp;

/**
 * Created by ruwan on 2/17/16.
 */
public class GeneralSummaryReceiptModel {

    private String centerId;
    private String individualId;
    private Timestamp date;
    private Integer type;
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

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

    @Override
    public String toString() {
        return "GeneralSummaryReceiptModel{" +
                "centerId='" + centerId + '\'' +
                ", individualId='" + individualId + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", balance=" + balance +
                '}';
    }
}
