package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.UserDAOController;
import org.egreen.opensms.server.entity.User;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
@Service
public class UserDAOService {

    @Autowired
    private UserDAOController userDAOController;


    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private BranchPermissionDAOService branchPermissionDAOService;

    private List<User> all;


    /**
     * Customer SignUp
     *
     * @param customer
     * @return
     */
    public String save(User customer) {
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        customer.setUserId(newid);

        String userPassword = authenticationController.getEncryptWord(customer.getPassword());
        customer.setPassword(userPassword);

        String aLong = userDAOController.create(customer);
        return aLong;
    }

    /**
     * Get Random String
     *
     * @param len
     * @return
     * @author Pramoda Nadeeshan Fernando
     * @version 1.0
     * @since 2015-02-12 4.26PM
     */
    private String randomString(int len) {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }


    public User login( String username, String password) {
        String userPassword = authenticationController.getEncryptWord(password);
        User user = userDAOController.login(username, userPassword);
        if (user != null) {
//            if (user.getUsername().equals("user")){
//                return user;
//            }
//            Boolean available = branchPermissionDAOService.checkBranchPermission(user.getUserId(), BranchId);
//            if (available) {
                return user;
           // }
           // return null;
        } else {
            return null;
        }

    }

    public List<User> getAll() {
        return userDAOController.getAll();
    }

    public String update(User user) {
        return userDAOController.update(user);
    }

    public String logout(String userId) {
        User read = userDAOController.read(userId);
        if (read != null) {

            return userDAOController.update(read);
        }
        return null;
    }

    public User getUserDetail(String userId) {
        return userDAOController.read(userId);
    }

    public String resetPassword(String username, String oldPassword, String newPassword) {

        String userPassword = authenticationController.getEncryptWord(oldPassword);
        User user = userDAOController.checkAccount(username, userPassword);
        if (user != null) {
            String newpw = authenticationController.getEncryptWord(newPassword);

            user.setPassword(newpw);
            String update = userDAOController.update(user);
            return update;
        } else {
            return null;
        }
    }


}
