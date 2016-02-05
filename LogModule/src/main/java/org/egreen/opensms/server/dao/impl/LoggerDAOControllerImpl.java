package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.LoggerDAOController;
import org.egreen.opensms.server.entity.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */

@Repository
public class LoggerDAOControllerImpl extends AbstractDAOController<Logger, String> implements LoggerDAOController {
    public LoggerDAOControllerImpl() {
        super(Logger.class, String.class);
    }


    @Override
    public List<Logger> getAllCenterersByPagination(String quary, Integer limit, Integer offset) {

        Criteria criteria = getSession().createCriteria(entityType);
        criteria.add(Restrictions.like("name", quary, MatchMode.START));
        if (limit!=null&&offset!=null) {
            criteria.setFirstResult(offset);
            criteria.setMaxResults(limit);
        }
        return criteria.list();
    }

    @Override
    public Integer removeCenterById(String centerid) {
        Session session = getSession();
        String hql = "delete from Center where centerid= :centerid";
        int i = session.createQuery(hql).setString("centerid", centerid).executeUpdate();
        return i;
    }

    @Override
    public List<Logger> getAllCentersByBranchId(String branchid) {
        Criteria criteria = getSession().createCriteria(entityType);
        criteria.add(Restrictions.eq("branchid", branchid));
        return criteria.list();
    }

    @Override
    public long getCenterCountByBranch(String branchID) {
        Criteria criteria = getSession().createCriteria(entityType);
        criteria.add(Restrictions.eq("branchid", branchID));
        List list = criteria.list();
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public boolean checkIfExist(String centerName) {
        Criteria criteria = getSession().createCriteria(entityType);
        criteria.add(Restrictions.eq("name", centerName));
        int size = criteria.list().size();
        if (size>0){
            return true;
        }else {
            return false;
        }
    }
}
