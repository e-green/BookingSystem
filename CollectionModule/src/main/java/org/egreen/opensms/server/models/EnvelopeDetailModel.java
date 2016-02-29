package org.egreen.opensms.server.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by ruwan on 2/18/16.
 */
public class EnvelopeDetailModel {
    private String individualId;
    private Timestamp date;
    private BigDecimal loanAmount;
    private BigDecimal Investment;
    private BigDecimal cash;
    private boolean isLCS;
    private BigDecimal ldPayment;
    private BigDecimal loanCharges;
    private BigDecimal pcCharges;
    private BigDecimal excess;
    private BigDecimal overPayment;
    private BigDecimal commision;
    private BigDecimal salary;
    private BigDecimal lessComissionSingle;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getLdPayment() {
        return ldPayment;
    }

    public void setLdPayment(BigDecimal ldPayment) {
        this.ldPayment = ldPayment;
    }

    public BigDecimal getLoanCharges() {
        return loanCharges;
    }

    public void setLoanCharges(BigDecimal loanCharges) {
        this.loanCharges = loanCharges;
    }

    public BigDecimal getPcCharges() {
        return pcCharges;
    }

    public void setPcCharges(BigDecimal pcCharges) {
        this.pcCharges = pcCharges;
    }

    public BigDecimal getExcess() {
        return excess;
    }

    public void setExcess(BigDecimal excess) {
        this.excess = excess;
    }

    public BigDecimal getOverPayment() {
        return overPayment;
    }

    public void setOverPayment(BigDecimal overPayment) {
        this.overPayment = overPayment;
    }

    public BigDecimal getCommision() {
        return commision;
    }

    public void setCommision(BigDecimal commision) {
        this.commision = commision;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getLessComissionSingle() {
        return lessComissionSingle;
    }

    public void setLessComissionSingle(BigDecimal lessComissionSingle) {
        this.lessComissionSingle = lessComissionSingle;
    }

    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

    public BigDecimal getInvestment() {
        return Investment;
    }

    public void setInvestment(BigDecimal investment) {
        Investment = investment;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public boolean isLCS() {
        return isLCS;
    }

    public void setLCS(boolean LCS) {
        isLCS = LCS;
    }

    @Override
    public String toString() {
        return "EnvelopeDetailModel{" +
                "individualId='" + individualId + '\'' +
                ", date=" + date +
                ", loanAmount=" + loanAmount +
                ", Investment=" + Investment +
                ", cash=" + cash +
                ", isLCS=" + isLCS +
                ", ldPayment=" + ldPayment +
                ", loanCharges=" + loanCharges +
                ", pcCharges=" + pcCharges +
                ", excess=" + excess +
                ", overPayment=" + overPayment +
                ", commision=" + commision +
                ", salary=" + salary +
                ", lessComissionSingle=" + lessComissionSingle +
                '}';
    }
}
