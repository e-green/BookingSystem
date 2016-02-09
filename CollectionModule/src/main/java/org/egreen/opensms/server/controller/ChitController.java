package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.Chit;
import org.egreen.opensms.server.models.ReturnIdModel;
import org.egreen.opensms.server.service.ChitDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * Created by dewmal on 7/17/14.
 */
@Controller
@RequestMapping("booking/v1/chit")
public class ChitController {

    @Autowired
    private ChitDAOService chitDAOService;

    /**
     * save Branch
     *
     * @param chit
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel save(@RequestBody Chit chit) {
        String res = chitDAOService.save(chit);
        ReturnIdModel returnIdModel = new ReturnIdModel();
        returnIdModel.setId(res);
        return returnIdModel;

    }


    /**
     * Update Branch
     *
     * @param chit
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel update(@RequestBody Chit chit) {
        String res = chitDAOService.update(chit);
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
    public ReturnIdModel getId(@RequestParam("twnName") String townName) {
        String res = chitDAOService.getNextID();
        String name=townName.substring(0, 3);
        ReturnIdModel returnIdModel = new ReturnIdModel();
        returnIdModel.setId(name);
        return returnIdModel;

    }


    /**
     * Get All
     *
     * @return
     */
    @RequestMapping(value = "getAll", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Chit> getAll() {
        return chitDAOService.getAll();
    }


    /***
     *
     * 0= centerId
     * 1= IndividualId
     * getAllChitById
     *
     *
     * @param limit
     * @param offset
     *
     * @return
     */
    @RequestMapping(value = "getAllChitById", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Chit> getAllChitById(@RequestParam("limit") Integer limit,
                                     @RequestParam("offset") Integer offset,
                                     @RequestParam("id") String id,
                                     @RequestParam("type") Integer type,
                                     @RequestParam("datetime") Long date) {

        Timestamp timestamp = new Timestamp(date);
        Date date1 =  new Date(timestamp.getTime());
        return chitDAOService.getAllChitById(limit, offset,id,type,date1);

    }

    @RequestMapping(value = "getAllChitByIdCount", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Long getAllChitByIdCount(
                                     @RequestParam("id") String id,
                                     @RequestParam("type") Integer type,
                                     @RequestParam("datetime") Long date) {

        Timestamp timestamp = new Timestamp(date);
        Date date1 =  new Date(timestamp.getTime());
        return chitDAOService.getAllChitByIdCount(id,type,date1);

    }

    @RequestMapping(value = "getAllCount", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Long getAllCount(@RequestParam("id") String id) {
        return chitDAOService.getAllCount(id);
    }


    @RequestMapping(value = "getAllChitsByEnvelopeId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Chit> getAllChitsByEnvelopeId(@RequestParam("envelopeId") String envelopeId) {
        return chitDAOService.getAllChitsByEnvelopeId(envelopeId);
    }

    /**
     * removeBranchById
     *
     * @param chitId
     * @return
     */
    @RequestMapping(value = "removeChitById", method = RequestMethod.POST)
    @ResponseBody
    public Integer removeBranchById(@RequestParam("chitId") String chitId) {
        Integer res = chitDAOService.removeChitById(chitId);

        return res;

    }

    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Chit getob() {
        return new Chit();
    }


    @RequestMapping(value = "getBranchCode", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public String getBranchCode(@RequestParam("locationName") String locationName) {

        String id = chitDAOService.getNextID();
        String name=locationName.substring(0, 2)+id;
        return name;
    }

    /**
     * Get Chit by chitId
     *
     * @param chitId
     * @return
     */
    @RequestMapping(value = "getChitById", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Chit getChitById(@RequestParam("chitId") String chitId) {
        return chitDAOService.getChitById(chitId);
    }

}
