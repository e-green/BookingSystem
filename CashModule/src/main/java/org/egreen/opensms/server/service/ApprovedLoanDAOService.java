package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.ApprovedLoanDAOController;


import org.egreen.opensms.server.entity.ApprovedLoan;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
@Service
public class ApprovedLoanDAOService {

    @Autowired
    private ApprovedLoanDAOController approvedLoanDAOController;
    
    private List<ApprovedLoan> all;
    private String id;


    /**
     *
     * approvedLoan SignUp
     *
     * @param approvedLoan
     * @return
     */
    public String save(ApprovedLoan approvedLoan) {
        String id = new Date().getTime()+"";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
//        if (approvedLoan.getName()!=null) {
//            approvedLoan.setBranchid(approvedLoan.getName());
//        }
        approvedLoan.setApprovedloanId(newid);

        String s = approvedLoanDAOController.create(approvedLoan);
        return s;
    }


    /**
     * Get Random String
     *
     * @param len
     * @return
     * @author Pramoda Nadeeshan Fernando
     * @version 1.0
     * @since 2015-02-12 4.26PM
     */
    private String randomString(int len) {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }


    public List<ApprovedLoan> getAll() {
        return approvedLoanDAOController.getAll();
    }

    public List<ApprovedLoan> getAllBranchersByPagination(Integer limit, Integer offset) {
        return approvedLoanDAOController.getAllBranchersByPagination(limit, offset);
    }

    public String update(ApprovedLoan approvedLoan) {
        return approvedLoanDAOController.update(approvedLoan);
    }

    public Integer removeBranchById(String approvedLoanId) {
        return approvedLoanDAOController.removeBranchById(approvedLoanId) ;
    }

    public String getId() {
        String id = new Date().getTime()+"";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        return newid;
    }

    public ApprovedLoan getBranchById(String approvedLoanid) {

        return approvedLoanDAOController.read(approvedLoanid);
    }

    public String getNextID() {
        return approvedLoanDAOController.getNextId();
    }

    public ApprovedLoan getOpenLoanDetailByIndividualId(String individualId) {
        return approvedLoanDAOController.getOpenLoanDetailByIndividualId(individualId);
    }
}
