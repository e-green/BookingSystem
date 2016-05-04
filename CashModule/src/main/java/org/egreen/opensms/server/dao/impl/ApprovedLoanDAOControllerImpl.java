package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.ApprovedLoanDAOController;
import org.egreen.opensms.server.entity.ApprovedLoan;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
        String hql = "delete from Branch b where b.branchid= :branchid";
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

    /**
     *
     * Check is there already have unfinished Approved loan with specific Center, specific individual
     * @param centerid
     * @param individualId
     * @return
     */
    @Override
    public boolean checkApprovedLoanDueAmountsZero(String centerid, String individualId) {
        Query query = getSession().createQuery("SELECT a FROM ApprovedLoan a WHERE a.center = :centerid AND a.individualId = :individualId AND a.dueamount > 1.0");
        query.setString("centerid", centerid);
        query.setString("individualId", individualId);
        List<ApprovedLoan> approvedLoanList = query.list();
        boolean isOk=false;
        if(approvedLoanList.size() > 0){
            isOk=false;
        }else{
            isOk=true;
        }
        return isOk;
    }

    @Override
    public List<ApprovedLoan> getAllUnpaidLoans() {
        Query query = getSession().createQuery("SELECT a FROM ApprovedLoan a WHERE a.dueamount > 0.0");
        List<ApprovedLoan> unpaidLoanList = query.list();
        return unpaidLoanList;
    }

    @Override
    public ApprovedLoan getApprovedLoanByDateNIndividualId(String formatedDate, String individualId) {
        Query query = getSession().createQuery("SELECT a FROM ApprovedLoan a WHERE a.individualId = :individualId AND DATE(a.datetime) = :date");
        query.setString("date", formatedDate);
        query.setString("individualId", individualId);

        /**
         * please use below code when you try to get with unique string Id(date)
         */
//        Query query = getSession().createQuery("SELECT a FROM ApprovedLoan a WHERE a.individualId = :individualId AND a.sTime = :date");
//        query.setString("date", formatedDate);
//        query.setString("individualId", individualId);
//
        ApprovedLoan approvedLoan=null;
        List<ApprovedLoan>  approvedLoanList= query.list();
        if(approvedLoanList.size() > 0){
            for (ApprovedLoan aLoan:approvedLoanList) {
                approvedLoan=aLoan;
            }
        }

        return approvedLoan;
    }

    @Override
    public List<ApprovedLoan> getUnpaidLoansByIndividualId(String individualId) {
        Query query = getSession().createQuery("SELECT a FROM ApprovedLoan a WHERE a.individualId = :individualId AND a.dueamount >0.0");
        query.setString("individualId", individualId);

        return query.list();
    }

    @Override
    public List<ApprovedLoan> getAllApprovedLoansByIndividualId(String individualId, Integer limit, Integer offset) {
        Criteria criteria = getSession().createCriteria(entityType);
        criteria.add(Restrictions.eq("individualId", individualId));
        criteria.setFirstResult(offset);
        criteria.setMaxResults(limit);
        return criteria.list();

    }

    @Override
    public ApprovedLoan getOpenLoanDetailByCenterlId(String centerId) {
        Criteria criteria = getSession().createCriteria(entityType);
        criteria.add(Restrictions.eq("center", centerId));
        criteria.add(Restrictions.eq("individualId", "0"));
        criteria.add(Restrictions.ne("dueamount", BigDecimal.ZERO));

        if (criteria.list()!=null && criteria.list().size()>0){
            return (ApprovedLoan)criteria.list().get(0);
        }
        return null;
    }
}
