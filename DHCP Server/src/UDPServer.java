import java.io.*;
import java.net.*;
public class UDPServer {
	public static void main(String args[]) throws Exception{          
		DatagramSocket serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while(true){                   
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			System.out.println("RECEIVED: " + receiveData);
			InetAddress IPAddress = receivePacket.getAddress(); 
			int port = receivePacket.getPort();
			sendData = receiveData;
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
			}
		} 
	} 

