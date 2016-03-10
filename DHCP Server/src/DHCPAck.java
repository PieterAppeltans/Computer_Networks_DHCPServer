
public class DHCPAck extends DHCPMessage {

	public DHCPAck(byte[] yiaddr, byte[] siaddr, byte[] chaddr, byte[] options) {
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
		// DHCP option 53: DHCP ACK (value=5) or DHCP NAK (value=6)
		// DHCP option 1: 255.255.255.0 subnet mask
		// DHCP option 3: 192.168.1.1 router
		// DHCP option 51: 86400s (1 day) IP address lease time
		// DHCP option 54: 192.168.1.1 DHCP server
		// DHCP option 6: DNS servers 9.7.10.15, 9.7.10.16, 9.7.10.18			(see Wikipedia)
	}
	
}
