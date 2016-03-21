/**
 * Class to run a DHCP server.
 * 
 * @author	Pieter Appeltans & Hans Cauwenbergh
 */
public class RunDHCPServer {
	
	public static void main(String[] args) throws Exception {
		DHCPServer server = new DHCPServer("server_configuration.txt");
		server.run();
	}
	
}