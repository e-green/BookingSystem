package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.ReportLogDAOController;
import org.egreen.opensms.server.entity.ReportLog;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ruwan on 2/11/16.
 */
@Repository
public class ReportLogDAOControllerImpl extends AbstractDAOController<ReportLog,String> implements ReportLogDAOController{
    public ReportLogDAOControllerImpl() {
        super(ReportLog.class, String.class);
    }

    @Override
    public ReportLog getReportLogByCenterAndStime(String centerId, String sTime) {
        ReportLog foReportLog=new ReportLog();
        Criteria criteria = getSession().createCriteria(entityType);
        criteria.add(Restrictions.eq("centerid", centerId));
        criteria.add(Restrictions.eq("stime", sTime));
        List<ReportLog> accountList= criteria.list();
        if(accountList.size()>0){
            for (ReportLog account:accountList) {
                foReportLog=account;
            }
        }
        return foReportLog;
    }

    @Override
    public ReportLog getReportLogByMemberId(String memberId) {
        return null;
    }


}
