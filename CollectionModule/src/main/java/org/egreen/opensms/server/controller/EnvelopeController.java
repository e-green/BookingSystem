package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.Envelope;
import org.egreen.opensms.server.models.EnvelopeModel;

import org.egreen.opensms.server.models.ReturnIdModel1;
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
//
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
//

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
     *
     * getAll
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "getAll", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Envelope> getAll( @RequestParam("limit") Integer limit,
                                @RequestParam("offset") Integer offset) {
        List<Envelope> all = envelopeDAOService.getAllByPagination(limit,offset);
        return all;
    }


    /**
     *
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
//
//    /**
//     * Get package Id
//     *
//     * @return
//     */
//    @RequestMapping(value = "getId", method = RequestMethod.GET, headers = "Accept=application/json")
//    @ResponseBody
//    public ReturnIdModel1 getId(@RequestParam("name") String name) {
//        String nameNew=name.substring(0, 3);
//        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
//        returnIdModel1.setId(nameNew);
//        return returnIdModel1;
//    }

    /**
     *
     * getEnvelopesByCenterId
     *
     * @param centerId
     * @return
     */
    @RequestMapping(value = "getEnvelopesByCenterId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Envelope> getEnvelopesByCenterId(@RequestParam("centerId") String centerId,
                                                 @RequestParam("limit")Integer limit,
                                                 @RequestParam("offset")Integer offset,
                                                 @RequestParam("datetime") Long date ) {
        Timestamp timestamp = new Timestamp(date);
        Date date1 =  new Date(timestamp.getTime());

        List<Envelope>envelopeList =  envelopeDAOService.getEnvelopesByCenterId(centerId,limit,offset,date1);
        return envelopeList;

    }

    /**
     * Get Envelope list by centerId and date
     *
     * @param envelope
     * @return
     */
    @RequestMapping(value = "getEnvelopeByCenterIdDate",method = RequestMethod.POST)
    @ResponseBody
    public List<Envelope> getEnvelopesByCenterId(@RequestBody EnvelopeModel envelope){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM--dd");
        String formatedDate = simpleDateFormat.format(envelope.getDate());
        return envelopeDAOService.getEnvelopeByCenterIdDate(envelope.getCenter(),envelope.getLimit(),envelope.getOffset(),formatedDate);

    }

    @RequestMapping(value = "getEnvelopeByCenterIdNIndividualIdDate",method = RequestMethod.POST)
    @ResponseBody
    public boolean getEnvelopeByCenterIdNIndividualIdDate(@RequestBody EnvelopeModel envelope){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM--dd");
        String formatedDate = simpleDateFormat.format(envelope.getDate());
        return envelopeDAOService.getEnvelopeByCenterIdNIndividualIdDate(envelope.getCenter(),envelope.getIndividualId(),formatedDate);

    }

    @RequestMapping(value = "getEnvelopesByIndividualIdByDate", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Envelope> getEnvelopesByIndividualIdByDate(@RequestParam("individualId") String individualId,
                                                 @RequestParam("limit")Integer limit,
                                                 @RequestParam("offset")Integer offset,
                                                 @RequestParam("datetime") Long date) {


        Timestamp timestamp = new Timestamp(date);
        Date date1 =  new Date(timestamp.getTime());


        List<Envelope>envelopeList =  envelopeDAOService.getEnvelopesByIndividualIdByDate(individualId,limit,offset,date1);
        return  envelopeList;
    }

    /**
     * Get All
     *
     * @return
     */
//    @RequestMapping(value = "getAll", method = RequestMethod.GET, headers = "Accept=application/json")
//    @ResponseBody
//    public List<Envelope> getAll() {
//        return envelopeDAOService.getAll();
//    }

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

    /***
     *
     * getAllBranches
     *
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


    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Envelope getob() {
        return new Envelope();
    }

    /**
     * ob
     * @return
     */
    @RequestMapping(value = "getEnvelopeByCenterIdDateOB", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public EnvelopeModel getEnvelopeModelob() {
        return new EnvelopeModel();
    }


    /**
     *
     *
     * getBranchCode
     *
     * @param locationName
     * @return
     */
    @RequestMapping(value = "getBranchCode", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public String getBranchCode(@RequestParam("locationName") String locationName) {

        String id = envelopeDAOService.getNextID();
        String name=locationName.substring(0, 2)+id;
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
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM--dd");

        String formatedDate = simpleDateFormat.format(envelope.getDate());

        return envelopeDAOService.getEnvelopesByIndividualIdByDateNCenterId(envelope.getIndividualId(),formatedDate,envelope.getCenter());

    }

}
