import java.nio.ByteBuffer;


public class DHCPDiscover extends DHCPMessage {

	public DHCPDiscover(byte[] chaddr, byte[] options) {
		
		super(DHCPOpcode.BOOTREQUEST, // opcode
			  DHCPHtype.ETHERNET, // htype
			  null, // xid
			  (short) 0, // secs
			  true, // flag
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // ciaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // yiaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // siaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // giaddr
			  chaddr,
			  options
			  );
	}
	
	public static byte[] getOptions(){
		return getOptions(new byte[]{ (byte) 0xC0, (byte) 0xA8, (byte) 0x01, (byte) 0x64 }); // 192.168.1.100 requested
	}
	
	public static byte[] getOptions(byte[] requestedIp){
		byte[] options = new byte[100]; // grootte nog aanpassen?
		ByteBuffer optionBuffer = ByteBuffer.wrap(options);
		optionBuffer.put(new byte[]{ (byte) DHCPOptions.DHCPMESSAGETYPE.getByte() }); // DHCP Message type
		optionBuffer.put(new byte[]{ (byte) 1 }); // length
		optionBuffer.put(new byte[]{ (byte) 1 }); // DHCP Discover
		if (requestedIp != null){
			optionBuffer.put(new byte[]{ (byte) DHCPOptions.REQUESTEDIPADDRESS.getByte() }); // Requested IP Address
			optionBuffer.put(new byte[]{ (byte) 4 }); // length
			optionBuffer.put(requestedIp);
		}
		// EXTRA:
		//
		// DHCP option 55: Parameter Request List:
		//  			   Request Subnet Mask (1), Router (3), Domain Name (15), Domain Name Server (6)			(see Wikipedia)
		return options;
	}

}
