package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.ApprovedLoanDAOController;
import org.egreen.opensms.server.entity.ApprovedLoan;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */

@Repository
public class ApprovedLoanDAOControllerImpl extends AbstractDAOController<ApprovedLoan, String> implements ApprovedLoanDAOController {
    public ApprovedLoanDAOControllerImpl() {
        super(ApprovedLoan.class, String.class);
    }


    @Override
    public List<ApprovedLoan> getAllBranchersByPagination(Integer limit, Integer offset) {

        Criteria criteria = getSession().createCriteria(entityType);
        criteria.setFirstResult(offset);
        criteria.setMaxResults(limit);
        return criteria.list();
    }

    @Override
    public Integer removeBranchById(String branchid) {
        Session session = getSession();
        String hql = "delete from Branch where branchid= :branchid";
        int i = session.createQuery(hql).setString("branchid", branchid).executeUpdate();
        return i;
    }

    @Override
    public String getNextId() {
        return getAllCount() + "";
    }

    @Override
    public ApprovedLoan getOpenLoanDetailByIndividualId(String individualId) {
        Criteria criteria = getSession().createCriteria(entityType);
        criteria.add(Restrictions.eq("individualId", individualId));
        criteria.add(Restrictions.ne("dueamount", BigDecimal.ZERO));

        if (criteria.list()!=null && criteria.list().size()>0){

            return (ApprovedLoan)criteria.list().get(0);

        }
        return null;
    }
}
