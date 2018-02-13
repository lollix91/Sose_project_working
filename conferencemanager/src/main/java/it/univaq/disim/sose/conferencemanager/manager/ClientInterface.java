package it.univaq.disim.sose.conferencemanager.manager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import it.univaq.disim.sose.conferencemanager.manager.business.ManagerService;
import it.univaq.disim.sose.conferencemanager.manager.business.impl.ws.WebServiceManagerServiceImpl;
import it.univaq.disim.sose.conferencemanager.manager.webservices.ManagerPTImpl;

/**
 * Servlet implementation class ClientInterface
 */
@WebServlet("/ClientInterface")
public class ClientInterface extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ManagerService service;
	
    /**
     * Default constructor. 
     */
    public ClientInterface() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	
        handleRequest(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void handleRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		res.setContentType("text/plain");
		
		String whatValue = req.getParameter("what");
		if(whatValue.equals("eventbyid")) {
			String idConference = req.getParameter("id");
			
			ManagerRequest reqparam = new ManagerRequest();
			reqparam.setIdRequest(idConference);

			WebServiceManagerServiceImpl wm = new WebServiceManagerServiceImpl();
			
			//ManagerPTImpl mm = new ManagerPTImpl();
			ManagerResponse man=wm.getInfo(reqparam);
			out.write("Parameter " + man.getName());
			

			
		}
		else if(whatValue.equals("list")) {
			
			ManagerRequest reqparam = new ManagerRequest();
			

			WebServiceManagerServiceImpl wm = new WebServiceManagerServiceImpl();
			
			//ManagerPTImpl mm = new ManagerPTImpl();
			//ManagerResponse man=wm.getInfo(reqparam);
			//out.write("Parameter " + man.getName());
			

			
		}
		else if(whatValue.equals("poi")) {
			
			String idConference = req.getParameter("id");
			
			ManagerRequest reqparam = new ManagerRequest();
			reqparam.setIdRequest(idConference);

			WebServiceManagerServiceImpl wm = new WebServiceManagerServiceImpl();
			
			//ManagerPTImpl mm = new ManagerPTImpl();
			JSONObject man;
			try {
				
				man = wm.getJsonPois(reqparam);
				out.write(man.toString());

			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
			

			
		}
		
		out.close();
	
	}


}
