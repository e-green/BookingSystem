package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.EnvelopeDAOController;
import org.egreen.opensms.server.entity.Envelope;
import org.egreen.opensms.server.models.FinishEnvelopeModel;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        if (size > 0) {
            return true;
        } else {
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

        if (limit != null && offset != null) {
            query.setMaxResults(limit);
            query.setFirstResult(offset);
        }

        return query.list();

    }

    @Override
    public List<Envelope> getEnvelopesByIndividualIdByDate(String individualId, Integer limit, Integer offset, String date) {
        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.individualId = :individualId AND DATE(e.date) = :date");
        query.setString("date", date);
        query.setString("individualId", individualId);

        if (limit != null && offset != null) {
            query.setMaxResults(limit);
            query.setFirstResult(offset);
        }

        return query.list();
    }

    @Override
    public Envelope getEnvelopesByIndividualIdByDateNCenterId(String individualId, String date, String centerId) {
        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.individualId = :individualId AND DATE(e.date) = DATE( :date) AND e.center = :centerId");
        query.setString("date", date);
        query.setString("individualId", individualId);
        query.setString("centerId", centerId);

        List<Envelope> list = query.list();
        Envelope envelope = new Envelope();
        if (list.size() > 0) {
            for (Envelope en : list) {
                envelope = en;
            }
        }
        return envelope;
    }

    @Override
    public List<Envelope> getEnvelopeByCenterIdDate(String center, Integer limit, Integer offset, String formatedDate) {
        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.center = :center AND DATE(e.date) = DATE( :formatedDate)");
        query.setString("formatedDate", formatedDate);
        query.setString("center", center);

        if (limit != null && offset != null) {
            query.setMaxResults(limit);
            query.setFirstResult(offset);
        }

        return query.list();
    }

    @Override
    public FinishEnvelopeModel getEnvelopeByCenterIdNIndividualIdDate(String center, String individualId, String formatedDate) {
        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.center = :center AND e.individualId = :individualId AND DATE(e.date) = DATE( :formatedDate)");
        query.setString("formatedDate", formatedDate);
        query.setString("center", center);
        query.setString("individualId", individualId);
        boolean b = false;
        FinishEnvelopeModel finishEnvelopeModel = new FinishEnvelopeModel();
        List<Envelope> envelopeList = query.list();
        for (Envelope envelope : envelopeList) {
            if (null != envelope) {
                if (!envelope.getCenter().equals(null)) {
                    finishEnvelopeModel.setInvestment(envelope.getInvesment());
                    finishEnvelopeModel.setCash(envelope.getCash());
                    finishEnvelopeModel.setChitRange(envelope.getChitNumbers());
                    finishEnvelopeModel.setCanAdd(true);
                    finishEnvelopeModel.setDate(envelope.getDate());
                } else {
                    finishEnvelopeModel.setCanAdd(false);
                }
            }
        }
        return finishEnvelopeModel;
    }

    @Override
    public Envelope getEnvelopesByDateNByIndividualId(String individualId, String formatedDate) {
        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.individualId = :individualId AND DATE(e.date) = DATE( :date)");
        query.setString("date", formatedDate);
        query.setString("individualId", individualId);
        Envelope envelope1 = null;
        List<Envelope> envelopeList = query.list();
        for (Envelope envelope : envelopeList) {
            if (envelope != null) {
                envelope1 = envelope;
            }
        }

        return envelope1;
    }

    @Override
    public String checkEnvelopeIsFinished(String envelopeId, String formatedDate) {
        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.envelopId = :envelopeId AND DATE(e.date) = DATE( :formatedDate)");
        query.setString("formatedDate", formatedDate);
        query.setString("envelopeId", envelopeId);

        String val = "0";
        List<Envelope> list = query.list();
        if (list.size() < 0) {
            val = "0";
        }
        if (list.size() > 0) {
            for (Envelope envelope : list) {
                if (envelope.getFinished() == true) {
                    val = "1";
                }
                if (envelope.getFinished() == false) {
                    val = "2";
                }
            }
        }
        return val;
    }

}
