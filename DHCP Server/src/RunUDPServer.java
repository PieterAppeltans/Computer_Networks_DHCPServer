import java.net.InetAddress;


public class RunUDPServer {
	public static void main(String[] args) throws Exception {
		UDPServer server = new UDPServer(9876);
		server.run();
	}
}
