package org.egreen.opensms.server.dao;

import org.egreen.opensms.server.entity.Logger;

import java.util.List;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
public interface LoggerDAOController extends DAOController<Logger,String> {

    List<Logger> getAllCenterersByPagination(String quary,Integer limit, Integer offset);

    Integer removeCenterById(String branchId);

    List<Logger> getAllCentersByBranchId(String branchid);

    long getCenterCountByBranch(String branchID);

    boolean checkIfExist(String centerName);
}
