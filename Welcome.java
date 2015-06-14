package rpctest;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Welcome
 */
@WebServlet("/Welcome")
@SuppressWarnings("unused")
public class Welcome extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static SessionManager sessionmanager = new SessionManager();
	
	String DEFAULT_MSG = "Hello User!";
	int COOKIE_MAX_AGE = 240;
	
	String CookieID = "CS5300PROJ1SESSION";
	String serverid = "";
	String sessionID = "";
	int versionNum = 0;
	String locationmetadata = "";
	String sessionstate = "";
	Date expirationtime = null;
	String cookieValue ="";
	Cookie newCookie = null;
	SessionData sessionData = null;
	
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Welcome() {
        super();
       // CleanUpStarter cleanup = null;
        //Thread t = new Thread(cleanup);
        //t.start();
                
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/HTML");
		PrintWriter out = response.getWriter();
		
		
		
		/* get cookies from request */
		Cookie[] cookies = request.getCookies();
		String cookieValue = getCookieValue(cookies);
		SessionData sessionData = null;
		String display;
		String replace = request.getParameter("Replace");
		
		RequestDispatcher view;
		boolean logout = false;
		
		/*get server ID */
		serverid = getServletName();
		
		//get session data :
		
		sessionData = SessionManager.getSession();
		
		if(sessionData ==  null){
			if(cookieValue != null){
				display = "Hey! You're not Supposed to Be Here!";
			}
		
		}
		else{

			/* User pressed replace */
			if (replace != null){ 
				
					/* get sessiondata for this sessionID 
					 * 
					 *update sessionstate 
					 */
					sessionstate = replace;
					sessionData.setSessionState(sessionstate);
					
					/* update version number */
					
					sessionData.incrementVersionNum();
					
			
					/* update time stamp */
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.SECOND, 300);
					expirationtime = cal.getTime();
					sessionData.setExpirationTime(expirationtime);
					
					//TODO: Store session with replication
					
					/* create new cookie for user */
					cookieValue = sessionID + "_" + versionNum + "_" + locationmetadata;
					Cookie newCookie = new Cookie(CookieID, cookieValue);
					newCookie.setMaxAge(COOKIE_MAX_AGE);
					response.addCookie(newCookie);
			}
			else if(request.getParameter("Refresh") != null){
				
				/* User pressed Refresh */
				
					/* get sessiondata for this sessionID */
					
					sessionstate = sessionData.getSessionState();
					if(sessionstate == null)
						sessionstate = DEFAULT_MSG;
					
					sessionData.incrementVersionNum();
					
					/* update time stamp */
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.SECOND, 300);
					expirationtime = cal.getTime();
					sessionData.setExpirationTime(expirationtime);

					
					//store session with replication
					
					
					cookieValue = sessionID + "_" + versionNum + "_" + locationmetadata;
					Cookie newCookie = new Cookie(CookieID, cookieValue);
					newCookie.setMaxAge(COOKIE_MAX_AGE);
					response.addCookie(newCookie);
					
				}
				else if(request.getParameter("Logout")!= null){
				
				/*User pressed Logout */
				
				logout = true;
				
				/* get sessiondata for this sessionID */
				expirationtime =null;
				sessionmanager.removeDataFromTable(sessionID);
				cookieValue = null;
				Cookie newCookie = new Cookie(CookieID, cookieValue);
				newCookie.setValue(null);
				newCookie.setMaxAge(0);
				response.addCookie(newCookie);
				}
				else{
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.SECOND, 300);
					expirationtime = cal.getTime();
					sessionData = new SessionData(DEFAULT_MSG,Server_Utils.getLocalIPAddress(), 0, expirationtime );
				}
			
		}		
	
		
		if(!logout){
			//request.setAttribute("serverid", serverid);
			request.setAttribute("message", sessionstate);
			request.setAttribute("cookievalue", cookieValue);
			request.setAttribute("sessionexptime", expirationtime);
			view = request.getRequestDispatcher("Welcome.jsp");
			view.forward(request, response);	
		}
		else{
			request.setAttribute("message", "You have Logged out");
			view = request.getRequestDispatcher("Logout.jsp");
			view.forward(request, response);
			
		}
			
}
			
	

	private String getCookieValue(Cookie[] cookies) {

		String myCookieValue = null;
		if(cookies == null){
			return null;
		}
				
		for(int i = 0; i < cookies.length; i++){
			if(cookies[i].getName().equals(CookieID)){
				myCookieValue = cookies[i].getValue();
				break;
			}
		}
		return myCookieValue;
	}

	public String getSessionID(Cookie[] cookies){
		if(cookies == null){
			return null;
		}
		
		String sessionID = null, versionNum = null;
		
		for(int i = 0; i < cookies.length; i++){
			if(cookies[i].getName().equals(CookieID)){
				String[] sessionsplit = cookies[i].getValue().split("_");
				sessionID = sessionsplit[0];
				versionNum = sessionsplit[1];
			}
		}
		
		return sessionID;
		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
