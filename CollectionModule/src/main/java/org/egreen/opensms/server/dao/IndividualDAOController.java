package org.egreen.opensms.server.dao;

import org.egreen.opensms.server.entity.Individual;

import java.util.List;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
public interface IndividualDAOController extends DAOController<Individual,String> {

    List<Individual> getAllBranchersByPagination(Integer limit, Integer offset);

    Integer removeIndividualById(String branchId);

    String getNextId();

    boolean checkIfExist(String centerName);

    Individual getIndividualsByCenterIdAndIndividualId(String centerId, String individualId);

    Individual getIndividualByName(String memberName);
}
