import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
// TODO: delete?
public class UDPServer {
	
	public UDPServer(int port){
		this.port = port;
	}
	
	public void run() throws Exception{
		ExecutorService executor = Executors.newFixedThreadPool(5);
		DatagramSocket serverSocket = new DatagramSocket(this.port);
		byte[] receiveData = new byte[this.bufferSize];
		while(true){                   
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			//System.out.println("RECEIVED FROM CLIENT:\t" + DHCPMessage.printByteArrayHexa(receiveData));
			Runnable thread = new UDPServerThread(serverSocket,receivePacket,server);
			executor.execute(thread);
		}
	}
	
	public int getBufferSize(){
		return this.bufferSize;
	}
	
	public void setBufferSize(int buffersize){
		this.bufferSize = buffersize;
	}
	private DHCPServer server = new DHCPServer("options.txt");
	private int bufferSize = 576;
	private int port;
	
}