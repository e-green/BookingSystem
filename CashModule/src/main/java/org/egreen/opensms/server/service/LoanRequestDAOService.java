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

    @Autowired
    private ApprovedLoanDAOController approvedLoanDAOController;

    private List<LoanRequest> all;
    private String id;


    /**
     * loanRequest SignUp
     *
     * @param loanRequest
     * @return
     */
    public String save(LoanRequest loanRequest) {
        boolean canRequest=loanRequestDAOController.checkIsThereAlreadyRequestedLoanHaveSpecifiedCenterIndividual(loanRequest.getCenterid(),loanRequest.getIndividualId());
        String res=null;
        if(canRequest == true){
            boolean isOk=approvedLoanDAOController.checkApprovedLoanDueAmountsZero(loanRequest.getCenterid(),loanRequest.getIndividualId());
            if(isOk == true){
                String id = new Date().getTime() + "";
                Hashids hashids = new Hashids(id);
                String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
                String newid = hexaid + "" + randomString(10);
                loanRequest.setLoanRequestId(newid);
                loanRequest.setRequestDate(new Timestamp(new Date().getTime()));
                loanRequestDAOController.create(loanRequest);
                res="3";
            }else{
                res="2";
            }
        }else{
            res="1";
        }

        return res;
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


    public List<LoanRequest> getAll() {
        return loanRequestDAOController.getAll();
    }
//
//    public List<LoanRequest> getAllloanRequestByPagination(String quary, Integer limit, Integer offset) {
//        return loanRequestDAOController.getAllloanRequestersByPagination(quary, limit, offset);
//    }

    public String update(LoanRequest loanRequest) {
        return loanRequestDAOController.update(loanRequest);
    }

//    public Integer removeloanRequestById(String centerid) {
//        return loanRequestDAOController.removeloanRequestById(centerid);
//    }

    public String getId() {
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(2);
        return newid;
    }
//
//    public List<LoanRequest> getAllloanRequestsByBranchId(String branchid) {
////
////        return loanRequestDAOController.getAllloanRequestsByBranchId(branchid);
//    }

//    public String getNextId(String branchID) {
////        Branch branch = branchDAOController.read(branchID);
//        String centerCode=null;
//        long centerCount = loanRequestDAOController.getloanRequestCountByBranch(branchID);
//        centerCount++;
//        LoanRequest branch = branchDAOController.read(branchID);
//        if (branch!=null) {
//            String city=branch.getName().substring(0, 3);
//           centerCode = city + "/" + centerCount;
//        }
//        return centerCode;
//    }

    public LoanRequest getloanRequestById(String centerid) {
        return loanRequestDAOController.read(centerid);
    }

    public List<LoanRequest> getAllLoanRequestsByUserId(String userId,Integer limit,Integer offset) {
        return loanRequestDAOController.getAllLoanRequestsByUserId(userId, limit, offset);
    }

    public List<LoanRequest> getAllLoanRequestByPagination(Integer type, Integer limit, Integer offset) {
        return loanRequestDAOController.getAllLoanRequestByPagination(type, limit, offset);
    }

    public List<ApprovedLoan> getAllPaidLoansNDueLoansByCenterIdNIndividualId(String centerId, String individualId, Integer limit, Integer offset) {
        return loanRequestDAOController.getAllPaidLoansNDueLoansByCenterIdNIndividualId(centerId,individualId,limit,offset);
    }
}
