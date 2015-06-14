package rpctest;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("unused")
public class RPCServerUtil {
	private static int serverPort =Utils.PORT;
	public static SessionManager sessionmanager= new SessionManager();
	
	public static int getPort(){
		  return serverPort;
	  }
	public static String readContent(String[] dataContent)
	{
		String callid = dataContent[0];
		String packetdata = dataContent[2];
		String sessionid = Utils.parseSessionIDFromString(packetdata);
		
		String response=null;
		String sessionValues= sessionmanager.getDataFromTable(sessionid);
		
		
		if(sessionValues==null)
		{
			response=callid + Utils.NETWORK_DELIMITER;
		}
		else
		{
			String[] Array = sessionValues.split(Utils.SESSION_DATA_DELIMITER);
			response=callid+Utils.NETWORK_DELIMITER+Array[1]+Utils.NETWORK_DELIMITER+Array[0]+Utils.NETWORK_DELIMITER+Array[2];
			System.out.println("Server data: "+ Array[1]+"_"+Array[0]+"_"+Array[2]);
		}
		return response;
	}
	
	public static String writeContent(String[] dataContent)
	{
		String callid = dataContent[0];
		String packetdata = dataContent[2];
		String response;
		String sessionid = Utils.parseSessionIDFromString(packetdata);
		String version = Utils.parseVersionFromString(packetdata);
		String message = Utils.parseSessionStateFromString(packetdata);
		String expirationTime = Utils.parseExpirationTimeFromString(packetdata);
		
		String sessionData = version+Utils.SESSION_DATA_DELIMITER+message+Utils.SESSION_DATA_DELIMITER+expirationTime;
		System.out.println("Server data: "+version+Utils.SESSION_DATA_DELIMITER+message+Utils.SESSION_DATA_DELIMITER+expirationTime);
		sessionmanager.putDataIntoTable(sessionid, sessionData);
		
		response=callid + Utils.NETWORK_DELIMITER;
		return response;
	}
	public static String retCallID(String[] dataContent)
	{
		String callid = dataContent[0] ;
		String response;
		
		response=callid+ Utils.NETWORK_DELIMITER;
		return response;
	}

}
