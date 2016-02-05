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
    private String center;
    private String reason;
    private BigDecimal amount;


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


    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
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
                ", center='" + center + '\'' +
                ", reason='" + reason + '\'' +
                ", amount=" + amount +
                '}';
    }
}
