import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDPServer {
	public static void main(String args[]) throws Exception{
		ExecutorService executor = Executors.newFixedThreadPool(5);
		DatagramSocket serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[1024];
		while(true){                   
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			System.out.println("RECEIVED FROM CLIENT:\t" + MessageBuilder.printByteArrayHexa(receiveData));
			Runnable thread = new UDPServerThread(serverSocket,receivePacket);
			executor.execute(thread);
		}
	} 
} 

