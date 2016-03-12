import java.net.InetAddress;

public class Test {

	public static void main(String[] args) throws Exception {

		// Test sending bytes to UDPServer
		
		DHCPClient client = new DHCPClient(InetAddress.getByName("localhost"),9876);
		client.init();
		
	}
	
}