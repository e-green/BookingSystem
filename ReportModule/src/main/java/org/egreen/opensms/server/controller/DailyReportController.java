package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.models.GeneralSummaryReportModel;
import org.egreen.opensms.server.models.SpecifyDateInOutModel;
import org.egreen.opensms.server.service.DailyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ruwan on 5/12/16.
 */
@Controller
@RequestMapping("booking/v1/dailyReport")
public class DailyReportController {

    @Autowired
    private DailyReportService dailyReportService;

    /**
     * get Input & Output By Specified Date For Individuals
     *
     * @param sTime
     * @return
     */
    @RequestMapping(value = "getInputNOutputBySpecifiedDateForIndividuals", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public SpecifyDateInOutModel getInputNOutputBySpecifiedDateForIndividuals(@RequestParam("sTime") String sTime) {
        return dailyReportService.getInputNOutputBySpecifiedDateForIndividuals(sTime);
    }




}
