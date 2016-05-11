package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.Permission;
import org.egreen.opensms.server.service.PermissionDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * Created by dewmal on 7/17/14.
 */
@Controller
@RequestMapping("booking/v1/permission")
public class PermissionController {

    @Autowired
    private PermissionDAOService permissionDAOService;


    /**
     * save permission
     *
     * @param permission
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage save(@RequestBody Permission permission) {
        String res = permissionDAOService.save(permission);
        ResponseMessage responseMessage;
        if (res != null) {
            responseMessage = ResponseMessage.SUCCESS;
            responseMessage.setData(res);
        } else {
            responseMessage = ResponseMessage.DANGER;
            responseMessage.setData(res);
        }
        return responseMessage;
    }

    /**
     * update permission
     *
     * @param permission
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage update(@RequestBody Permission permission) {
        String res = permissionDAOService.save(permission);
        ResponseMessage responseMessage;
        if (res != null) {
            responseMessage = ResponseMessage.SUCCESS;
            responseMessage.setData(res);
        } else {
            responseMessage = ResponseMessage.DANGER;
            responseMessage.setData(res);
        }
        return responseMessage;
    }

    /***
     *
     * getPermissionByAdminId
     *
     * @param permissionId
     * @return
     */
    @RequestMapping(value = "getPermissionByUserId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Permission> getPermissionByUserId(@RequestParam("adminId")String permissionId) {
        return permissionDAOService.getPermissionByClientId(permissionId);
    }

    @RequestMapping(value = "getPermissionIsAvailable", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public boolean getPermissionIsAvailable(@RequestParam("adminId")String adminId,@RequestParam("permission")String permission) {
        return permissionDAOService.getPermissionIsAvailable(adminId,permission);
    }

    @RequestMapping(value = "getAll", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Permission> getAll() {

        return permissionDAOService.getAll();
    }



    @RequestMapping(value = "remove", method = RequestMethod.POST)
    @ResponseBody
    public Integer removePermission(@RequestParam("permissionId")String permissionId) {

        return permissionDAOService.removePermission(permissionId);

    }


    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Permission getob() {
        return new Permission();
    }


}
