import java.nio.ByteBuffer;


public class DHCPRequest extends DHCPMessage {

	public DHCPRequest(byte[] siaddr, byte[] chaddr, byte[] options) {
		super(DHCPOpcode.BOOTREQUEST, // opcode
			  DHCPHtype.ETHERNET, // htype
			  null, // xid
			  (short) 0, // secs
			  false, // flag
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // ciaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // yiaddr
			  siaddr,
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // giaddr
			  chaddr,
			  options
			  );
	}
	
	public static byte[] getOptions(){
		return getOptions(new byte[]{ (byte) 0xC0, (byte) 0xA8, (byte) 0x01, (byte) 0x64 }, // 192.168.1.100 requested
					   	  new byte[]{ (byte) 0xC0, (byte) 0xA8, (byte) 0x01, (byte) 0x01 }); // 192.168.1.1 DHCP server
	}
	
	public static byte[] getOptions(byte[] requestedIp, byte[] serverIp){
		byte[] options = new byte[100]; // grootte nog aanpassen?
		ByteBuffer optionBuffer = ByteBuffer.wrap(options);
		optionBuffer.put(new byte[]{ (byte) DHCPOptions.DHCPMESSAGETYPE.getByte() }); // DHCP Message type
		optionBuffer.put(new byte[]{ (byte) 1 }); // length
		optionBuffer.put(new byte[]{ (byte) 3 }); // DHCP Request
		optionBuffer.put(new byte[]{ (byte) DHCPOptions.REQUESTEDIPADDRESS.getByte() }); // Requested IP Address
		optionBuffer.put(new byte[]{ (byte) 4 }); // length
		optionBuffer.put(requestedIp); // lease time in seconds
		optionBuffer.put(new byte[]{ (byte) DHCPOptions.SERVERIDENTIFIER.getByte() }); // Server identifier
		optionBuffer.put(new byte[]{ (byte) 4 }); // length
		optionBuffer.put(serverIp); // IP address of the server
		return options;
	}

	
}
