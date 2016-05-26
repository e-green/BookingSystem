package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.ApprovedLoanDAOController;
import org.egreen.opensms.server.dao.LoanRequestDAOController;
import org.egreen.opensms.server.entity.ApprovedLoan;
import org.egreen.opensms.server.entity.LoanRequest;
import org.egreen.opensms.server.models.LoanRequestModel;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
@Service
public class LoanRequestDAOService {

    @Autowired
    private LoanRequestDAOController loanRequestDAOController;


    /**
     * loanRequest SignUp
     *
     * @param loanRequest
     * @return
     */
    public String save(LoanRequest loanRequest) {
        System.out.println("S Timeeeeeeeeee :" +loanRequest.getsTime());
        String res=null;
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        loanRequest.setLoanRequestId(newid);
        loanRequest.setRequestDate(new Timestamp(new Date().getTime()));
        loanRequestDAOController.create(loanRequest);
        res="3";
        return res;
    }

    //get all loan requests
    public List<LoanRequest> getAll() {
        return loanRequestDAOController.getAll();
    }

    /**
     * update LoanRequest
     *
     * @param loanRequest
     * @return
     */
    public String update(LoanRequest loanRequest) {
        return loanRequestDAOController.update(loanRequest);
    }

    public LoanRequest getloanRequestById(String centerid) {
        return loanRequestDAOController.read(centerid);
    }

    /**
     * get All LoanRequests By UserId
     *
     * @param userId
     * @param limit
     * @param offset
     * @return
     */
    public List<LoanRequest> getAllLoanRequestsByUserId(String userId,Integer limit,Integer offset) {
        return loanRequestDAOController.getAllLoanRequestsByUserId(userId, limit, offset);
    }

    /**
     * get All Loan Request By Pagination
     *
     * @param type
     * @param limit
     * @param offset
     * @return
     */
    public List<LoanRequest> getAllLoanRequestByPagination(Integer type, Integer limit, Integer offset) {
        return loanRequestDAOController.getAllLoanRequestByPagination(type, limit, offset);
    }

    /**
     * get All Paid Loans and Due Loans By Center Id and IndividualId
     *
     * @param centerId
     * @param individualId
     * @param limit
     * @param offset
     * @return
     */
    public List<ApprovedLoan> getAllPaidLoansNDueLoansByCenterIdNIndividualId(String centerId, String individualId, Integer limit, Integer offset) {
        return loanRequestDAOController.getAllPaidLoansNDueLoansByCenterIdNIndividualId(centerId,individualId,limit,offset);
    }

    public String getId() {
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(2);
        return newid;
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


}
