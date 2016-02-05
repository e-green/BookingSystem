package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.IndividualDAOController;
import org.egreen.opensms.server.entity.Individual;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */

@Repository
public class IndividualDAOControllerImpl extends AbstractDAOController<Individual, String> implements IndividualDAOController {
    public IndividualDAOControllerImpl() {
        super(Individual.class, String.class);
    }


    @Override
    public List<Individual> getAllBranchersByPagination(Integer limit, Integer offset) {

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
