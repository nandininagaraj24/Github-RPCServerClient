package rpctest;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class RPCServer implements Runnable {
   
  DatagramSocket rpcSocket = null;
  int serverPort = Utils.PORT ;
  
     
   public RPCServer() {
      try {
    	 
    
    	serverPort=RPCServerUtil.getPort();
    	rpcSocket = new DatagramSocket(serverPort);
    	 
    	
	  	       
      } catch (SocketException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
  
	public void run()
	{
		while(true)
		{
			
			byte[] inputBuffer= new byte[512];
			byte[] outputBuffer= new byte[512];
			
			
			DatagramPacket receivePacket= new DatagramPacket(inputBuffer, inputBuffer.length);
		    
			try
			{
				/*Receive packet here */
				rpcSocket.receive(receivePacket);
				InetAddress returnAddr= receivePacket.getAddress();
				int returnPort= receivePacket.getPort();
				
				String[] packetData = new String(receivePacket.getData(),0,receivePacket.getLength()).split(Utils.NETWORK_DELIMITER);
				
				outputBuffer=actionToTake(packetData);
				
				DatagramPacket sendPacket= new DatagramPacket(outputBuffer, outputBuffer.length, returnAddr, returnPort);
				
				rpcSocket.send(sendPacket);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private byte[] actionToTake(String[] packetData)
	{
	    //packet in the forn callID@OPcode@packetData
		
		int opCode = Integer.parseInt(packetData[1]);
		String response=null;
		ByteArrayOutputStream opStream = new ByteArrayOutputStream();
	    ObjectOutput output;
		//Read session
		if(opCode==1) 
		{
			response =RPCServerUtil.readContent(packetData);
			
		}
		//Write session
		if(opCode==2)
		{
			response=RPCServerUtil.writeContent(packetData);
		}
		//TODO: GOSSIP with servers
		if(opCode==3) 
		{
			response=RPCServerUtil.retCallID(packetData);
		}
			        
		System.out.println("Server response: " + response);
		
				
	  try {
	    	output = new ObjectOutputStream(opStream);
	    	output.writeObject(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    byte[] outputArray = opStream.toByteArray();
		
		return outputArray;
	}
}
			
