package org.egreen.opensms.server.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ruwan on 2/17/16.
 */
public class GenralSummeryReportModel {


    private String name;
    private String date;
    private double investement;


    private Map<String, Double> transactions;

    private double payDue;
    private double cash;
    private double totalInv;
    private double totalPay;
    private double balance;

    public GenralSummeryReportModel() {
        if (transactions == null) {
            transactions = new HashMap<String, Double>();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getInvestement() {
        return investement;
    }

    public void setInvestement(double investement) {
        this.investement = investement;
    }

    public Map<String, Double> getTransactions() {
        return transactions;
    }

    public void setTransactions(Map<String, Double> transactions) {
        this.transactions = transactions;
    }

    public double getPayDue() {
        return payDue;
    }

    public void setPayDue(double payDue) {
        this.payDue = payDue;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getTotalInv() {
        return totalInv;
    }

    public void setTotalInv(double totalInv) {
        this.totalInv = totalInv;
    }

    public double getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(double totalPay) {
        this.totalPay = totalPay;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
