package it.univaq.disim.sose.conferencemanager.manager.business.impl.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.conferencemanager.manager.business.ManagerService;
import it.univaq.disim.sose.conferencemanager.manager.business.model.Conference;
import it.univaq.disim.sose.conferencemanager.clients.PreviewPT;
import it.univaq.disim.sose.conferencemanager.clients.PreviewRequest;
import it.univaq.disim.sose.conferencemanager.clients.PreviewResponse;
import it.univaq.disim.sose.conferencemanager.clients.PreviewService;


@Service
public class WebServiceManagerServiceImpl implements ManagerService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(WebServiceManagerServiceImpl.class);
	
	@Value("#{cfg.range}")
	private int range;


	
	@Override
	public Conference getInfo(long id) {
		// TODO Auto-generated method stub
		
		PreviewService prw=new PreviewService();
		PreviewPT pt=prw.getPreviewPort();
		PreviewRequest req=new PreviewRequest();
		req.setIdConference("2");
		PreviewResponse resp=pt.previewConferenceRequest(req);
		Conference cf=new Conference();
		cf.setName(resp.getName());
		return cf;
	}
	
	

}
