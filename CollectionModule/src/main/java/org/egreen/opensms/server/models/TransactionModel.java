package org.egreen.opensms.server.models;

import org.egreen.opensms.server.entity.Chit;
import org.egreen.opensms.server.entity.Envelope;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by ruwan on 2/17/16.
 */
public class TransactionModel {
    private Envelope envelope;
    private Chit chit;
    private Timestamp date;
    private BigDecimal loanAmount;
    private BigDecimal ldPayment;
    private BigDecimal loanCharges;
    private BigDecimal pcCharges;
    private BigDecimal excess;
    private BigDecimal notCommisionPresentage;
    private BigDecimal commisionPresentage;
    private BigDecimal salaryPresentage;
    private String individualId;


    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
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

    public Envelope getEnvelope() { return envelope; }

    public void setEnvelope(Envelope envelope) { this.envelope = envelope; }

    public Chit getChit() { return chit; }

    public void setChit(Chit chit) { this.chit = chit;}

    public BigDecimal getNotCommisionPresentage() {
        return notCommisionPresentage;
    }

    public void setNotCommisionPresentage(BigDecimal notCommisionPresentage) {
        this.notCommisionPresentage = notCommisionPresentage;
    }

    public BigDecimal getCommisionPresentage() {
        return commisionPresentage;
    }

    public void setCommisionPresentage(BigDecimal commisionPresentage) {
        this.commisionPresentage = commisionPresentage;
    }

    public BigDecimal getSalaryPresentage() {
        return salaryPresentage;
    }

    public void setSalaryPresentage(BigDecimal salaryPresentage) {
        this.salaryPresentage = salaryPresentage;
    }
}
