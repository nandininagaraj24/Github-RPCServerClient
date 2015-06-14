package rpctest;
/**
 * 
 */


import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author karteeka
 *
 */
@SuppressWarnings("unused")
public class SessionData {

	private String sessionID;
	private int versionNum;
	private String sessionState;
	private Date expirationTimeStamp;
	
	
	public SessionData(String dEFAULT_MSG, String localIPAddress, int i,
			Date expirationtime) {
		// TODO Auto-generated constructor stub
		this.versionNum = i;
		this.expirationTimeStamp = expirationtime;
		String uniqueNumber = UUID.randomUUID().toString();
		this.sessionID = uniqueNumber+localIPAddress;
		this.sessionState = dEFAULT_MSG;
	}

	public String getSessionID(){
		return sessionID;
	}
	
	public int getVersionNum(){
		return versionNum;
	}
	
	public String getSessionState(){
		return sessionState;
	}
	
	public Date getExpTimeStamp(){
		return expirationTimeStamp;
	}
		
	public void setSessionState(String msg){
		this.sessionState = msg;
	}
	
	public void setVersionNum(int version){
		this.versionNum = version;
	}

	public void incrementVersionNum(){
		int version = getVersionNum();
		this.versionNum = version + 1;
	}
	public void setExpirationTime(Date expiration){
		this.expirationTimeStamp = expiration;
	}
	
	
}
