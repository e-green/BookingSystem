package org.egreen.opensms.server.dao;

import org.egreen.opensms.server.entity.Envelope;
import org.egreen.opensms.server.models.FinishEnvelopeModel;

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

    List<Envelope> getEnvelopesByCenterId(String centerId, Integer limit, Integer offset, String date);

    List<Envelope> getEnvelopesByIndividualIdByDate(String individualId, Integer limit, Integer offset, String date);

    Envelope getEnvelopesByIndividualIdByDateNCenterId(String individualId, String formatedDate, String centerId);

    List<Envelope> getEnvelopeByCenterIdDate(String center, Integer limit, Integer offset, String formatedDate);

    FinishEnvelopeModel getEnvelopeByCenterIdNIndividualIdDate(String center, String individualId, String formatedDate);

    Envelope getEnvelopesByDateNByIndividualId(String individualId, String formatedDate);

    String checkEnvelopeIsFinished(String envelopeId, String formatedDate);

    int getEnvelopeCountByCenterIdDate(String center, String formatedDate);

    boolean getAllEnvelopesAreFinishedByCenterIdDate(String centerId, String sTime);

    List<Envelope> getEnvelopeByDateRange(String centerId, String individualId, String firstDate, String secondDate);
}
