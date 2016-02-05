package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.Logger;
import org.egreen.opensms.server.models.ReturnIdModel;
import org.egreen.opensms.server.service.LoggerDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by dewmal on 7/17/14.
 */
@Controller
@RequestMapping("booking/v1/logger")
public class LoggerController {

    @Autowired
    private LoggerDAOService loggerDAOService;

    /**
     * save Center
     *
     * @param logger
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel save(@RequestBody Logger logger) {
        String res = loggerDAOService.save(logger);
        ReturnIdModel returnIdModel = new ReturnIdModel();
        returnIdModel.setId(res);
        return returnIdModel;

    }


    /**
     * Update Center
     *
     * @param logger
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel update(@RequestBody Logger logger) {
        String res = loggerDAOService.update(logger);
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
        String res = loggerDAOService.getNextId(branchID);
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
    public List<Logger> getAll() {
        List<Logger> all = loggerDAOService.getAll();
        return all;
    }

    /**
     *
     * checkIfExist
     *
     * @param loggerName
     * @return
     */
    @RequestMapping(value = "checkIfExist", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public boolean checkIfExist(@RequestParam("loggerName") String loggerName) {
        boolean all = loggerDAOService.checkIfExist(loggerName);
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
    public List<Logger> getAll( @RequestParam("limit") Integer limit,
                                @RequestParam("offset") Integer offset) {
        List<Logger> all = loggerDAOService.getAllByPagination(limit,offset);
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
    public List<Logger> getAllCenterByPagination(@RequestParam("quary") String quary,
                                                 @RequestParam("limit") Integer limit,
                                                 @RequestParam("offset") Integer offset) {

        return loggerDAOService.getAllCenterByPagination(quary, limit, offset);
    }


    /**
     * getAllCentersByBranchId
     *
     * @param loggerId
     * @return
     */
    @RequestMapping(value = "getAllCentersById", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Logger getAllCentersById(@RequestParam("loggerId") String loggerId) {
        return loggerDAOService.getCenterById(loggerId);
    }

    /**
     * removeCenterById
     *
     * @param loggerid
     * @return
     */
    @RequestMapping(value = "removeCenterById", method = RequestMethod.POST)
    @ResponseBody
    public Integer removeCenterById(@RequestParam("loggerid") String loggerid) {
        Integer res = loggerDAOService.removeCenterById(loggerid);

        return res;

    }

    /**
     *
     * getCenterCode
     *
     * @param loggerName
     * @return
     */
    @RequestMapping(value = "getCenterCode", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ReturnIdModel getCenterCode(@RequestParam("loggerName") String loggerName) {
        String name=loggerName.substring(0, 3);
        ReturnIdModel returnIdModel = new ReturnIdModel();
        returnIdModel.setId(name);
        return returnIdModel;

    }

    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Logger getob() {
        return new Logger();
    }


}
