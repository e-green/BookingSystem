package org.egreen.opensms.server.models;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ruwan on 6/4/16.
 */
public class IndividualReportModel {
    private String sTime;
    private String individual;
    private BigDecimal pc;
    private BigDecimal ln;
    private BigDecimal pd;
    private BigDecimal com;
    private BigDecimal nc;
    private BigDecimal lcs;
    private BigDecimal lon;
    private BigDecimal sal;
    private BigDecimal overPay;
    private BigDecimal exces;
    private BigDecimal exp;
    private BigDecimal rent;
    private BigDecimal csh;
    private BigDecimal totInv;
    private BigDecimal totPay;
    private BigDecimal tpyPay;
    private BigDecimal tpyInv;
    private BigDecimal due;
    private String dueLable;
    private BigDecimal payment;
    private String paymentLable;
    private BigDecimal tpyInvDeduct;
    private BigDecimal tpyPayDeduct;
    private BigDecimal loanDue;
    private List<ReportChitModel> reportChitModelList;

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String getIndividual() {
        return individual;
    }

    public void setIndividual(String individual) {
        this.individual = individual;
    }

    public BigDecimal getPc() {
        return pc;
    }

    public void setPc(BigDecimal pc) {
        this.pc = pc;
    }

    public BigDecimal getLn() {
        return ln;
    }

    public void setLn(BigDecimal ln) {
        this.ln = ln;
    }

    public BigDecimal getPd() {
        return pd;
    }

    public void setPd(BigDecimal pd) {
        this.pd = pd;
    }

    public BigDecimal getCom() {
        return com;
    }

    public void setCom(BigDecimal com) {
        this.com = com;
    }

    public BigDecimal getNc() {
        return nc;
    }

    public void setNc(BigDecimal nc) {
        this.nc = nc;
    }

    public BigDecimal getLcs() {
        return lcs;
    }

    public void setLcs(BigDecimal lcs) {
        this.lcs = lcs;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getSal() {
        return sal;
    }

    public void setSal(BigDecimal sal) {
        this.sal = sal;
    }

    public BigDecimal getOverPay() {
        return overPay;
    }

    public void setOverPay(BigDecimal overPay) {
        this.overPay = overPay;
    }

    public BigDecimal getExces() {
        return exces;
    }

    public void setExces(BigDecimal exces) {
        this.exces = exces;
    }

    public BigDecimal getExp() {
        return exp;
    }

    public void setExp(BigDecimal exp) {
        this.exp = exp;
    }

    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    public BigDecimal getCsh() {
        return csh;
    }

    public void setCsh(BigDecimal csh) {
        this.csh = csh;
    }

    public BigDecimal getTotInv() {
        return totInv;
    }

    public void setTotInv(BigDecimal totInv) {
        this.totInv = totInv;
    }

    public BigDecimal getTotPay() {
        return totPay;
    }

    public void setTotPay(BigDecimal totPay) {
        this.totPay = totPay;
    }

    public BigDecimal getTpyPay() {
        return tpyPay;
    }

    public void setTpyPay(BigDecimal tpyPay) {
        this.tpyPay = tpyPay;
    }

    public BigDecimal getTpyInv() {
        return tpyInv;
    }

    public void setTpyInv(BigDecimal tpyInv) {
        this.tpyInv = tpyInv;
    }

    public BigDecimal getDue() {
        return due;
    }

    public void setDue(BigDecimal due) {
        this.due = due;
    }

    public String getDueLable() {
        return dueLable;
    }

    public void setDueLable(String dueLable) {
        this.dueLable = dueLable;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getPaymentLable() {
        return paymentLable;
    }

    public void setPaymentLable(String paymentLable) {
        this.paymentLable = paymentLable;
    }

    public BigDecimal getTpyInvDeduct() {
        return tpyInvDeduct;
    }

    public void setTpyInvDeduct(BigDecimal tpyInvDeduct) {
        this.tpyInvDeduct = tpyInvDeduct;
    }

    public BigDecimal getTpyPayDeduct() {
        return tpyPayDeduct;
    }

    public void setTpyPayDeduct(BigDecimal tpyPayDeduct) {
        this.tpyPayDeduct = tpyPayDeduct;
    }

    public BigDecimal getLoanDue() {
        return loanDue;
    }

    public void setLoanDue(BigDecimal loanDue) {
        this.loanDue = loanDue;
    }

    public List<ReportChitModel> getReportChitModelList() {
        return reportChitModelList;
    }

    public void setReportChitModelList(List<ReportChitModel> reportChitModelList) {
        this.reportChitModelList = reportChitModelList;
    }
}
