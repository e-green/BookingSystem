package org.egreen.opensms.server.dao;

import org.egreen.opensms.server.entity.CenterSummeryCheck;

/**
 * Created by ruwan on 5/20/16.
 */
public interface CenterSummeryCheckDAOController extends DAOController<CenterSummeryCheck,String> {
    boolean getCenterSummeryCheck(String centerId, String sTime);
}
