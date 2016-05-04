package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by ruwan on 2/11/16.
 */
@Entity
@Table(name = "transaction")
@JsonIgnoreProperties
public class Transaction implements EntityInterface <String> {

    private String transactionId;
    private String typeId;
    private String accountNo;
    private BigDecimal debit;
    private BigDecimal credit;
    private Timestamp time;
    private Timestamp transactionTime;
    private String sTime;

    @Id
    @Column(name = "transaction")
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Timestamp getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Timestamp transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    @Override
    @Transient
    public String getId() {
        return getTransactionId();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", typeId='" + typeId + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", debit=" + debit +
                ", credit=" + credit +
                ", time=" + time +
                ", transactionTime=" + transactionTime +
                ", sTime='" + sTime + '\'' +
                '}';
    }
}
