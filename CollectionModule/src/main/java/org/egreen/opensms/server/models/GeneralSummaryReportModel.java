package org.egreen.opensms.server.models;

import java.sql.Timestamp;

/**
 * Created by ruwan on 5/3/16.
 */
public class GeneralSummaryReportModel {

    private String centerId;
    private Integer type;
    private String sTime;
    private double investment;
    private double pay;
    private double nc;
    private double lcs;
    private double pc;
    private double pd;
    private double loan;
    private double ld;
    private double commision;
    private double cash;
    private double salary;
    private double overPayment;
    private double excess;
    private double expences;
    private double ln;
    private double payment;
    private double modelCash;
    private double modelExcess;
    private double modelExpences;
    private double tpyInv;
    private double tpyPay;
    private double balance;
    private double dueAmount;

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public double getInvestment() {
        return investment;
    }

    public void setInvestment(double investment) {
        this.investment = investment;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
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

    public double getPc() {
        return pc;
    }

    public void setPc(double pc) {
        this.pc = pc;
    }

    public double getPd() {
        return pd;
    }

    public void setPd(double pd) {
        this.pd = pd;
    }

    public double getLoan() {
        return loan;
    }

    public void setLoan(double loan) {
        this.loan = loan;
    }

    public double getLd() {
        return ld;
    }

    public void setLd(double ld) {
        this.ld = ld;
    }

    public double getCommision() {
        return commision;
    }

    public void setCommision(double commision) {
        this.commision = commision;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
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

    public double getExpences() {
        return expences;
    }

    public void setExpences(double expences) {
        this.expences = expences;
    }

    public double getLn() {
        return ln;
    }

    public void setLn(double lon) {
        this.ln = ln;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getModelCash() {
        return modelCash;
    }

    public void setModelCash(double modelCash) {
        this.modelCash = modelCash;
    }

    public double getModelExcess() {
        return modelExcess;
    }

    public void setModelExcess(double modelExcess) {
        this.modelExcess = modelExcess;
    }

    public double getModelExpences() {
        return modelExpences;
    }

    public void setModelExpences(double modelExpences) {
        this.modelExpences = modelExpences;
    }

    public double getTpyInv() {
        return tpyInv;
    }

    public void setTpyInv(double tpyInv) {
        this.tpyInv = tpyInv;
    }

    public double getTpyPay() {
        return tpyPay;
    }

    public void setTpyPay(double tpyPay) {
        this.tpyPay = tpyPay;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(double dueAmount) {
        this.dueAmount = dueAmount;
    }

    @Override
    public String toString() {
        return "GeneralSummaryReportModel{" +
                "centerId='" + centerId + '\'' +
                ", type=" + type +
                ", sTime='" + sTime + '\'' +
                ", investment=" + investment +
                ", pay=" + pay +
                ", nc=" + nc +
                ", lcs=" + lcs +
                ", pc=" + pc +
                ", pd=" + pd +
                ", loan=" + loan +
                ", ld=" + ld +
                ", commision=" + commision +
                ", cash=" + cash +
                ", salary=" + salary +
                ", overPayment=" + overPayment +
                ", excess=" + excess +
                ", expences=" + expences +
                ", ln=" + ln +
                ", payment=" + payment +
                ", modelCash=" + modelCash +
                ", modelExcess=" + modelExcess +
                ", modelExpences=" + modelExpences +
                ", tpyInv=" + tpyInv +
                ", tpyPay=" + tpyPay +
                ", balance=" + balance +
                ", dueAmount=" + dueAmount +
                '}';
    }
}
