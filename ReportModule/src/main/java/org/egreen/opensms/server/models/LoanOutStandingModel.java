package org.egreen.opensms.server.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by pramoda-nf on 11/3/15.
 */
public class LoanOutStandingModel {

    private String memberId;//
    private String groupId;//
    private String memberCode;//
    private String memberName;//
    private String memberNic;//
    private String memberTPNum;//
    private String loanCode;//
    private Timestamp loanDate;//
    private Timestamp lastPayDate;//
    private BigDecimal due;//
    private BigDecimal loanAmount;//
    private BigDecimal payAmount;//
    private BigDecimal balance;//
    private BigDecimal capBalance;//
    private BigDecimal insBalance;


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberNic() {
        return memberNic;
    }

    public void setMemberNic(String memberNic) {
        this.memberNic = memberNic;
    }

    public String getMemberTPNum() {
        return memberTPNum;
    }

    public void setMemberTPNum(String memberTPNum) {
        this.memberTPNum = memberTPNum;
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public Timestamp getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Timestamp loanDate) {
        this.loanDate = loanDate;
    }

    public Timestamp getLastPayDate() {
        return lastPayDate;
    }

    public void setLastPayDate(Timestamp lastPayDate) {
        this.lastPayDate = lastPayDate;
    }

    public BigDecimal getDue() {
        return due;
    }

    public void setDue(BigDecimal due) {
        this.due = due;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getCapBalance() {
        return capBalance;
    }

    public void setCapBalance(BigDecimal capBalance) {
        this.capBalance = capBalance;
    }

    public BigDecimal getInsBalance() {
        return insBalance;
    }

    public void setInsBalance(BigDecimal insBalance) {
        this.insBalance = insBalance;
    }
}
