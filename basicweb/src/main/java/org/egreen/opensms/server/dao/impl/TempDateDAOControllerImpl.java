package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.TempDataDAOController;
import org.egreen.opensms.server.entity.Tempdata;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by dewmal on 7/31/14.
 */
@Repository
public class TempDateDAOControllerImpl extends AbstractDAOController<Tempdata, String> implements TempDataDAOController {

    public TempDateDAOControllerImpl() {
        super(Tempdata.class, String.class);
    }

    @Override
    public Boolean getTokenValidate(String key, String token) {
        Criteria criteria = getSession().createCriteria(entityType);
        criteria.add(Restrictions.eq("key", "token"));
        criteria.add(Restrictions.eq("value", token));

        Object o = criteria.list().get(0);
        if (o!=null){
            return true;
        }else {
            return false;
        }
    }
}
