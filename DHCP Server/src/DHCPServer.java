import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class DHCPServer {
	
	public DHCPServer(String configurationAddress){
		try{
			this.processConfigurationFile(configurationAddress);
		}
		catch (IOException e){
			ErrorPrinter.print("Configuration File not found");
		}
		
		
	}
	public DHCPServer(int port,byte[] start,byte[] end, byte[] mask,int defaultLeaseTime,int maxLeaseTime,int minLeaseTime){
		this.port = port;
		this.addressKeeper = new IPAddressKeeper(start, end, mask, defaultLeaseTime, maxLeaseTime, minLeaseTime);
	} 
	// TODO Finish reading from configuration file.
	// File format
	// PORT:23532
	// START:0.0.12.12
	// END:0.0.16.12
	// MASK:14.12.0.0
	// DEFAULTLEASETIME:2423
	// MAXLEASETIME:23524
	// MINLEASETIME:2123
	private void processConfigurationFile(String configurationAddress) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(configurationAddress));
		Map<String,String> options = new HashMap<String,String>();
		try {
		    String line = br.readLine();
		    while (line != null) {
		    	String[] parsedLine =  line.split(":");
		    	options.put(parsedLine[0], parsedLine[1]);
		    	line = br.readLine();
		    }
		}
		finally {
		    br.close();
		}
		this.port = Integer.parseInt(options.get("PORT"));
		
	}

	public void run() throws Exception{
		ExecutorService executor = Executors.newFixedThreadPool(5);
		DatagramSocket serverSocket = new DatagramSocket(this.port);
		byte[] receiveData = new byte[this.bufferSize];
		while(true){                   
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			//System.out.println("RECEIVED FROM CLIENT:\t" + DHCPMessage.printByteArrayHexa(receiveData));
			Runnable thread = new DHCPServerThread(serverSocket,receivePacket,this.addressKeeper,InetAddress.getByName("localhost").getAddress());
			executor.execute(thread);
		}
	}
	
	public int getBufferSize(){
		return this.bufferSize;
	}
	
	public void setBufferSize(int buffersize){
		this.bufferSize = buffersize;
	}
	private IPAddressKeeper addressKeeper;
	private int bufferSize = 576;
	private int port;
	
}