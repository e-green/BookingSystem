package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.LoanRequestDAOController;
import org.egreen.opensms.server.entity.ApprovedLoan;
import org.egreen.opensms.server.entity.LoanRequest;
import org.egreen.opensms.server.models.LoanRequestModel;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */

@Repository
public class LoanRequestDAOControllerImpl extends AbstractDAOController<LoanRequest, String> implements LoanRequestDAOController {
    public LoanRequestDAOControllerImpl() {
        super(LoanRequest.class, String.class);
    }


    @Override
    public List<LoanRequest> getAllCenterersByPagination(String quary, Integer limit, Integer offset) {

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
    public List<LoanRequest> getAllCentersByBranchId(String branchid) {
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
    public List<LoanRequest> getAllLoanRequestsByUserId(String userId, Integer limit, Integer offset) {
        Criteria criteria = getSession().createCriteria(entityType);
        criteria.add(Restrictions.eq("user", userId));
        if (limit!=null&&offset!=null) {
            criteria.setFirstResult(offset);
            criteria.setMaxResults(limit);
        }
        return criteria.list();
    }

    @Override
    public List<LoanRequest> getAllLoanRequestByPagination(Integer type, Integer limit, Integer offset) {
        Criteria criteria = getSession().createCriteria(entityType);
        if (type==0){
            criteria.add(Restrictions.eq("status", false));
        }else if(type==1){
            criteria.add(Restrictions.eq("status", true));
        }

        if (limit!=null&&offset!=null) {
            criteria.setFirstResult(offset);
            criteria.setMaxResults(limit);
        }
        return criteria.list();

    }

    /**
     * Check is there already have not Approved loan with specific Center, specific individual
     *
     * @param centerid
     * @param individualId
     * @return
     * @author ruwan
     */
    @Override
    public boolean checkIsThereAlreadyRequestedLoanHaveSpecifiedCenterIndividual(String centerid, String individualId) {
        Query query = getSession().createQuery("SELECT lr FROM LoanRequest lr WHERE lr.centerid = :centerid AND lr.individualId = :individualId AND lr.status = false");
        query.setString("centerid", centerid);
        query.setString("individualId", individualId);
        List<LoanRequest> loanRequestList = query.list();
        boolean isOk=false;
        if(loanRequestList.size() > 0){
            isOk=false;
        }else{
            isOk=true;
        }
        return isOk;
    }

    @Override
    public List<ApprovedLoan> getAllPaidLoansNDueLoansByCenterIdNIndividualId(String centerId, String individualId, Integer limit, Integer offset) {
        Query query = getSession().createQuery("SELECT a FROM ApprovedLoan a WHERE a.center = :centerid AND a.individualId = :individualId");
        query.setString("centerid", centerId);
        query.setString("individualId", individualId);
        return query.list();
    }
}
