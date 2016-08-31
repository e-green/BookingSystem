package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.ReportLog;

import org.egreen.opensms.server.models.ReturnIdModel1;
import org.egreen.opensms.server.service.ReportLogDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ruwan on 2/11/16.
 */
@Controller
@RequestMapping(value = "booking/v1/reportLog")
public class ReportLogController {

    @Autowired
    private ReportLogDAOService reportLogDAOService;

    /**
     * save ReportLog
     *
     * @param reportLog
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 save(@RequestBody ReportLog reportLog) {
        String res = reportLogDAOService.save(reportLog);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;

    }


    /**
     * Update ReportLog
     *
     * @param reportLog
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 update(@RequestBody ReportLog reportLog) {
        String res = reportLogDAOService.update(reportLog);
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
    public List<ReportLog> getAll() {
        List<ReportLog> all = reportLogDAOService.getAll();
        return all;
    }

    /**
     * get reportLog ob
     *
     * @return
     */
    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ReportLog getob() {
        return new ReportLog();
    }



    /**
     * get reportLog by center and stime
     *
     * @param centerId
     * @param sTime
     * @return
     */
    @RequestMapping(value = "getReportLogByCenterAndStime", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ReportLog getReportLogByCenterAndStime(@RequestParam("centerId") String centerId,@RequestParam("sTime") String sTime) {
        return reportLogDAOService.getReportLogByCenterAndStime(centerId,sTime);
    }

}
