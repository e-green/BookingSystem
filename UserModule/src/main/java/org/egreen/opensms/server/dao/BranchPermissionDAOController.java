package org.egreen.opensms.server.dao;

import org.egreen.opensms.server.entity.BranchPermission;
import org.egreen.opensms.server.entity.Permission;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
public interface BranchPermissionDAOController extends DAOController<BranchPermission,String> {



    Integer removePermission(String permissionId);

    Boolean checkBranchPermission(String adminid, String branchId);
}
