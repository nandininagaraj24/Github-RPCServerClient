package rpctest;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.UUID;
import java.net.UnknownHostException;

//Hey man!

@SuppressWarnings("unused")
public class RPCClient
{
	public static Integer maxPacketSize = 512;
	public static Integer destPort = Utils.PORT; //Destination Port Number: portProj1bRPC   =   5300; 
	public static Integer waitTime = 20; // The wait time is mentioned in seconds;
	private final int SOCKET_TIMEOUT = 6*1000;
	DatagramSocket rpcSocket = null;
	public static int CallID = 0;
	
	public static int getCallID(){
		return CallID += 1;
	}
	
	public RPCClient(){
		
		try {
			rpcSocket = new DatagramSocket();
		} catch (SocketException e1) {
			
			e1.printStackTrace();
		}
		
			try {
				rpcSocket.setSoTimeout(SOCKET_TIMEOUT);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public String ForwardToServer(String serverIP, String data){
		DatagramPacket recvPkt = null;
		Integer temp = getCallID();
		String callID = temp.toString();
		
		byte [] inBuf = new byte[maxPacketSize];
		byte [] outBuf = new byte[maxPacketSize];
		outBuf = data.getBytes();
		String destAddr = "";
		
		InetAddress destinationAddr = null;
		try {
			destinationAddr = InetAddress.getByName(serverIP);
			
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}
		
		System.out.println("destport" + destPort + "destAddr" + destAddr);
		DatagramPacket sendPkt = new DatagramPacket(outBuf,outBuf.length ,destinationAddr, destPort );
		try {
			rpcSocket.send(sendPkt);
		
			System.out.println("Sent the first packet");
		DatagramPacket receivePacket= new DatagramPacket(inBuf, inBuf.length);
		
		do{
			receivePacket.setLength(inBuf.length);
			rpcSocket.receive(receivePacket);
		}while(checkCallID(callID, receivePacket));
		
		System.out.println("Received the packet: "+ receivePacket.getData().toString());
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			rpcSocket.close();
		}
		
		 //Packets are being sent 
		return inBuf.toString();
	
	}

	private boolean checkCallID(String callID2, DatagramPacket receivePacket) {
		String receivedCallID = Utils.parseReceivePacket(receivePacket);
		
		if(receivedCallID.contentEquals(callID2))
		return true;
		else
			return false;
	}
}
