package it.univaq.disim.sose.conferencemanager.manager.business;

import javax.ws.rs.core.Response;


public interface ManagerService {

	/**
	 * 
	 * @param req Conference id
	 * @return Data about conference
	 */
	Response getInfo(String req);
	
	/**
	 * 
	 * @param req Conference id 
	 * @return	Pois around a certain place
	 * @throws Exception
	 */
	Response getJsonPois(String req) throws Exception;
	
	/**
	 * 
	 * @param date Selected Date
	 * @return Data about conference
	 * @throws Exception
	 */
	Response getConferenceByDate(String date) throws Exception;
	
	/**
	 * 
	 * @param date Selected Date
	 * @return Data about all the stored conferences
	 * @throws Exception
	 */
	Response getAllConferencesByActualDate(String date) throws Exception;
}
