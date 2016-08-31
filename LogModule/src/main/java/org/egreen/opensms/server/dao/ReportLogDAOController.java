package org.egreen.opensms.server.dao;

import org.egreen.opensms.server.entity.ReportLog;

/**
 * Created by ruwan on 2/11/16.
 */
public interface ReportLogDAOController extends DAOController<ReportLog,String> {

    ReportLog getReportLogByCenterAndStime(String centerId, String sTime);

    ReportLog getReportLogByMemberId(String memberId);
}
