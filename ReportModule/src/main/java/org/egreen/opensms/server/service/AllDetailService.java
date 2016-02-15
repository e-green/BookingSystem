package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.UserDAOController;
import org.egreen.opensms.server.entity.ApprovedLoan;
import org.egreen.opensms.server.entity.Center;
import org.egreen.opensms.server.entity.Individual;
import org.egreen.opensms.server.entity.User;
import org.egreen.opensms.server.models.AllDetailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ruwan on 2/15/16.
 */
@Service
public class AllDetailService {

    @Autowired
    private CenterDAOService centerDAOService;

    @Autowired
    private  IndividualDAOService individualDAOService;

    @Autowired
    private UserDAOController userDAOController;

    @Autowired
    private ApprovedLoanDAOService approvedLoanDAOService;


    /**
     * getAll User count ,loan rate, center count & individual count
     *
     * @return
     */
    public AllDetailModel getAllDetails() {
        List<User> userList = userDAOController.getAll();
        int userCount = userList.size();
        List<ApprovedLoan> unpaidLoanList =approvedLoanDAOService.getUnpaidLoans();
        int unpaidLoanCount = unpaidLoanList.size();
        List<Center> centerList = centerDAOService.getAll();
        int centerCount = centerList.size();
        List<Individual> individualList = individualDAOService.getAll();
        int individualCount = individualList.size();

        AllDetailModel allDetailModel=new AllDetailModel();
        allDetailModel.setUsersCount(userCount);
        allDetailModel.setDueLoanCount(unpaidLoanCount);
        allDetailModel.setCenterCount(centerCount);
        allDetailModel.setIndividualCount(individualCount);

        return allDetailModel;

    }
}
