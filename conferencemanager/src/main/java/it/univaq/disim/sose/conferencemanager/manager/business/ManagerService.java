package it.univaq.disim.sose.conferencemanager.manager.business;

import it.univaq.disim.sose.conferencemanager.manager.ManagerRequestByDate;
import it.univaq.disim.sose.conferencemanager.manager.business.model.Conference;


public interface ManagerService {

	  Conference getInfo(long id);
	  
	  void getEventByDate(ManagerRequestByDate param);
	
}
