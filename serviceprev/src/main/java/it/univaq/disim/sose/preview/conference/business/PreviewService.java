package it.univaq.disim.sose.preview.conference.business;

import java.util.List;

import it.univaq.disim.sose.preview.conference.business.model.Conference;

public interface PreviewService {

	/**
	 * 
	 * @param List of conferences
	 * @return Data about all the conferences
	 * @throws BusinessException
	 */
	List<Conference> getConferences(List<String> conferences) throws BusinessException;
	
	/**
	 * 
	 * @param conferenceId Conference id
	 * @return Data about a certain conference
	 * @throws BusinessException
	 */
	Conference getConferenceData(String conferenceId) throws BusinessException;
}
