package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.CenterSummeryCheckDAOController;
import org.egreen.opensms.server.entity.CenterSummeryCheck;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ruwan on 5/20/16.
 */
@Repository
public class CenterSummeryCheckDAOControllerImpl extends AbstractDAOController<CenterSummeryCheck,String> implements CenterSummeryCheckDAOController {
    public CenterSummeryCheckDAOControllerImpl() {
        super(CenterSummeryCheck.class, String.class);
    }

    @Override
    public boolean getCenterSummeryCheck(String centerId, String sTime) {
        Query query = getSession().createQuery("SELECT c FROM CenterSummeryCheck c WHERE c.centerId = :centerId AND c.sDate = :sTime");
        query.setString("centerId", centerId);
        query.setString("sTime", sTime);
        boolean boolResult=false;
        List<CenterSummeryCheck> list = query.list();
        if(!list.isEmpty()){
            boolResult=true;
        }else if(list.isEmpty()){
            boolResult=false;
        }
        return boolResult;
    }
}
