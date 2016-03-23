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
    private double pay;
    private double salary;
    private double overPayment;
    private double excess;
    private double nc;
    private double lcs;
    private double payment;

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

    public double getPd() {
        return pd;
    }

    public void setPd(double pd) {
        this.pd = pd;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getOverPayment() {
        return overPayment;
    }

    public void setOverPayment(double overPayment) {
        this.overPayment = overPayment;
    }

    public double getExcess() {
        return excess;
    }

    public void setExcess(double excess) {
        this.excess = excess;
    }

    public double getNc() {
        return nc;
    }

    public void setNc(double nc) {
        this.nc = nc;
    }

    public double getLcs() {
        return lcs;
    }

    public void setLcs(double lcs) {
        this.lcs = lcs;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
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
                ", pay=" + pay +
                ", salary=" + salary +
                ", overPayment=" + overPayment +
                ", excess=" + excess +
                ", nc=" + nc +
                ", lcs=" + lcs +
                ", payment=" + payment +
                '}';
    }
}
