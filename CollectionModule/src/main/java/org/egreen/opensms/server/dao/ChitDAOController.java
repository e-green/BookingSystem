package org.egreen.opensms.server.dao;

import org.egreen.opensms.server.entity.Chit;

import java.util.Date;
import java.util.List;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
public interface ChitDAOController extends DAOController<Chit,String> {

    List<Chit> getAllBranchersByPagination(Integer limit, Integer offset);

    Integer removeChitById(String branchId);

    String getNextId();

    List<Chit> getAllChitById(Integer limit, Integer offset, String id,Integer type,String date);

    Long getAllChitCount(String id);

    List<Chit> getAllChitsByEnvelopeId(String envelopeId);

    Long getAllChitByIdCount(String id, Integer type, Date date1);

    List<Chit> getAllChithsByFormattedDateNIndividualId(String formatedDate, String individualId);

}
