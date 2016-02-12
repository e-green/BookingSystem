package org.egreen.opensms.server.models;

import org.egreen.opensms.server.entity.ApprovedLoan;

/**
 * Created by pramoda-nf on 9/3/15.
 */
public class CenterModell extends ApprovedLoan {
    private String branchName;


    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
