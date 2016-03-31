package org.egreen.opensms.server.models;

import java.math.BigDecimal;

/**
 * Created by ruwan on 3/29/16.
 */
public class AccountDetail {

    String individualId;
    BigDecimal amount;

    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "AccountDetail{" +
                "individualId='" + individualId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
