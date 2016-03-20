import java.io.IOException;
import java.net.*;

public class UDPClient {
	
	public UDPClient(InetAddress serverIP,int serverPort){
		this(serverIP,serverPort,576); // default receiveSize = 576
	}
	
	public UDPClient(InetAddress serverIP,int serverPort, int receiveSize){
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.receiveSize = receiveSize;
		try {
			this.clientSocket =  new DatagramSocket();
			this.clientSocket.setSoTimeout(10000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	DatagramSocket clientSocket;
	/**
	 * A method to set the size of the receive buffer
	 * @param size Int, desired new size of the receive buffer
	 * @post this.receiveSize == size
	 */
	public void setReceiveSize(int size){
		this.receiveSize = size;
	}
	
	/**
	 * Current value of the receive buffer
	 */
	private int receiveSize;
	
	/**
	 * Storing the IP address of the server
	 */
	private InetAddress serverIP;
	
	/**
	 * Storing the port to send the UDP message to.
	 */
	private int serverPort;
	
	/**
	 * Send message to the server. 
	 * @param sendData The message to be send
	 * @throws IOException When the message couldn't be send.
	 */
	public void send(byte[] sendData) throws IOException{         
		//byte[] receiveData = new byte[receiveSize];
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, this.serverIP, this.serverPort);
		this.clientSocket.send(sendPacket);
		//DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		//clientSocket.receive(receivePacket);
		//System.out.println("ANSWER FROM SERVER:\t" + DHCPMessage.printByteArrayHexa(receiveData));
		//this.clientSocket.close();
		//return receiveData;
		}
	/**
	 * Message that receive data.
	 * @return Byte array of length receiveSize containing the received bytes. 
	 */
	public byte[] receive() {
		try {
			byte[] receiveData = new byte[receiveSize];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			this.clientSocket.receive(receivePacket);
			return receiveData;
		} catch (IOException e) {
			return null;
		}
		
	}
	/**
	 * Method that close the socket.
	 */
	public void close(){
		this.clientSocket.close();
	}
	
}