package it.univaq.disim.sose.conferencemanager.manager.business;

import it.univaq.disim.sose.conferencemanager.manager.ManagerRequest;
import it.univaq.disim.sose.conferencemanager.manager.ManagerResponse;
import it.univaq.disim.sose.conferencemanager.manager.business.model.Conference;
import org.json.JSONObject;

public interface ManagerService {

	  ManagerResponse getInfo(ManagerRequest req);
	  JSONObject getJsonPois(String idconference) throws Exception;
	
}
