package org.egreen.opensms.server.dao;

import org.egreen.opensms.server.entity.LoanRequest;

import java.util.List;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
public interface LoanRequestDAOController extends DAOController<LoanRequest,String> {

    List<LoanRequest> getAllCenterersByPagination(String quary,Integer limit, Integer offset);

    Integer removeCenterById(String branchId);

    List<LoanRequest> getAllCentersByBranchId(String branchid);

    long getCenterCountByBranch(String branchID);

    List<LoanRequest> getAllLoanRequestsByUserId(String userId, Integer limit, Integer offset);

    List<LoanRequest> getAllLoanRequestByPagination(Integer type, Integer limit, Integer offset);
}
