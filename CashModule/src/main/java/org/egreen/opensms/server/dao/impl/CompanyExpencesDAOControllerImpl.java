package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.CompanyExpencesDAOController;
import org.egreen.opensms.server.entity.CompanyExpences;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */

@Repository
public class CompanyExpencesDAOControllerImpl extends AbstractDAOController<CompanyExpences, String> implements CompanyExpencesDAOController {
    public CompanyExpencesDAOControllerImpl() {
        super(CompanyExpences.class, String.class);
    }


    @Override
    public List<CompanyExpences> getAllCenterersByPagination(String quary, Integer limit, Integer offset) {

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
    public List<CompanyExpences> getAllCentersByBranchId(String branchid) {
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
    public List<CompanyExpences> getAllCompanyExpencessByCenter(String center, String firstDate, String secondDate, Integer limit, Integer offset) {
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println(firstDate);
        System.out.println(secondDate);

        Date fDa = null;
        Date sDa = null;

        try {
            fDa = sm.parse(firstDate);
            sDa = sm.parse(secondDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Criteria criteria = getSession().createCriteria(entityType);
        criteria.add(Restrictions.eq("center", center));
        criteria.add(Restrictions.between("date", fDa, sDa));

        if (limit != null && offset != null) {
            criteria.setMaxResults(limit);
            criteria.setFirstResult(offset);
        }
        return criteria.list();
    }
}
