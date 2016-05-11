package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.PermissionDAOController;

import org.egreen.opensms.server.entity.Permission;
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
public class PermissionDAOService {

    @Autowired
    private PermissionDAOController permissionDAOController;


    @Autowired
    private AuthenticationController authenticationController;

    private List<Permission> all;


    /**
     * Customer SignUp
     *
     * @param customer
     * @return
     */
    public String save(Permission customer) {
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        customer.setPermissionid(newid);


        String aLong = permissionDAOController.create(customer);
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




    public List<Permission> getPermissionByClientId(String adminId) {
        return permissionDAOController.getAllByPropertyByStringValue(adminId,"adminId");
    }

    public Integer removePermission(String permissionId) {
        return permissionDAOController.removePermission(permissionId);
    }

    public List<Permission> getAll() {

        return permissionDAOController.getAll();
    }

    public boolean getPermissionIsAvailable(String adminId, String permission) {
        return permissionDAOController.getPermissionIsAvailable(adminId,permission);
    }
}
