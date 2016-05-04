package org.egreen.opensms.server.models;

import java.sql.Timestamp;

/**
 * Created by ruwan on 2/9/16.
 */
public class EnvelopeModel {
    private String center;
    private String individualId;
    private Timestamp date;
    private Integer limit;
    private Integer offset;
    private String sTime;


    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

    @Override
    public String toString() {
        return "EnvelopeModel{" +
                "center='" + center + '\'' +
                ", individualId='" + individualId + '\'' +
                ", date=" + date +
                ", limit=" + limit +
                ", offset=" + offset +
                ", sTime='" + sTime + '\'' +
                '}';
    }
}
