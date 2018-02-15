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
		return null;
	}

	@Override
	public ManagerResponseByDate managerConferenceRequestByDate(ManagerRequestByDate parameters) {
		// TODO Auto-generated method stub
		
		LOGGER.info("CALLED MANAGERBYDATE");
		
		ManagerResponseByDate response = new ManagerResponseByDate();
		ConferenceType conference = new ConferenceType();
		/*
		try {
			 PreviewResponse rp = service.getConferenceByDate(parameters.getDateConference());
			
			 conference.setCity(rp.getCity());
			 conference.setAbstract(rp.getAbstract());
			 conference.setDate(rp.getDate());
			 conference.setLatitude(rp.getLatitude());
			 conference.setLongitude(rp.getLongitude());
			 conference.setName(rp.getName());
			 conference.setUrlImage(rp.getUrlImage());
			 conference.setUrlPDFs(rp.getUrlPDFs());
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
		response.setCalendarEventRecord(conference);
		
		return response;
	}

	@Override
	public ManagerResponseById managerConferenceRequestById(ManagerRequestById parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManagerPoiResponseByLocation managerPoiRequestByLocation(ManagerPoiRequestByLocation parameters) {
		// TODO Auto-generated method stub
		
	
		try {
			String conference=parameters.getEventLocation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//maps
		return null;
	}

	@Override
	public ManagerResponse getPreviewById(ManagerRequest reqparam) {
		// TODO Auto-generated method stub
		
		//ManagerResponse man=service.getInfo(reqparam);
	
		//return man;
		return null;
	}
	
	
}
