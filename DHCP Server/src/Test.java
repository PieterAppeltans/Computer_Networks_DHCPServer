import java.net.InetAddress;

public class Test {

	public static void main(String[] args) throws Exception {
		//Uncomment when using provided server.
		//DHCPClient client = new DHCPClient(InetAddress.getByName("10.33.14.246"),1234);
		// Uncomment when  using own server
		DHCPClient client = new DHCPClient(InetAddress.getByName("localhost"),9000);
		client.run();
	}
	
}