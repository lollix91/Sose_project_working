package it.univaq.disim.sose.conferencemanager.manager.business.impl.ws;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

@Service
public class WebServiceManagerServiceImpl implements ManagerService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(WebServiceManagerServiceImpl.class);
	
	@Value("#{cfg.range}")
	private int range;

	@Override
	public ManagerResponse getInfo(ManagerRequest reqparam) {		
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
		
		
		return man;
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




	
	
	

}
