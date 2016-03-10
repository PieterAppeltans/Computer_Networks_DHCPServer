
public class DHCPDiscover extends DHCPMessage {

	public DHCPDiscover() {
		super(DHCPOpcode.BOOTREQUEST, // opcode
			  DHCPHtype.ETHERNET, // htype
			  new byte[] { (byte) 0x39, (byte) 0x03, (byte) 0xF3, (byte) 0x26 }, // xid
			  (short) 0, // secs
			  true, // flag
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // ciaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // yiaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // siaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // giaddr
			  new byte[] { (byte) 0x00, (byte) 0x05, (byte) 0x3C, (byte) 0x04,
				   		   (byte) 0x8D, (byte) 0x59, (byte) 0x00, (byte) 0x00,
				   		   (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				   		   (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 } // chaddr
			  );
		// DHCP option 53: DHCP Discover
		// DHCP option 50: 192.168.1.100 requested
		// DHCP option 55: Parameter Request List:
		//  			   Request Subnet Mask (1), Router (3), Domain Name (15), Domain Name Server (6)
	}

}
