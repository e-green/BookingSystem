package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.Chit;

import org.egreen.opensms.server.models.ChitModel;
import org.egreen.opensms.server.models.FinishChitModel;
import org.egreen.opensms.server.models.ReturnIdModel1;
import org.egreen.opensms.server.service.ChitDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    public ReturnIdModel1 save(@RequestBody Chit chit) {
        String res = chitDAOService.save(chit);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;

    }


    /**
     * Update Branch
     *
     * @param chit
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 update(@RequestBody Chit chit) {
        String res = chitDAOService.update(chit);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;
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

    /**
     * get All Chits By memberId(individualId or centerId) & string Time (sTime) with limits
     *
     * 0= centerId
     * 1= IndividualId
     * getAllChitById
     *
     * @param chitModel
     * @return
     */
    @RequestMapping(value = "getAllChitById", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public List<Chit> getAllChitById(@RequestBody ChitModel chitModel) {
        return chitDAOService.getAllChitById(chitModel.getLimit(), chitModel.getOffset(),chitModel.getIndividualId(),chitModel.getType(),chitModel.getsTime());
    }

    /**
     * get All Chits By Id, type & string date(sTime)
     *
     * @param id
     * @param type
     * @param date
     * @return
     */
    @RequestMapping(value = "getAllChitByIdCount", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Long getAllChitByIdCount(
                                     @RequestParam("id") String id,
                                     @RequestParam("type") Integer type,
                                     @RequestParam("datetime") String date) {

        return chitDAOService.getAllChitByIdCount(id,type,date);

    }

    /**
     * get all chits by cennterId
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getAllCount", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Long getAllCount(@RequestParam("id") String id) {
        return chitDAOService.getAllCount(id);
    }

    /**
     * get all chits by envelopeId
     *
     * @param envelopeId
     * @return
     */
    @RequestMapping(value = "getAllChitsByEnvelopeId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Chit> getAllChitsByEnvelopeId(@RequestParam("envelopeId") String envelopeId) {
        return chitDAOService.getAllChitsByEnvelopeId(envelopeId);
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

    /**
     * get ChitModel ob
     *
     * @return
     */
    @RequestMapping(value = "ChitModelOB", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ChitModel getChitModelOB() {
        return new ChitModel();
    }

    /**
     * get FinishChitModel ob
     *
     * @return
     */
    @RequestMapping(value = "FinishChitModelOB", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public FinishChitModel getFinishChitModelOB() {
        return new FinishChitModel();
    }

    /**
     * get Chit ob
     *
     * @return
     */
    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Chit getob() {
        return new Chit();
    }


}
