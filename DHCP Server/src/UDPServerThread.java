import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServerThread implements Runnable {

	private DatagramSocket serverSocket = null;
    private DatagramPacket receivePacket = null;
    private DHCPServer server;
    
    public UDPServerThread(DatagramSocket socket, DatagramPacket packet,DHCPServer server) {
        this.serverSocket = socket;
        this.receivePacket = packet;
        this.server = server;
    }

    public void run() {
    	System.out.println(Thread.currentThread().getName() + " started");
    	InetAddress IPAddress = receivePacket.getAddress(); 
		int port = receivePacket.getPort();
		byte[] sendData = receivePacket.getData();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}