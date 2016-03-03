import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.DatagramPacket;

public class DatagramTest {

	public static void main(String[] args) throws IOException{
		byte[] sendBytes = new byte[6];
		byte[] receiveBytes = new byte[6];
		for (int i = 0; i < 6; i++) {
			sendBytes[i] = (byte) 0xff; } 
		DatagramSocket s1 = new DatagramSocket(8890);
		DatagramSocket s2 = new DatagramSocket(8891);
		SocketAddress Socket1=s1.getLocalSocketAddress();
		DatagramPacket packet = new DatagramPacket(bytes,bytes.length, Socket1);
		try {
			s2.connect(Socket1);
			s2.send(packet);
		} catch (Exception e) {
			s1.close();
			s2.close();
			e.printStackTrace();
		}
		s2.close();
		DatagramPacket recPacket = new DatagramPacket(new byte[6], 6);
		s1.receive(recPacket);
		s1.close();
		System.out.println(recPacket.getData().toString());
	}
}
