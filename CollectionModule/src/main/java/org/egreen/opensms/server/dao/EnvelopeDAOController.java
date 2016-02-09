package org.egreen.opensms.server.dao;

import org.egreen.opensms.server.entity.Envelope;

import java.util.Date;
import java.util.List;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
public interface EnvelopeDAOController extends DAOController<Envelope,String> {

    List<Envelope> getAllBranchersByPagination(Integer limit, Integer offset);

    Integer removeBranchById(String branchId);

    String getNextId();

    boolean checkIfExist(String centerName);

    List<Envelope> getEnvelopesByCenterId(String centerId, Integer limit, Integer offset, Date date);

    List<Envelope> getEnvelopesByIndividualIdByDate(String individualId, Integer limit, Integer offset, Date date);

    Envelope etEnvelopesByIndividualIdByDateNCenterId(String individualId, Date date1, String centerId);
}
