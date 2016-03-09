
public class Test {

	public static void main(String[] args) throws Exception {

		// Test sending bytes to UDPServer
		UDPClient client = new UDPClient();
		byte[] bytes = new byte[6];
		for (int i = 0; i < 6; i++) {
			bytes[i] = (byte) 0xff; }
		for (int i=1;i<10;i++){
			client.send(bytes);
		}
		
		// Test building DHCP message format
		
		byte[] chaddr = new byte[16]; 
		for (int i=0;i<16;i++){
			chaddr[i] = (byte)255; // arbitrary value
		}
		
		//DHCPMessage message = new DHCPMessage(DHCPOpcode.BOOTREQUEST, DHCPHtype.IEE802, null, (short) 2, false, local, local, local, local, chaddr);
		DHCPDiscover message = new DHCPDiscover();
		message.generateMessage();
		
	}
	
	public static byte[] local = {(byte)127,(byte)0,(byte)0,(byte)1};
	
}
