package org.egreen.opensms.server.models;

/**
 * Created by pramoda on 6/21/16.
 */
public class InOutModel {

    private String individualName;
    private Double chitCount;
    private Double totalInvesment;
    private Double totalPayment;
    private Double totalCash;


    public String getIndividualName() {
        return individualName;
    }

    public void setIndividualName(String individualName) {
        this.individualName = individualName;
    }

    public Double getChitCount() {
        return chitCount;
    }

    public void setChitCount(Double chitCount) {
        this.chitCount = chitCount;
    }

    public Double getTotalInvesment() {
        return totalInvesment;
    }

    public void setTotalInvesment(Double totalInvesment) {
        this.totalInvesment = totalInvesment;
    }

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Double getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(Double totalCash) {
        this.totalCash = totalCash;
    }
}
