
public class DHCPDiscover extends DHCPMessage {

	public DHCPDiscover(byte[] chaddr) {
		super(DHCPOpcode.BOOTREQUEST, // opcode
			  DHCPHtype.ETHERNET, // htype
			  null, // xid
			  (short) 0, // secs
			  true, // flag
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // ciaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // yiaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // siaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // giaddr
			  chaddr
			  );
		// DHCP option 53: DHCP Discover
		// DHCP option 50: 192.168.1.100 requested
		// DHCP option 55: Parameter Request List:
		//  			   Request Subnet Mask (1), Router (3), Domain Name (15), Domain Name Server (6)			(see Wikipedia)
	}

}
