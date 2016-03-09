import java.io.*; 
import java.net.*;

public class UDPClient {
	public UDPClient(){
	}
	
	public void send(byte[] sendData) throws Exception{       
		DatagramSocket clientSocket = new DatagramSocket();     
		byte[] receiveData = new byte[1024];
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,InetAddress.getByName("localhost"),9876);
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		System.out.println("ANSWER FROM SERVER:\t" + DHCPMessage.printByteArrayHexa(receiveData));
		clientSocket.close();
		} 
}
