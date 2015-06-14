package rpctest;
/**
 * 
 */


import java.net.DatagramPacket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author karteeka
 *
 */
public class Utils {

	public static final int PORT = 5300;
	public static String SESSION_DATA_DELIMITER = "#";
	public static String NETWORK_DELIMITER = "@";
	
	public static Date parseExpTime(String state) {
		// TODO Auto-generated method stub
		
		Date exp = null; 
		String[] pairs = state.split(SESSION_DATA_DELIMITER);
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
		try {
			exp = formatter.parse(pairs[3]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exp;
	}

	public static String parseReceivePacket(DatagramPacket receivePacket) {
		String callid;
		
		String[] packetData = new String(receivePacket.getData(),0,receivePacket.getLength()).split(Utils.NETWORK_DELIMITER);
		
		callid = packetData[0];
		return callid;
	}

	public static String parseSessionIDFromString(String packetdata) {
		
		
		String[] pairs = packetdata.split(SESSION_DATA_DELIMITER);
		//Packet data is in the form of SESSIONID#VERSIONNUM#SESSIONSTATE#EXPIRATIONTIME
		String sessionID = pairs[0];
		System.out.println("Session ID is:" +sessionID);
		return sessionID;
	}

	public static String parseVersionFromString(String packetdata) {
		
		String[] pairs = packetdata.split(SESSION_DATA_DELIMITER);
		//Packet data is in the form of SESSIONID#VERSIONNUM#SESSIONSTATE#EXPIRATIONTIME
		String version = pairs[1];
		System.out.println("Version is:" +version);
		return version;
		
	}

	public static String parseSessionStateFromString(String packetdata) {
		
		String[] pairs = packetdata.split(SESSION_DATA_DELIMITER);
		//Packet data is in the form of SESSIONID#VERSIONNUM#SESSIONSTATE#EXPIRATIONTIME
		String state = pairs[2];
		System.out.println("Session state is:" +state);
		return state;
		
	}
	
	public static String parseExpirationTimeFromString(String packetdata) {
		
		String[] pairs = packetdata.split(SESSION_DATA_DELIMITER);
		//Packet data is in the form of SESSIONID#VERSIONNUM#SESSIONSTATE#EXPIRATIONTIME
		String expTime = pairs[3];
		System.out.println("Exp Time is:" +expTime);
		return expTime;
		
	}
}
