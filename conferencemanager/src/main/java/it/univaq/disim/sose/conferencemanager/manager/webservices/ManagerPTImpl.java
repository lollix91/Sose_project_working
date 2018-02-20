package it.univaq.disim.sose.conferencemanager.manager.webservices;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import it.univaq.disim.sose.conferencemanager.manager.ManagerPT;
import it.univaq.disim.sose.conferencemanager.manager.ManagerRequestByDate;
import it.univaq.disim.sose.conferencemanager.manager.ManagerRequestListByDate;
import it.univaq.disim.sose.conferencemanager.manager.ManagerResponseByDate;
import it.univaq.disim.sose.conferencemanager.manager.ManagerResponseListByDate;
import it.univaq.disim.sose.conferencemanager.manager.business.ManagerService;


public class ManagerPTImpl implements ManagerPT {

	private static Logger LOGGER = LoggerFactory.getLogger(ManagerPTImpl.class);
	
	@Autowired
	private ManagerService service;
		

	
	@Override
	public ManagerResponseListByDate managerConferenceListRequestByDate(ManagerRequestListByDate parameters) {
		// TODO Auto-generated method stub
		
		LOGGER.info("CALLED MANAGERLISTBYDATE");
		
		ManagerResponseListByDate conferences_list = new ManagerResponseListByDate();
		
		
		return conferences_list;
	}

	@Override
	public ManagerResponseByDate managerConferenceRequestByDate(ManagerRequestByDate parameters) {
		// TODO Auto-generated method stub
		
		LOGGER.info("CALLED MANAGERBYDATE");
		
		ManagerResponseByDate response = new ManagerResponseByDate();
		
		return response;
	}

	
	
}
