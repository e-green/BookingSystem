package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.CompanyExpencesDAOController;
import org.egreen.opensms.server.entity.CompanyExpences;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
@Service
public class CompanyExpencesDAOService {

    @Autowired
    private CompanyExpencesDAOController companyExpencesDAOController;

//    @Autowired
//    private CompanyExpencesDAOController branchDAOController;

    private List<CompanyExpences> all;
    private String id;


    /**
     * companyExpences SignUp
     *
     * @param companyExpences
     * @return
     */
    public String save(CompanyExpences companyExpences) {
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        companyExpences.setCompanyexpencesId(newid);


        String s = companyExpencesDAOController.create(companyExpences);
        return s;
    }


    /**
     * Get Random String
     *
     * @param len
     * @return
     * @author Pramoda Nadeeshan Fernando
     * @version 1.0
     * @since 2015-02-12 4.26PM
     */
    private String randomString(int len) {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }


    public List<CompanyExpences> getAll() {
        return companyExpencesDAOController.getAll();
    }

//    public List<CompanyExpences> getAllcompanyExpencesByPagination(String quary, Integer limit, Integer offset) {
//        return companyExpencesDAOController.getAllcompanyExpencesersByPagination(quary, limit, offset);
//    }

    public String update(CompanyExpences companyExpences) {
        return companyExpencesDAOController.update(companyExpences);
    }

//    public Integer removecompanyExpencesById(String centerid) {
//        return companyExpencesDAOController.removecompanyExpencesById(centerid);
//    }

    public String getId() {
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(2);
        return newid;
    }

//    public List<CompanyExpences> getAllcompanyExpencessByBranchId(String branchid) {
//
//        return companyExpencesDAOController.getAllcompanyExpencessByBranchId(branchid);
//    }

    public String getNextId(String branchID) {
//        Branch branch = branchDAOController.read(branchID);
        String centerCode = null;
//        long centerCount = companyExpencesDAOController.getcompanyExpencesCountByBranch(branchID);
//        centerCount++;
        //CompanyExpences branch = branchDAOController.read(branchID);
//        if (branch!=null) {
//            String city=branch.getName().substring(0, 3);
//           centerCode = city + "/" + centerCount;
//        }
        return centerCode;
    }

    public CompanyExpences getcompanyExpencesById(String centerid) {
        return companyExpencesDAOController.read(centerid);
    }

    public List<CompanyExpences> getAllCompanyExpencessByCenter(String center, String firstDate, String secondDate, Integer limit, Integer offset) {

        return companyExpencesDAOController.getAllCompanyExpencessByCenter(center,firstDate,secondDate,limit,offset);
    }
}
