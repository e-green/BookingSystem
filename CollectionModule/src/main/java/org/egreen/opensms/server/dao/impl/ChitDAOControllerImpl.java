package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.ChitDAOController;
import org.egreen.opensms.server.entity.Chit;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */

@Repository
public class ChitDAOControllerImpl extends AbstractDAOController<Chit, String> implements ChitDAOController {
    public ChitDAOControllerImpl() {
        super(Chit.class, String.class);
    }


    @Override
    public List<Chit> getAllBranchersByPagination(Integer limit, Integer offset) {

        Criteria criteria = getSession().createCriteria(entityType);
        criteria.setFirstResult(offset);
        criteria.setMaxResults(limit);
        return criteria.list();
    }

    @Override
    public Integer removeChitById(String chitId) {
        Session session = getSession();
        String hql = "delete from Chit where chitId= :chitId";
        int i = session.createQuery(hql).setString("chitId", chitId).executeUpdate();
        return i;
    }

    @Override
    public String getNextId() {
        return getAllCount() + "";
    }

    @Override
    public List<Chit> getAllChitById(Integer limit, Integer offset, String id, Integer type, String date) {
        Query query = null;
        if (type == 0) {
            query = getSession().createQuery("SELECT c FROM Chit c WHERE c.centerid = :id AND DATE(c.datetime) = DATE(:date)  ORDER BY DATE(c.datetime) ASC");
            query.setString("date", date);
            query.setString("id", id);
        } else if (type == 1) {
            query = getSession().createQuery("SELECT c FROM Chit c WHERE c.individualId = :id AND DATE(c.datetime) = DATE(:date) ORDER BY DATE(c.datetime) ASC ");
            query.setString("date", date);
            query.setString("id", id);
        }


        if (limit != null && offset != null) {
            query.setMaxResults(limit);
            query.setFirstResult(offset);
        }

        return query.list();
    }

    @Override
    public Long getAllChitCount(String id) {
        Criteria criteria = getSession().createCriteria(entityType);
        criteria.add(Restrictions.eq("centerid", id));
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();

    }

    @Override
    public List<Chit> getAllChitsByEnvelopeId(String envelopeId) {
        Criteria criteria = getSession().createCriteria(entityType);
        criteria.add(Restrictions.eq("envelopeId", envelopeId));
        return criteria.list();
    }

    @Override
    public Long getAllChitByIdCount(String id, Integer type, Date date) {
        Query query = null;
        if (type == 0) {
            query = getSession().createQuery("SELECT COUNT(c) FROM Chit c WHERE c.centerid = :id AND DATE(c.datetime) = :date");
            query.setDate("date", date);
            query.setString("id", id);
        } else if (type == 1) {
            query = getSession().createQuery("SELECT COUNT(c) FROM Chit c WHERE c.individualId = :id AND DATE(c.datetime) = :date");
            query.setDate("date", date);
            query.setString("id", id);
        }
        return (Long) query.uniqueResult();

    }

    @Override
    public List<Chit> getAllChithsByFormattedDateNIndividualId(String formatedDate, String individualId) {
        Query query = getSession().createQuery("SELECT c FROM Chit c WHERE c.individualId = :individualId AND DATE(c.datetime) = DATE( :formatedDate)");
        query.setString("formatedDate", formatedDate);
        query.setString("individualId", individualId);

        return query.list();
    }
    
}
