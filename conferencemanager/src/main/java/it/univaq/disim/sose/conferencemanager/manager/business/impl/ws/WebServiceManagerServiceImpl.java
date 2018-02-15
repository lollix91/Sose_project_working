package it.univaq.disim.sose.conferencemanager.manager.business.impl.ws;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.gson.Gson;

import it.univaq.disim.sose.conferencemanager.manager.ManagerRequest;
import it.univaq.disim.sose.conferencemanager.manager.ManagerResponse;
import it.univaq.disim.sose.conferencemanager.manager.business.ManagerService;
import it.univaq.disim.sose.conferencemanager.manager.business.model.Conference;
import it.univaq.disim.sose.conferencemanager.clients.PreviewPT;
import it.univaq.disim.sose.conferencemanager.clients.PreviewRequest;
import it.univaq.disim.sose.conferencemanager.clients.PreviewResponse;
import it.univaq.disim.sose.conferencemanager.clients.PreviewService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@Service
public class WebServiceManagerServiceImpl implements ManagerService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(WebServiceManagerServiceImpl.class);
	
	@Value("#{cfg.range}")
	private int range;

	@Override
	public JSONObject getInfo(ManagerRequest reqparam) {		
		// TODO Auto-generated method stub
		PreviewService ps = new PreviewService();
		PreviewPT pt = ps.getPreviewPort();
		PreviewRequest request = new PreviewRequest();
		request.setIdConference(reqparam.getIdRequest());
		
		
		
		PreviewResponse response = pt.previewConferenceRequest(request);
		
		
		/*Conference conf=new Conference();
		conf.setName(response.getName());
		conf.setLongitude(response.getLongitude());
		conf.setLatitude(response.getLatitude());
		conf.setCity(response.getCity());
		*/

		ManagerResponse man=new ManagerResponse();
		man.setConferenceID(reqparam.getIdRequest());
		man.setName(response.getName());
		man.setLongitude(response.getLongitude());
		man.setLatitude(response.getLatitude());
		man.setCity(response.getCity());
		man.setDate(response.getDate());
		man.setUrlImage(response.getUrlImage());
		man.setUrlPDFs(response.getUrlPDFs());
		
		Gson gson = new Gson();
		
		
		JSONObject json=new JSONObject(gson.toJson(man));
		System.out.println(json);
		
		return json;
	}

	@Override
	public JSONObject getJsonPois(ManagerRequest req) throws Exception {
		// TODO Auto-generated method stub
		/*
		String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670,151.1957&radius=500&types=food&name=cruise&key=AIzaSyB8YmmJamci1OdCC15vzqB2JRSS8zqIzeo";
		*/
		
		PreviewService ps = new PreviewService();
		PreviewPT pt = ps.getPreviewPort();
		PreviewRequest request = new PreviewRequest();
		request.setIdConference(req.getIdRequest());
		
		
		
		PreviewResponse response = pt.previewConferenceRequest(request);
		
		ManagerResponse man=new ManagerResponse();
		Conference conf=new Conference();
		conf.setName(response.getName());
		conf.setLongitude(response.getLongitude());
		conf.setLatitude(response.getLatitude());
		conf.setCity(response.getCity());
		
		
		man.setConferenceID(conf.getConferenceId());
		System.out.println(conf.getLatitude() +" lat");
		
		System.out.println(conf.getLongitude() +" long");
		String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+conf.getLatitude()+","+conf.getLongitude()+"&radius=500&types=food&name=cruise&key=AIzaSyB8YmmJamci1OdCC15vzqB2JRSS8zqIzeo";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "ciao");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer responsejson = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			responsejson.append(inputLine);
		}
		in.close();

		//print result
		
		
		JSONObject json=new JSONObject(responsejson.toString());
		System.out.println(json);
		
		return json;
	}

	@Override
	public JSONObject getConferenceByDate(String date) throws Exception {
		// TODO Auto-generated method stub
		
		
		
		PreviewService ps = new PreviewService();
		PreviewPT pt = ps.getPreviewPort();
		PreviewRequest request = new PreviewRequest();
		PreviewResponse response = new PreviewResponse();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date convertedCurrentDate = sdf.parse(date);
		
		
		//chiamata a calendario
        com.google.api.services.calendar.Calendar service =
            Calendar.getCalendarService();

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(convertedCurrentDate);
        
        Events events = service.events().list("primary")
            .setMaxResults(1)
            .setTimeMin(now)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute();
        
        
        List<Event> items = events.getItems();
        if (items.size() == 0) {
            System.out.println("No upcoming events found.");
            return new JSONObject();
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                //System.out.printf("%s (%s)\n", event.getSummary(), start);
                //System.out.printf(event.getId());
                request.setIdConference(event.getId());
        		
        		response = pt.previewConferenceRequest(request);
        		
        		JSONObject json=new JSONObject(response.toString());
        		System.out.println(json);
        		
        		return json;
        		
            }
        }
		
        return new JSONObject();

	}




	
	
	

}
