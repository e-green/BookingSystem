package org.egreen.opensms.server.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by ruwan on 3/16/16.
 */
public class FinishEnvelopeModel {
    BigDecimal investment;
    BigDecimal cash;
    String chitRange;
    Timestamp date;
    boolean canAdd;

    public BigDecimal getInvestment() {
        return investment;
    }

    public void setInvestment(BigDecimal investment) {
        this.investment = investment;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public String getChitRange() {
        return chitRange;
    }

    public void setChitRange(String chitRange) {
        this.chitRange = chitRange;
    }

    public boolean isCanAdd() {
        return canAdd;
    }

    public void setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "FinishEnvelopeModel{" +
                "investment=" + investment +
                ", cash=" + cash +
                ", chitRange='" + chitRange + '\'' +
                ", date=" + date +
                ", canAdd=" + canAdd +
                '}';
    }
}
