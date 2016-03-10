
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
		// DHCP option 53: DHCP Request
		// DHCP option 50: 192.168.1.100 requested
		// DHCP option 54: 192.168.1.1 DHCP server			(see Wikipedia)
	}

	
}
