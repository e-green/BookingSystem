package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.AdminDAOController;
import org.egreen.opensms.server.entity.Admin;
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
public class AdminDAOService {

    @Autowired
    private AdminDAOController adminDAOController;


    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private BranchPermissionDAOService branchPermissionDAOService;

    private List<Admin> all;


    /**
     * Customer SignUp
     *
     * @param customer
     * @return
     */
    public String save(Admin customer) {
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        customer.setAdminid(newid);

        String userPassword = authenticationController.getEncryptWord(customer.getPassword());
        customer.setPassword(userPassword);

        String aLong = adminDAOController.create(customer);
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


    public Admin login(String BranchId, String username, String password) {
        String userPassword = authenticationController.getEncryptWord(password);
        Admin admin = adminDAOController.login(username, userPassword);
        if (admin != null) {
            if (admin.getUsername().equals("admin")){
                return admin;
            }
            Boolean available = branchPermissionDAOService.checkBranchPermission(admin.getAdminid(), BranchId);
            if (available) {
                return admin;
            }
            return null;
        } else {
            return null;
        }

    }

    public List<Admin> getAll() {
        return adminDAOController.getAll();
    }

    public String update(Admin admin) {
        return adminDAOController.update(admin);
    }

    public String logout(String userId) {
        Admin read = adminDAOController.read(userId);
        if (read != null) {

            return adminDAOController.update(read);
        }
        return null;
    }

    public Admin getAdminDetail(String userId) {
        return adminDAOController.read(userId);
    }

    public String resetPassword(String username, String oldPassword, String newPassword) {

        String userPassword = authenticationController.getEncryptWord(oldPassword);
        Admin admin = adminDAOController.checkAccount(username, userPassword);
        if (admin != null) {
            String newpw = authenticationController.getEncryptWord(newPassword);

            admin.setPassword(newpw);
            String update = adminDAOController.update(admin);
            return update;
        } else {
            return null;
        }
    }
}
