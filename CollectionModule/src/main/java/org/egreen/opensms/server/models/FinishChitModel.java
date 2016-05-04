package org.egreen.opensms.server.models;

import java.sql.Timestamp;

/**
 * Created by ruwan on 3/9/16.
 */
public class FinishChitModel {
    private String envelopeId;
    private Timestamp timestamp;
    private String sTime;

    public String getEnvelopeId() {
        return envelopeId;
    }

    public void setEnvelopeId(String envelopeId) {
        this.envelopeId = envelopeId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    @Override
    public String toString() {
        return "FinishChitModel{" +
                "envelopeId='" + envelopeId + '\'' +
                ", timestamp=" + timestamp +
                ", sTime='" + sTime + '\'' +
                '}';
    }
}
