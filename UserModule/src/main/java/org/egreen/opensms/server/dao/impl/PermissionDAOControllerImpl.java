package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.PermissionDAOController;
import org.egreen.opensms.server.entity.Permission;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public boolean getPermissionIsAvailable(String adminId, String permission) {
        Criteria criteria = getSession().createCriteria(entityType);

        criteria.add(Restrictions.eq("adminId", adminId));
        criteria.add(Restrictions.eq("permission", permission));
        boolean b=false;
        List<Permission> list = criteria.list();
        if(!list.isEmpty()){
            b=true;
        }
        return b;

    }
}
