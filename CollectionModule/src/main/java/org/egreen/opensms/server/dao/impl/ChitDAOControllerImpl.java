package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.ChitDAOController;
import org.egreen.opensms.server.entity.Chit;
import org.egreen.opensms.server.models.ChitCountListModel;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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
        String hql = "delete from Chit c where c.chitId= :chitId";
        int i = session.createQuery(hql).setString("chitId", chitId).executeUpdate();
        return i;
    }

    @Override
    public List<Chit> getAllChitById(Integer limit, Integer offset, String id, Integer type, String date) {
        Query query = null;
        if (type == 0) {
            /**
             * please use below code when you try to get with unique string Id(date)
             */
            query = getSession().createQuery("SELECT c FROM Chit c WHERE c.centerid = :id AND c.sTime = :date  ORDER BY c.datetime ASC");
            query.setString("date", date);
            query.setString("id", id);
        } else if (type == 1) {
            /**
             * please use below code when you try to get with unique string Id(date)
             */
            query = getSession().createQuery("SELECT c FROM Chit c WHERE c.individualId = :id AND c.sTime = :date ORDER BY c.datetime ASC ");
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
        criteria.addOrder(Order.asc("datetime"));

        return criteria.list();
    }

    @Override
    public Long getAllChitByIdCount(String id, Integer type, String date) {
        Query query = null;
        if (type == 0) {
            /**
             * please use below code when you try to get with unique string Id(date)
             */
            query = getSession().createQuery("SELECT COUNT(c) FROM Chit c WHERE c.centerid = :id AND c.sTime = :date");
            query.setString("date", date);
            query.setString("id", id);
        } else if (type == 1) {
            /**
             * please use below code when you try to get with unique string Id(date)
             */
            query = getSession().createQuery("SELECT COUNT(c) FROM Chit c WHERE c.individualId = :id AND c.sTime = :date");
            query.setString("date", date);
            query.setString("id", id);

        }
        return (Long) query.uniqueResult();

    }

    @Override
    public List<Chit> getAllChithsByFormattedDateNIndividualId(String formatedDate, String individualId) {
        /**
         * please use below code when you try to get with unique string Id(date)
         */
        Query query = getSession().createQuery("SELECT c FROM Chit c WHERE c.individualId = :individualId AND c.sTime = :formatedDate");
        query.setString("formatedDate", formatedDate);
        query.setString("individualId", individualId);

        return query.list();
    }

    @Override
    public List<ChitCountListModel> getChitCountListOfIndividualForSpesificDateRange(String individualId,String sTime, String endSTime) {
        String[] split = sTime.split("-");
        String year = split[0];
        String month = split[1];
        String day = split[2];
        int startYear = Integer.parseInt(year);
        int startMonth = Integer.parseInt(month);
        int startDay = Integer.parseInt(day);

        String[] splitEnd = endSTime.split("-");
        String yearEnd = splitEnd[0];
        String monthEnd = splitEnd[1];
        String dayEnd = splitEnd[2];
        int endYear = Integer.parseInt(yearEnd);
        int endMonth = Integer.parseInt(monthEnd);
        int endDay = Integer.parseInt(dayEnd);

        int dateDifference = endDay - startDay+1;
        List<ChitCountListModel> chitCountListModelList=new ArrayList<ChitCountListModel>();
        int incrementDate=startDay;
        for (int i = 0; i < dateDifference; i++) {
            String s=null;
            if(i==0){
                s = startYear + "-" + startMonth + "-" + startDay;
            }
            if(i > 0 ){
                s = startYear + "-" + startMonth + "-" + ++startDay;
            }

            Query query = getSession().createQuery("SELECT c FROM Chit c WHERE c.individualId = :individualId AND c.sTime = :formatedDate");
            query.setString("individualId", individualId);
            query.setString("formatedDate", s);
            List<Chit> list = query.list();
            ChitCountListModel chitCountListModel=new ChitCountListModel();
            chitCountListModel.setChitCount(list.size());
            chitCountListModelList.add(chitCountListModel);
        }
        return chitCountListModelList;
    }

}
