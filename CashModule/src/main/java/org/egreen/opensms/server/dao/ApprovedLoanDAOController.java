package org.egreen.opensms.server.dao;

import org.egreen.opensms.server.entity.ApprovedLoan;

import java.util.List;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
public interface ApprovedLoanDAOController extends DAOController<ApprovedLoan,String> {

    List<ApprovedLoan> getAllBranchersByPagination(Integer limit, Integer offset);

    Integer removeBranchById(String branchId);

    String getNextId();

    ApprovedLoan getOpenLoanDetailByIndividualId(String individualId);
}
