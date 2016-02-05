package org.egreen.opensms.server.models;

/**
 * Created by pramoda-nf on 9/8/15.
 */
public class SampleGuaranteeModel {


    private String guaranteerecordid;
    private String customerName;
    private String guaranteeName;

    public String getGuaranteerecordid() {
        return guaranteerecordid;
    }

    public void setGuaranteerecordid(String guaranteerecordid) {
        this.guaranteerecordid = guaranteerecordid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getGuaranteeName() {
        return guaranteeName;
    }

    public void setGuaranteeName(String guaranteeName) {
        this.guaranteeName = guaranteeName;
    }
}
