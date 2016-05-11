package org.egreen.opensms.server.dao;

import org.egreen.opensms.server.entity.Permission;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
public interface PermissionDAOController extends DAOController<Permission,String> {



    Integer removePermission(String permissionId);

    boolean getPermissionIsAvailable(String adminId, String permission);
}
