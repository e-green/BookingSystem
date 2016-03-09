package org.egreen.opensms.server.models;

import java.sql.Timestamp;

/**
 * Created by ruwan on 3/9/16.
 */
public class FinishChitModel {
    String envelopeId;
    Timestamp timestamp;

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

    @Override
    public String toString() {
        return "FinishChitModel{" +
                "envelopeId='" + envelopeId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
