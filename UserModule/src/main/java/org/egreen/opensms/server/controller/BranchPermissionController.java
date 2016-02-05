package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.BranchPermission;
import org.egreen.opensms.server.service.BranchPermissionDAOService;
import org.egreen.opensms.server.service.PermissionDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * Created by dewmal on 7/17/14.
 */
@Controller
@RequestMapping("booking/v1/branchpermission")
public class BranchPermissionController {

    @Autowired
    private BranchPermissionDAOService branchpermissionDAOService;


    /**
     * save permission
     *
     * @param permission
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage save(@RequestBody BranchPermission permission) {
        String res = branchpermissionDAOService.save(permission);
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
    public ResponseMessage update(@RequestBody BranchPermission permission) {
        String res = branchpermissionDAOService.save(permission);
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
    @RequestMapping(value = "getBranchPermissionByAdminId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<BranchPermission> getPermissionByClientId(@RequestParam("adminId")String permissionId) {
        return branchpermissionDAOService.getPermissionByClientId(permissionId);
    }


    @RequestMapping(value = "remove", method = RequestMethod.POST)
    @ResponseBody
    public Integer removePermission(@RequestParam("branchPermissionId")String branchPermissionId) {

        return branchpermissionDAOService.removePermission(branchPermissionId);

    }


    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public BranchPermission getob() {
        return new BranchPermission();
    }


}
