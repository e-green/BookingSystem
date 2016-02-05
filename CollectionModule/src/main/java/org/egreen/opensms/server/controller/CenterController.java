package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.Center;
import org.egreen.opensms.server.models.CenterModel;
import org.egreen.opensms.server.models.ReturnIdModel;
import org.egreen.opensms.server.service.CenterDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by dewmal on 7/17/14.
 */
@Controller
@RequestMapping("booking/v1/center")
public class CenterController {

    @Autowired
    private CenterDAOService centerDAOService;

    /**
     * save Center
     *
     * @param center
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel save(@RequestBody Center center) {
        String res = centerDAOService.save(center);
        ReturnIdModel returnIdModel = new ReturnIdModel();
        returnIdModel.setId(res);
        return returnIdModel;

    }


    /**
     * Update Center
     *
     * @param center
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel update(@RequestBody Center center) {
        String res = centerDAOService.update(center);
        ReturnIdModel returnIdModel = new ReturnIdModel();
        returnIdModel.setId(res);
        return returnIdModel;

    }

    /**
     * Get package Id
     *
     * @return
     */
    @RequestMapping(value = "getId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ReturnIdModel getId(@RequestParam("branchCode") String branchID) {
        String res = centerDAOService.getNextId(branchID);
        ReturnIdModel returnIdModel = new ReturnIdModel();
        returnIdModel.setId(res);
        return returnIdModel;

    }


    /**
     * Get All
     *
     * @return
     */
    @RequestMapping(value = "getAll", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Center> getAll() {
        List<Center> all = centerDAOService.getAll();
        return all;
    }

    /**
     *
     * checkIfExist
     *
     * @param centerName
     * @return
     */
    @RequestMapping(value = "checkIfExist", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public boolean checkIfExist(@RequestParam("centerName") String centerName) {
        boolean all = centerDAOService.checkIfExist(centerName);
        return all;
    }

    /**
     *
     * getAll
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "getAllPagination", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Center> getAll( @RequestParam("limit") Integer limit,
                                @RequestParam("offset") Integer offset) {
        List<Center> all = centerDAOService.getAllByPagination(limit,offset);
        return all;
    }

    /**
     * Get All Centerers By Pagination
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "getAllCenterersByPagination", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Center> getAllCenterByPagination(@RequestParam("quary") String quary,
                                                 @RequestParam("limit") Integer limit,
                                                 @RequestParam("offset") Integer offset) {

        return centerDAOService.getAllCenterByPagination(quary, limit, offset);
    }


    /**
     * getAllCentersByBranchId
     *
     * @param centerId
     * @return
     */
    @RequestMapping(value = "getAllCentersById", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Center getAllCentersById(@RequestParam("centerId") String centerId) {
        return centerDAOService.getCenterById(centerId);
    }

    /**
     * removeCenterById
     *
     * @param centerid
     * @return
     */
    @RequestMapping(value = "removeCenterById", method = RequestMethod.POST)
    @ResponseBody
    public Integer removeCenterById(@RequestParam("centerid") String centerid) {
        Integer res = centerDAOService.removeCenterById(centerid);

        return res;

    }

    /**
     *
     * getCenterCode
     *
     * @param centerName
     * @return
     */
    @RequestMapping(value = "getCenterCode", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ReturnIdModel getCenterCode(@RequestParam("centerName") String centerName) {
        String name=centerName.substring(0, 3);
        ReturnIdModel returnIdModel = new ReturnIdModel();
        returnIdModel.setId(name);
        return returnIdModel;

    }

    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Center getob() {
        return new Center();
    }


}
