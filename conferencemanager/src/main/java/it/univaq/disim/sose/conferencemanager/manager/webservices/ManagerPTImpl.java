package it.univaq.disim.sose.conferencemanager.manager.webservices;



import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.disim.sose.conferencemanager.clients.PreviewPT;
import it.univaq.disim.sose.conferencemanager.clients.PreviewRequest;
import it.univaq.disim.sose.conferencemanager.clients.PreviewResponse;
import it.univaq.disim.sose.conferencemanager.clients.PreviewService;
import it.univaq.disim.sose.conferencemanager.manager.CalendarEventRecord;
import it.univaq.disim.sose.conferencemanager.manager.ConferenceType;
import it.univaq.disim.sose.conferencemanager.manager.ManagerPoiRequestByLocation;
import it.univaq.disim.sose.conferencemanager.manager.ManagerPoiResponseByLocation;
import it.univaq.disim.sose.conferencemanager.manager.ManagerRequest;
import it.univaq.disim.sose.conferencemanager.manager.ManagerPT;
import it.univaq.disim.sose.conferencemanager.manager.ManagerRequestByDate;
import it.univaq.disim.sose.conferencemanager.manager.ManagerRequestById;
import it.univaq.disim.sose.conferencemanager.manager.ManagerRequestListByDate;
import it.univaq.disim.sose.conferencemanager.manager.ManagerResponse;
import it.univaq.disim.sose.conferencemanager.manager.ManagerResponseByDate;
import it.univaq.disim.sose.conferencemanager.manager.ManagerResponseById;
import it.univaq.disim.sose.conferencemanager.manager.ManagerResponseListByDate;

import it.univaq.disim.sose.conferencemanager.manager.ObjectFactory;
import it.univaq.disim.sose.conferencemanager.manager.PoisRecord;
import it.univaq.disim.sose.conferencemanager.manager.business.ManagerService;
import it.univaq.disim.sose.conferencemanager.manager.business.model.Conference;



@Component(value = "ManagerPTImpl")
public class ManagerPTImpl implements ManagerPT {

	private static Logger LOGGER = LoggerFactory.getLogger(ManagerPTImpl.class);
	
	@Autowired
	private ManagerService service;
		

	@Override
	public ManagerResponseListByDate managerConferenceListRequestByDate(ManagerRequestListByDate parameters) {
		// TODO Auto-generated method stub
		
		LOGGER.info("CALLED MANAGERLISTBYDATE");
		
		ManagerResponseListByDate conferences_list = new ManagerResponseListByDate();
		
		try {
			service.getAllConferencesByActualDate(parameters.getDateToday());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ManagerResponseByDate managerConferenceRequestByDate(ManagerRequestByDate parameters) {
		// TODO Auto-generated method stub
		
		LOGGER.info("CALLED MANAGERBYDATE");
		
		ManagerResponseByDate response = new ManagerResponseByDate();
		ConferenceType conference = new ConferenceType();
		
		try {
			 JSONObject rp = service.getConferenceByDate(parameters.getDateConference());
			 //System.out.println(rp);
			 conference.setCity(rp.getString("city"));
			 conference.setAbstract(rp.getString("_abstract"));
			 conference.setLatitude(rp.getDouble("latitude"));
			 conference.setLongitude(rp.getDouble("longitude"));
			 conference.setName(rp.getString("name"));
			 conference.setUrlImage(rp.getString("urlImage"));
			 conference.setUrlPDFs(rp.getString("urlPDFs"));
			 //conference.setDate(value);
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setCalendarEventRecord(conference);
		
		return response;
	}

	
	
}
