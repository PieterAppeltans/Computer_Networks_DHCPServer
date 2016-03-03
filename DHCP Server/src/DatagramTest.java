import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;


public class DatagramTest {
	public static void main() throws SocketException{
		DatagramSocket s1 = new DatagramSocket(8888);
		DatagramSocket s2 = new DatagramSocket(8889);
		SocketAddress IP=s1.getLocalSocketAddress();
		System.out.println(IP.toString());
	}
}
