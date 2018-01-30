package it.univaq.disim.sose.preview.conference.business;

import java.util.List;

import it.univaq.disim.sose.preview.conference.business.model.Conference;

public interface PreviewService {

	// get conferences (multiple) data
	List<Conference> getConferences(List<String> conferences) throws BusinessException;
	
	// get single conference data
	Conference getConferenceData(String conferenceId) throws BusinessException;
}
