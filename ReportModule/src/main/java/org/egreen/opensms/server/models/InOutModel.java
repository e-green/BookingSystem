package org.egreen.opensms.server.models;

/**
 * Created by pramoda on 6/21/16.
 */
public class InOutModel {

    private String individualName;
    private Double totalChitCount;
    private Integer winChitCount;
    private Double totalInvesment;
    private Double totalPayment;
    private Double totalCash;



    public String getIndividualName() {
        return individualName;
    }

    public void setIndividualName(String individualName) {
        this.individualName = individualName;
    }

    public Double getTotalChitCount() {
        return totalChitCount;
    }

    public void setTotalChitCount(Double totalChitCount) {
        this.totalChitCount = totalChitCount;
    }

    public Integer getWinChitCount() {
        return winChitCount;
    }

    public void setWinChitCount(Integer winChitCount) {
        this.winChitCount = winChitCount;
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
