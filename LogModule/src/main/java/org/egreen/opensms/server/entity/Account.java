package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by ruwan on 2/11/16.
 */
@Entity
@Table(name = "account")
@JsonIgnoreProperties
public class Account implements EntityInterface <String>{

    private String accountNo;
    private BigDecimal amount;
    private String memberId;

    @Id
    @Column(name = "accountNo")
    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    @Transient
    public String getId() {
        return getAccountNo();
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNo='" + accountNo + '\'' +
                ", amount='" + amount + '\'' +
                ", memberId='" + memberId + '\'' +
                '}';
    }
}
