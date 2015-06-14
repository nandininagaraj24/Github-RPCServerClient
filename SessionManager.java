package rpctest;
/**
 * 
 */


import java.util.concurrent.ConcurrentHashMap;



/**
 * @author karteeka
 *
 */
public class SessionManager {
	
	private ConcurrentHashMap<String, String> sessionTable= new ConcurrentHashMap<String, String>();

	public static SessionData getSession() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void putDataIntoTable(String SessionId, String SessionData){
		this.sessionTable.put(SessionId, SessionData);		
	}
	
	public String getDataFromTable(String sessionID){
		return this.sessionTable.get(sessionID);
	}

	public void removeDataFromTable(String sessionID) {
		// TODO Auto-generated method stub
		this.sessionTable.remove(sessionID);
	}
	

	
	//TODO: Store session with replication
	
	
}
