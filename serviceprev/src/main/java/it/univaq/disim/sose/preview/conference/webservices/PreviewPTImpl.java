package it.univaq.disim.sose.preview.conference.webservices;

import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.disim.sose.preview.conference.ConferenceType;
import it.univaq.disim.sose.preview.conference.PreviewMultipleRequest;
import it.univaq.disim.sose.preview.conference.PreviewMultipleResponse;
import it.univaq.disim.sose.preview.conference.PreviewPT;
import it.univaq.disim.sose.preview.conference.PreviewRequest;
import it.univaq.disim.sose.preview.conference.PreviewResponse;
//import it.univaq.disim.sose.preview.conference.PreviewService;
import it.univaq.disim.sose.preview.conference.business.PreviewService;
import it.univaq.disim.sose.preview.conference.business.model.Conference;

@Component(value = "PreviewPTImpl")
public class PreviewPTImpl implements PreviewPT {

	private static Logger LOGGER = LoggerFactory.getLogger(PreviewPTImpl.class);
	
	@Autowired
	private PreviewService service;
	
	@Override
	public PreviewResponse previewConferenceRequest(PreviewRequest conferenceId) {
		// TODO Auto-generated method stub
		
		LOGGER.info("CALLED Singlepreview");
		
		
		try {
			
		Conference conference = service.getConferenceData(conferenceId.getIdConference());
		PreviewResponse response = new PreviewResponse();
		
		response.setName(conference.getName());
		response.setAbstract(conference.getConferenceAbstract());
		response.setUrlImage(conference.getImage());
		response.setUrlPDFs(conference.getPdf());
		response.setCity(conference.getCity());
		
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(conference.getDate());
		XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		response.setDate(date);
		
		response.setLatitude(conference.getLatitude());
		response.setLongitude(conference.getLongitude());
		
		return response;
		}
		catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		
	}

	@Override
	public PreviewMultipleResponse previewMultipleConferencesRequest(PreviewMultipleRequest parameters) {
		// TODO Auto-generated method stub
		
		LOGGER.info("CALLED Multiplepreview");
		
		try {
			List<Conference> conferences = service.getConferences(parameters.getConferencesList());
			PreviewMultipleResponse response = new PreviewMultipleResponse();
			
			for (Conference conference : conferences) {
				ConferenceType actualConference = new ConferenceType();
				
				
				actualConference.setName(conference.getName());
				actualConference.setAbstract(conference.getConferenceAbstract());
				actualConference.setUrlImage(conference.getImage());
				actualConference.setUrlPDFs(conference.getPdf());
				actualConference.setCity(conference.getCity());
				
				GregorianCalendar c = new GregorianCalendar();
				c.setTime(conference.getDate());
				XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
				actualConference.setDate(date);
				
				actualConference.setLatitude(conference.getLatitude());
				actualConference.setLongitude(conference.getLongitude());
				
				response.getConferences().add(actualConference);
			}
			return response;
		} 
		catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		
	}


}
