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
		// DHCP option 55: Parameter Request List:
		//  			   Request Subnet Mask (1), Router (3), Domain Name (15), Domain Name Server (6)			(see Wikipedia)
	}
	
	public static byte[] getOptions(){
		return getOptions(null);
	}
	
	public static byte[] getOptions(byte[] requestedIp){
		byte[] options = new byte[100]; // grootte nog aanpassen?
		ByteBuffer optionBuffer = ByteBuffer.wrap(options);
		optionBuffer.put(new byte[]{ (byte) 53 }); // DHCP Message type
		optionBuffer.put(new byte[]{ (byte) 1 }); // length
		optionBuffer.put(new byte[]{ (byte) 1 }); // DHCP Discover
		if (requestedIp != null){
			optionBuffer.put(new byte[]{ (byte) 50 }); // Requested IP Address
			optionBuffer.put(new byte[]{ (byte) 4 }); // length
			optionBuffer.put(requestedIp);
		}
		return options;
	}

}
