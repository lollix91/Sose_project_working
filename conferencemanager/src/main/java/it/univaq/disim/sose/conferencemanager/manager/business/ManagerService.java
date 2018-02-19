package it.univaq.disim.sose.conferencemanager.manager.business;

import it.univaq.disim.sose.conferencemanager.clients.PreviewResponse;
import it.univaq.disim.sose.conferencemanager.manager.ManagerRequest;
import it.univaq.disim.sose.conferencemanager.manager.ManagerResponse;
import it.univaq.disim.sose.conferencemanager.manager.business.model.Conference;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

public interface ManagerService {

	//JSONObject getInfo(ManagerRequest req);
	JSONObject getInfo(String req);
	
	JSONObject getJsonPois(ManagerRequest req) throws Exception;
	
	JSONObject getConferenceByDate(String date) throws Exception;
	JSONArray getAllConferencesByActualDate(String date) throws Exception;
}
