
public class Test {

	public static void main(String[] args) throws Exception {

		// Test sending bytes to UDPServer
		UDPClient client = new UDPClient();
		byte[] bytes = new byte[6 ];
		for (int i = 0; i < 6; i++) {
			bytes[i] = (byte) 0xff; }
		for (int i=1;i<10;i++){
			client.send(bytes);
		}
		
		// Test building DHCP message format
		MessageBuilder builder = new MessageBuilder();
		builder.generateMessage(DHCPOpcode.BOOTREQUEST, DHCPhtype.IEE802, null, (short) 2, local, local, local, local);
		
	}
	
	public static byte[] local = {(byte)127,(byte)0,(byte)0,(byte)1};
	
}
