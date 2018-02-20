package it.univaq.disim.sose.conferencemanager.manager.business.impl.ws;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.gson.Gson;

import it.univaq.disim.sose.conferencemanager.manager.ConferenceType;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class WebServiceManagerServiceImpl implements ManagerService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(WebServiceManagerServiceImpl.class);
	
	@Value("#{cfg.range}")
	private int range;

	
	@GET
    @Path("/getInfo")
	@Produces({MediaType.APPLICATION_JSON})
	@Override
	public Response getInfo(@QueryParam("id") String id) {		
		// TODO Auto-generated method stub
		
		LOGGER.info("CALLED GETINFO BY ID");
		
		
		PreviewService ps = new PreviewService();
		PreviewPT pt = ps.getPreviewPort();
		PreviewRequest request = new PreviewRequest();
		request.setIdConference(id);
		
		PreviewResponse response = pt.previewConferenceRequest(request);
		
		ManagerResponse man=new ManagerResponse();
		man.setConferenceID(id);
		man.setName(response.getName());
		man.setAbstract(response.getAbstract());
		man.setLongitude(response.getLongitude());
		man.setLatitude(response.getLatitude());
		man.setCity(response.getCity());
		man.setDate(response.getDate());
		man.setUrlImage(response.getUrlImage());
		man.setUrlPDFs(response.getUrlPDFs());
		
		Gson gson = new Gson();
		JSONObject json=new JSONObject(gson.toJson(man));
		System.out.println(json);
		
		return Response.ok(man).build();

	}

	@GET
    @Path("/getJsonPois")
	@Produces({MediaType.APPLICATION_JSON})
	@Override
	public Response getJsonPois(@QueryParam("id") String id) throws Exception {
		// TODO Auto-generated method stub
		
		LOGGER.info("CALLED GETJSONPOIS BY ID");
		
		PreviewService ps = new PreviewService();
		PreviewPT pt = ps.getPreviewPort();
		PreviewRequest request = new PreviewRequest();
		request.setIdConference(id);

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
		
		con.setRequestMethod("GET");

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
		
		
		JSONObject json=new JSONObject(responsejson.toString());
		System.out.println(json);
		
		return Response.ok(responsejson.toString()).build();
	}

	@GET
    @Path("/getConferenceByDate")
	@Produces({MediaType.APPLICATION_JSON})
	@Override
	public Response getConferenceByDate(@QueryParam("date") String date) throws Exception {
		// TODO Auto-generated method stub
		
		LOGGER.info("CALLED GETCONFERENCE BY DATE");
		
		PreviewService ps = new PreviewService();
		PreviewPT pt = ps.getPreviewPort();
		PreviewRequest request = new PreviewRequest();
		PreviewResponse response = new PreviewResponse();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date convertedCurrentDate = sdf.parse(date);
		
	    java.util.Calendar c = java.util.Calendar.getInstance();
	    c.setTime(convertedCurrentDate);
	    c.add(java.util.Calendar.DATE, 1);
	    Date convertedNextDate = c.getTime();
		
        com.google.api.services.calendar.Calendar service =
            Calendar.getCalendarService();

        DateTime now = new DateTime(convertedCurrentDate);
        
        Events events = service.events().list("primary")
            .setMaxResults(1)
            .setTimeMin(now)
            .setTimeMax(new DateTime(convertedNextDate))
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute();
        
        
        List<Event> items = events.getItems();
        if (items.size() == 0) {
            System.out.println("No upcoming events found.");
            return Response.ok(null).build();
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                
                request.setIdConference(event.getId());
        		
        		response = pt.previewConferenceRequest(request);
        		
        		response.setIdConference(event.getId());
        		
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
        		
        		con.setRequestMethod("GET");

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

        		JSONObject jsonmap=new JSONObject(responsejson.toString());
        		System.out.println(jsonmap);
        		
        		Gson gson = new Gson();
        	    String jsonString = gson.toJson(response);
        	    try {
        	        JSONObject json = new JSONObject(jsonString);
        	        return Response.ok(json.toString()).build();
        	    } catch (JSONException e) {
        	        // TODO Auto-generated catch block
        	        e.printStackTrace();
        	    }
        	    
        		
            }
        }
		
        return Response.ok(null).build();
	}

	@GET
    @Path("/getAllConferencesByActualDate")
    @Consumes("text/plain")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Response getAllConferencesByActualDate(@QueryParam("date") String date) throws Exception {
		// TODO Auto-generated method stub

		LOGGER.info("CALLED GETALLCONFERENCES BY ACTUAL DATE");
		
		PreviewService ps = new PreviewService();
		PreviewPT pt = ps.getPreviewPort();
		PreviewRequest request = new PreviewRequest();
		PreviewResponse response = new PreviewResponse();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date convertedCurrentDate = sdf.parse(date);
		
		List<ConferenceType> conferencelist = new ArrayList<ConferenceType>();

        com.google.api.services.calendar.Calendar service =
            Calendar.getCalendarService();

        DateTime now = new DateTime(convertedCurrentDate);
        
        Events events = service.events().list("primary")
            .setMaxResults(10)
            .setTimeMin(now)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute();
        
        
        List<Event> items = events.getItems();
        if (items.size() == 0) {
            System.out.println("No upcoming events found.");
            return Response.ok(null).build();
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                
                request.setIdConference(event.getId());
                
                response = pt.previewConferenceRequest(request);

                response.setIdConference(event.getId());
                
                ConferenceType actualconference = new ConferenceType();
                
                actualconference.setName(response.getName());
                actualconference.setAbstract(response.getAbstract());
                actualconference.setCity(response.getCity());
                actualconference.setUrlImage(response.getUrlImage());
                actualconference.setUrlPDFs(response.getUrlPDFs());
                actualconference.setDate(response.getDate());
                actualconference.setLatitude(response.getLatitude());
                actualconference.setLongitude(response.getLongitude());
                actualconference.setIdConference(response.getIdConference());
                conferencelist.add(actualconference);
                
        	    
            }
            
            GenericEntity<List<ConferenceType>> list = new GenericEntity<List<ConferenceType>>(conferencelist) {};
            return Response.ok(list,MediaType.APPLICATION_JSON).build();
            
        }
		
        
	}


}
