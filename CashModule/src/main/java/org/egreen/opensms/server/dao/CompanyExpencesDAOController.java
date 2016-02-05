package org.egreen.opensms.server.dao;

import org.egreen.opensms.server.entity.CompanyExpences;

import java.util.List;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
public interface CompanyExpencesDAOController extends DAOController<CompanyExpences,String> {

    List<CompanyExpences> getAllCenterersByPagination(String quary, Integer limit, Integer offset);

    Integer removeCenterById(String branchId);

    List<CompanyExpences> getAllCentersByBranchId(String branchid);

    long getCenterCountByBranch(String branchID);

    List<CompanyExpences> getAllCompanyExpencessByCenter(String center, String firstDate, String secondDate, Integer limit, Integer offset);

}
