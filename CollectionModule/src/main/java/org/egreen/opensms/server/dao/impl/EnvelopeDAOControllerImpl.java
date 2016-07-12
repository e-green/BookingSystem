package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.EnvelopeDAOController;
import org.egreen.opensms.server.entity.Envelope;
import org.egreen.opensms.server.models.FinishEnvelopeModel;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
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
    public List<Envelope> getEnvelopesByCenterId(String centerId,Integer limit, Integer offset,String date) {

        /**
         * please use below code when you try to get with unique string Id(date)
         */
        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.center = :centerId AND e.sTime = :date");
        query.setString("date", date);
        query.setString("centerId", centerId);

        if (limit != null && offset != null) {
            query.setMaxResults(limit);
            query.setFirstResult(offset);
        }

        return query.list();

    }

    @Override
    public List<Envelope> getEnvelopesByIndividualIdByDate(String individualId, Integer limit, Integer offset, String date) {
        /**
         * please use below code when you try to get with unique string Id(date)
         */
        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.individualId = :individualId AND e.sTime = :date");
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
        /**
         * please use below code when you try to get with unique string Id(date)
         */
        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.individualId = :individualId AND e.sTime =  :date AND e.center = :centerId");
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
        /**
         * please use below code when you try to get with unique string Id(date)
         */
        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.center = :center AND e.sTime = :formatedDate");
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
        /**
         * please use below code when you try to get with unique string Id(date)
         */
        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.center = :center AND e.individualId = :individualId AND e.sTime = :formatedDate");
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

        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.individualId = :individualId AND e.sTime =  :date");
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
        /**
         * please use below code when you try to get with unique string Id(date)
         */
        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.envelopId = :envelopeId AND e.sTime = :formatedDate");
        query.setString("formatedDate", formatedDate);
        query.setString("envelopeId", envelopeId);

        String val = "0";
        List<Envelope> list = query.list();
        if (list.size() < 0) {
            val = "0";
        }
        if (list.size() > 0) {
            for (Envelope envelope : list) {
                if ( envelope.getFinished() == true) {
                    val = "1";
                }
                if ( envelope.getFinished() == false) {
                    val = "2";
                }
            }
        }
        return val;
    }

    @Override
    public int getEnvelopeCountByCenterIdDate(String center, String formatedDate) {
        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.center = :center AND e.sTime = :formatedDate AND e.finished = :finished");
        query.setString("formatedDate", formatedDate);
        query.setString("center", center);
        query.setBoolean("finished",true);

        List<Envelope> list = query.list();
        return list.size();
    }

    @Override
    public boolean getAllEnvelopesAreFinishedByCenterIdDate(String centerId, String sTime) {
        Query query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.center = :center AND e.sTime = :formatedDate");
        query.setString("formatedDate", sTime);
        query.setString("center", centerId);
        List<Envelope> list = query.list();
        boolean notFinishOneDetected=false;
        boolean returnBool=false;
        if(list.size()==0){
            notFinishOneDetected=true;
        }
        for (Envelope envelope:list) {
            Boolean finished = envelope.getFinished();
            if(finished==false){
                notFinishOneDetected=true;
            }
        }
        if(notFinishOneDetected==true){
            returnBool=false;
        }
        if(notFinishOneDetected==false){
            returnBool=true;
        }
        return returnBool;
    }

    @Override
    public List<Envelope> getEnvelopeByDateRange(String centerId, String individualId, String firstDate, String secondDate) {


        Query query;
        if (secondDate!=null && !secondDate.isEmpty()) {


             query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.center = :center AND e.individualId = :individualId AND e.sTime BETWEEN  :firstDate AND :secondDate AND e.finished = :finished");

            query.setString("center", centerId);
            query.setString("individualId", individualId);
            query.setString("firstDate", firstDate);
            query.setString("secondDate", secondDate);
            query.setBoolean("finished", true);
        }else{

             query = getSession().createQuery("SELECT e FROM Envelope e WHERE e.center = :center AND e.individualId = :individualId AND e.sTime =:firstDate AND e.finished = :finished");

            query.setString("center", centerId);
            query.setString("individualId", individualId);
            query.setString("firstDate", firstDate);
            query.setBoolean("finished", true);
        }

        return query.list();
    }

}
