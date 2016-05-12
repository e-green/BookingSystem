package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.*;
import org.egreen.opensms.server.models.*;

import org.egreen.opensms.server.service.EnvelopeDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;


/**
 * Created by dewmal on 7/17/14.
 */
@Controller
@RequestMapping("booking/v1/envelope")
public class EnvelopeController {


    @Autowired
    private EnvelopeDAOService envelopeDAOService;

    /**
     * save Branch
     *
     * @param envelope
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 save(@RequestBody Envelope envelope) {
        String res = envelopeDAOService.save(envelope);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;

    }

    /**
     * Update Branch
     *
     * @param envelope
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 update(@RequestBody Envelope envelope) {
        String res = envelopeDAOService.update(envelope);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;

    }

    /**
     * getAll
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "getAll", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Envelope> getAll(@RequestParam("limit") Integer limit,
                                 @RequestParam("offset") Integer offset) {
        List<Envelope> all = envelopeDAOService.getAllByPagination(limit, offset);
        return all;
    }


    /**
     * checkIfExist
     *
     * @param envelopeName
     * @return
     */
    @RequestMapping(value = "checkIfExist", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public boolean checkIfExist(@RequestParam("envelopeName") String envelopeName) {
        boolean all = envelopeDAOService.checkIfExist(envelopeName);
        return all;
    }

    /**
     * get Envelope By envelopeId
     *
     * @param envelopeId
     * @return
     */
    @RequestMapping(value = "getEnvelopeById", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Envelope getEnvelopeById(@RequestParam("envelopeId") String envelopeId) {
        Envelope envelope=envelopeDAOService.getEnvelopeById(envelopeId);
        return envelope;
    }

    /**
     * getEnvelopesByCenterId
     *
     * @param centerId
     * @return
     */
    @RequestMapping(value = "getEnvelopesByCenterId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Envelope> getEnvelopesByCenterId(@RequestParam("centerId") String centerId,
                                                 @RequestParam("limit") Integer limit,
                                                 @RequestParam("offset") Integer offset,
                                                 @RequestParam("datetime") String date) {


        List<Envelope> envelopeList = envelopeDAOService.getEnvelopesByCenterId(centerId, limit, offset, date);
        return envelopeList;

    }

    /**
     * Get Envelope list by centerId and date
     *
     * @param envelope
     * @return
     */
    @RequestMapping(value = "getEnvelopeByCenterIdDate", method = RequestMethod.POST)
    @ResponseBody
    public List<Envelope> getEnvelopesByCenterId(@RequestBody EnvelopeModel envelope) {
        return envelopeDAOService.getEnvelopeByCenterIdDate(envelope.getCenter(), envelope.getLimit(), envelope.getOffset(), envelope.getsTime());

    }

    /**
     * get Envelope By centerId & individualId & sTime
     *
     * @param envelope
     * @return
     */
    @RequestMapping(value = "getEnvelopeByCenterIdNIndividualIdDate", method = RequestMethod.POST)
    @ResponseBody
    public FinishEnvelopeModel getEnvelopeByCenterIdNIndividualIdDate(@RequestBody EnvelopeModel envelope) {
        return envelopeDAOService.getEnvelopeByCenterIdNIndividualIdDate(envelope.getCenter(), envelope.getIndividualId(), envelope.getsTime());

    }

    /**
     * get Envelopes By individualId & sTime
     *
     * @param envelopeModel
     * @return
     */
    @RequestMapping(value = "getEnvelopesByIndividualIdByDate", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public List<Envelope> getEnvelopesByIndividualIdByDate(@RequestBody EnvelopeModel envelopeModel) {
        List<Envelope> envelopeList = envelopeDAOService.getEnvelopesByIndividualIdByDate(envelopeModel.getIndividualId(), null, null, envelopeModel.getsTime());
        return envelopeList;
    }

    /**
     * Get All Branchers By Pagination
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "getAllPagination", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Envelope> getAllBranchersByPagination(@RequestParam("limit") Integer limit,
                                                      @RequestParam("offset") Integer offset) {
        return envelopeDAOService.getAllBranchersByPagination(limit, offset);
    }

    /**
     * check Envelope is Finished
     *
     * @param finishChitModel
     * @return
     */
    @RequestMapping(value = "checkEnvelopeIsFinished", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public String checkEnvelopeIsFinished(@RequestBody FinishChitModel finishChitModel) {
        return envelopeDAOService.checkEnvelopeIsFinished(finishChitModel.getEnvelopeId(),finishChitModel.getsTime());
    }


    /***
     * getAllBranches
     *
     * @param limit
     * @param offset
     * @param type
     * @return
     */
    @RequestMapping(value = "getAllBranches", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Envelope> getAllBranches(@RequestParam("limit") Integer limit,
                                         @RequestParam("offset") Integer offset,
                                         @RequestParam("type") Integer type) {
        return envelopeDAOService.getAllBranchersByPagination(limit, offset);
    }

    /**
     * removeBranchById
     *
     * @param envelopeId
     * @return
     */
    @RequestMapping(value = "removeBranchById", method = RequestMethod.POST)
    @ResponseBody
    public Integer removeBranchById(@RequestParam("envelopeId") String envelopeId) {
        Integer res = envelopeDAOService.removeBranchById(envelopeId);
        return res;
    }




    /**
     * ob
     *
     * @return
     */
    @RequestMapping(value = "getEnvelopeByCenterIdDateOB", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public EnvelopeModel getEnvelopeModelob() {
        return new EnvelopeModel();
    }


    /**
     * getBranchCode
     *
     * @param locationName
     * @return
     */
    @RequestMapping(value = "getBranchCode", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public String getBranchCode(@RequestParam("locationName") String locationName) {

        String id = envelopeDAOService.getNextID();
        String name = locationName.substring(0, 2) + id;
        return name;
    }

    /**
     * getEnvelope By individual id ,date and centerId
     *
     * @param envelope
     * @return
     */
    @RequestMapping(value = "getEnvelopesByIndividualIdByDateNCenterId", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public Envelope getEnvelopesByIndividualIdByDateNCenterId(@RequestBody Envelope envelope) {

        return envelopeDAOService.getEnvelopesByIndividualIdByDateNCenterId(envelope.getIndividualId(), envelope.getsTime(), envelope.getCenter());

    }

    @RequestMapping(value = "getEnvelopesByDateNByIndividualId", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public EnvelopeDetailModel getEnvelopesByIndividualIdByDateNCenterId(@RequestBody EnvelopeDetailModel envelopeDetailModel) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM--dd");
//        String formatedDate = simpleDateFormat.format(envelopeDetailModel.getDate());
//        return envelopeDAOService.getEnvelopesByDateNByIndividualId(formatedDate,envelopeDetailModel.getIndividualId());
        return  null;
    }

    /**
     * get EnvelopesDetailModel
     *
     * @param envelopeDetailModel
     * @return
     */
    @RequestMapping(value = "getEnvelopesDetailModel", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public EnvelopeDetailModel getEnvelopesDetailModel(@RequestBody EnvelopeDetailModel envelopeDetailModel) {
        return envelopeDAOService.getEnvelopesDetailModel(envelopeDetailModel);
    }

    /**
     *get Envelope Count By CenterId & Date
     *
     * @param envelope
     * @return
     */
    @RequestMapping(value = "getEnvelopeCountByCenterIdDate", method = RequestMethod.POST)
    @ResponseBody
    public int getEnvelopeCountByCenterIdDate(@RequestBody EnvelopeModel envelope) {
        return envelopeDAOService.getEnvelopeCountByCenterIdDate(envelope.getCenter(),envelope.getsTime());

    }

    /**
     * checking All Envelopes are Finished By CenterId & Date
     *
     * @param centerId
     * @param sTime
     * @return
     */
    @RequestMapping(value = "getAllEnvelopesAreFinishedByCenterIdDate", method = RequestMethod.POST)
    @ResponseBody
    public boolean getAllEnvelopesAreFinishedByCenterIdDate(@RequestParam("centerId") String centerId,@RequestParam("sTime") String sTime) {
        return envelopeDAOService.getAllEnvelopesAreFinishedByCenterIdDate(centerId,sTime);

    }

    /**
     * get EnvelopeDetailModel ob
     *
     * @return
     */
    @RequestMapping(value = "EnvelopesDetailModelOB", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public EnvelopeDetailModel getEnvelopeDetailModelOB() {
        return new EnvelopeDetailModel();
    }

    /**
     * get Envelope ob
     *
     * @return
     */
    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Envelope getob() {
        return new Envelope();
    }

}
