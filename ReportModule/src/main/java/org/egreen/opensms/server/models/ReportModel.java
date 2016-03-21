package org.egreen.opensms.server.models;

import java.sql.Timestamp;

/**
 * Created by ruwan on 3/21/16.
 */
public class ReportModel {

    String centerId;
    String individualId;
    Timestamp date;
    Integer type;

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ReportModel{" +
                "centerId='" + centerId + '\'' +
                ", individualId='" + individualId + '\'' +
                ", date=" + date +
                ", type=" + type +
                '}';
    }
}
