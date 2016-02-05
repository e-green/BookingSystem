package org.egreen.opensms.server.models;


/**
 * Created by pramoda-nf on 9/10/15.
 */
public class LoanModel {

    private String branchName;
    private String centerName;
    private String customerName;

    private String guarante1Name;
    private String guarante2Name;
    private String packageName;



    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getGuarante1Name() {
        return guarante1Name;
    }

    public void setGuarante1Name(String guarante1Name) {
        this.guarante1Name = guarante1Name;
    }

    public String getGuarante2Name() {
        return guarante2Name;
    }

    public void setGuarante2Name(String guarante2Name) {
        this.guarante2Name = guarante2Name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
