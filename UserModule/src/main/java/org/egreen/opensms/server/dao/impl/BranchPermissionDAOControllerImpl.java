package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.BranchPermissionDAOController;
import org.egreen.opensms.server.dao.PermissionDAOController;
import org.egreen.opensms.server.entity.BranchPermission;
import org.egreen.opensms.server.entity.Permission;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */

@Repository
public class BranchPermissionDAOControllerImpl extends AbstractDAOController<BranchPermission,String> implements BranchPermissionDAOController {
    public BranchPermissionDAOControllerImpl() {
        super(BranchPermission.class, String.class);
    }




    @Override
    public Integer removePermission(String branchpermissionid) {
        Session session = getSession();
        String hql = "delete from BranchPermission where branchpermissionid= :branchpermissionid";
        int i = session.createQuery(hql).setString("branchpermissionid", branchpermissionid).executeUpdate();
        return i;
    }

    @Override
    public Boolean checkBranchPermission(String adminid, String branchId) {
        Criteria criteria = getSession().createCriteria(entityType);
        criteria.add(Restrictions.eq("adminId", adminid));
        criteria.add(Restrictions.eq("branchId", branchId));
        if ( criteria.list().size()>0) {

            BranchPermission o = (BranchPermission) criteria.list().get(0);
            if (o != null) {
                return true;
            } else {
                return false;
            }

        }else {
            return false;
        }

    }
}
