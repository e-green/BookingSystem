package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Pramoda Fernando on 3/18/2015.
 */
@Entity
@Table(name = "companyexpences")
@JsonIgnoreProperties
public class CompanyExpences implements EntityInterface<String> {


    private String companyexpencesId;
    private Timestamp date;
    private String accountNo;
    private String reason;
    private BigDecimal amount;
    private String sTime;


    @Id
    @Column(name = "companyexpencesId")
    public String getCompanyexpencesId() {
        return companyexpencesId;
    }

    public void setCompanyexpencesId(String companyexpencesId) {
        this.companyexpencesId = companyexpencesId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
        return getCompanyexpencesId();
    }

    @Override
    public String toString() {
        return "CompanyExpences{" +
                "companyexpencesId='" + companyexpencesId + '\'' +
                ", date=" + date +
                ", accountNo='" + accountNo + '\'' +
                ", reason='" + reason + '\'' +
                ", amount=" + amount +
                ", sTime='" + sTime + '\'' +
                '}';
    }
}
