import java.nio.ByteBuffer;


public class DHCPOffer extends DHCPMessage {
	
	public DHCPOffer(byte[] yiaddr, byte[] siaddr, byte[] chaddr, byte[] options) {
		super(DHCPOpcode.BOOTREPLY, // opcode
			  DHCPHtype.ETHERNET, // htype
			  null, // xid
			  (short) 0, // secs
			  false, // flag
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // ciaddr
			  yiaddr,
			  siaddr,
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // giaddr
			  chaddr,
			  options
			  );
	}
	
	public static byte[] getOptions(){
		return getOptions(new byte[]{ (byte) 0, (byte) 0x01, (byte) 0x51, (byte) 0x80 }, // 86400s (1 day)
					   	  new byte[]{ (byte) 0xC0, (byte) 0xA8, (byte) 0x01, (byte) 0x01 }); // 192.168.1.1 DHCP server
	}
	
	public static byte[] getOptions(byte[] leaseTime, byte[] serverIp){
		byte[] options = new byte[100]; // grootte nog aanpassen?
		ByteBuffer optionBuffer = ByteBuffer.wrap(options);
		optionBuffer.put(new byte[]{ (byte) DHCPOptions.DHCPMESSAGETYPE.getByte() }); // DHCP Message type
		optionBuffer.put(new byte[]{ (byte) 1 }); // length
		optionBuffer.put(new byte[]{ (byte) 2 }); // DHCP Offer
		optionBuffer.put(new byte[]{ (byte) DHCPOptions.IPADDRESSLEASETIME.getByte() }); // IP Address Lease Time
		optionBuffer.put(new byte[]{ (byte) 4 }); // length
		optionBuffer.put(leaseTime); // lease time in seconds
		optionBuffer.put(new byte[]{ (byte) DHCPOptions.SERVERIDENTIFIER.getByte() }); // Server identifier
		optionBuffer.put(new byte[]{ (byte) 4 }); // length
		optionBuffer.put(serverIp); // IP address of the server
		// EXTRA:
		//
		// DHCP option 1: 255.255.255.0 subnet mask
		// DHCP option 3: 192.168.1.1 router
		// DHCP option 6: DNS servers 9.7.10.15, 9.7.10.16, 9.7.10.18			(see Wikipedia)
		return options;
	}

}
