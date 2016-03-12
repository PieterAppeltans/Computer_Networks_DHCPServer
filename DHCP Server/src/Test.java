import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Random;


public class Test {

	public static void main(String[] args) throws Exception {

		// Test sending bytes to UDPServer
		
		DHCPClient client = new DHCPClient(InetAddress.getByName("localhost"),9876);
		client.init();
		
		
	}
	
	public static byte[] local = {(byte)127,(byte)0,(byte)0,(byte)1};
	
}
