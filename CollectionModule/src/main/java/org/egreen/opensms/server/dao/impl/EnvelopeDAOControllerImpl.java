package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.EnvelopeDAOController;
import org.egreen.opensms.server.entity.Envelope;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */

@Repository
public class EnvelopeDAOControllerImpl extends AbstractDAOController<Envelope, String> implements EnvelopeDAOController {
    public EnvelopeDAOControllerImpl() {
        super(Envelope.class, String.class);
    }


    @Override
    public List<Envelope> getAllBranchersByPagination(Integer limit, Integer offset) {

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

    @Override
    public List<Envelope> getEnvelopesByCenterId(String centerId,
                                                 Integer limit,
                                                 Integer offset,
                                                 Date date) {
        //SELECT * FROM `envelop` WHERE `center` = 'Alu'AND DATE(`date`) = '2015-12-30'

        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.center = :centerId AND DATE(e.date) = :date");
        query.setDate("date", date);
        query.setString("centerId", centerId);

        if (limit!=null&&offset!=null) {
            query.setMaxResults(limit);
            query.setFirstResult(offset);
        }

        return query.list();

    }

    @Override
    public List<Envelope> getEnvelopesByIndividualIdByDate(String individualId, Integer limit, Integer offset, Date date) {
        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.individualId = :individualId AND DATE(e.date) = :date");
        query.setDate("date", date);
        query.setString("individualId", individualId);

        if (limit!=null&&offset!=null) {
            query.setMaxResults(limit);
            query.setFirstResult(offset);
        }

        return query.list();
    }


}
