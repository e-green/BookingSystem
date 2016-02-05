package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.UserDAOController;
import org.egreen.opensms.server.entity.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */

@Repository
public class UserDAOControllerImpl extends AbstractDAOController<User,String> implements UserDAOController {
    public UserDAOControllerImpl() {
        super(User.class, String.class);
    }


    @Override
    public User login(String username, String userPassword) {
        Criteria criteria = getSession().createCriteria(entityType);

        criteria.add(Restrictions.eq("username", username));
        criteria.add(Restrictions.eq("password", userPassword));


        if ( criteria.list().size()>0) {

            User o = (User) criteria.list().get(0);
            if (o != null) {
                return o;
            } else {
                return null;
            }

        }else {
            return null;
        }

    }

    @Override
    public User checkAccount(String username, String userPassword) {
        Criteria criteria = getSession().createCriteria(entityType);
        criteria.add(Restrictions.eq("username", username));
        criteria.add(Restrictions.eq("password", userPassword));
        if ( criteria.list().size()>0) {

            User o = (User) criteria.list().get(0);
            if (o != null) {
                return o;
            } else {
                return null;
            }

        }else {
            return null;
        }

    }
}
