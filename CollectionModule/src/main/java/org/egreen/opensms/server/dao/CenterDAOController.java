package org.egreen.opensms.server.dao;

import org.egreen.opensms.server.entity.Center;

import java.util.List;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
public interface CenterDAOController extends DAOController<Center,String> {

    List<Center> getAllCenterersByPagination(String quary,Integer limit, Integer offset);

    Integer removeCenterById(String branchId);

    List<Center> getAllCentersByBranchId(String branchid);

    long getCenterCountByBranch(String branchID);

    boolean checkIfExist(String centerName);
}
