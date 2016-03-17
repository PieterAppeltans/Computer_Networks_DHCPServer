import java.util.HashMap;
import java.util.Map;

public class DHCPAck extends DHCPMessage {

	public DHCPAck(int xid,byte[] yiaddr, byte[] siaddr, byte[] chaddr, Map<DHCPOptions,byte[]> options) {
		super(DHCPOpcode.BOOTREPLY, // opcode
			  DHCPHtype.ETHERNET, // htype
			  xid, // xid
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
	
	public static Map<DHCPOptions,byte[]> getDefaultOptions(){
		return DHCPAck.getDefaultOptions(new byte[]{ (byte) 0, (byte) 0x01, (byte) 0x51, (byte) 0x80 }, // 86400s (1 day)
					   	  new byte[]{ (byte) 0xC0, (byte) 0xA8, (byte) 0x01, (byte) 0x01 }, // 192.168.1.1 DHCP server
					   	  true);
	}
	
	public static Map<DHCPOptions,byte[]> getDefaultOptions(byte[] leaseTime, byte[] serverIp, boolean acknowledged){
		Map<DHCPOptions,byte[]> options = new HashMap<DHCPOptions,byte[]>();
		if (acknowledged){
			options.put(DHCPOptions.DHCPMESSAGETYPE, new byte[] {DHCPMessageType.DHCPACK.getByte()});
		} else {
			options.put(DHCPOptions.DHCPMESSAGETYPE, new byte[] {DHCPMessageType.DHCPNAK.getByte()});
		}
		options.put(DHCPOptions.IPADDRESSLEASETIME, leaseTime);
		options.put(DHCPOptions.SERVERIDENTIFIER, serverIp);
		// EXTRA:
		// 
		// DHCP option 1: 255.255.255.0 subnet mask
		// DHCP option 3: 192.168.1.1 router
		// DHCP option 6: DNS servers 9.7.10.15, 9.7.10.16, 9.7.10.18			(see Wikipedia)
		return options;
	}
	
}