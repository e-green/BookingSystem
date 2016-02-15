package org.egreen.opensms.server.dao;

import org.egreen.opensms.server.entity.User;
import org.egreen.opensms.server.models.AllDetailModel;

import java.util.List;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
public interface UserDAOController extends DAOController<User,String> {

    User login(String username, String userPassword);

    User checkAccount(String username, String userPassword);

    List<AllDetailModel> getAllDetails();
}
