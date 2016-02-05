package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.PermissionDAOController;
import org.egreen.opensms.server.entity.Permission;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */

@Repository
public class PermissionDAOControllerImpl extends AbstractDAOController<Permission,String> implements PermissionDAOController {
    public PermissionDAOControllerImpl() {
        super(Permission.class, String.class);
    }




    @Override
    public Integer removePermission(String permissionid) {
        Session session = getSession();
        String hql = "delete from Permission where permissionid= :permissionid";
        int i = session.createQuery(hql).setString("permissionid", permissionid).executeUpdate();
        return i;
    }
}
