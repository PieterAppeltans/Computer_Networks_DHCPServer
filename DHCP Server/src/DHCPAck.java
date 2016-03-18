import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class DHCPAck extends DHCPMessage {

	public DHCPAck(int xid,byte[] yiaddr, byte[] siaddr, byte[] chaddr, Map<DHCPOption,byte[]> options) {
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
	
	public static Map<DHCPOption,byte[]> getDefaultOptions(){
		return DHCPAck.getDefaultOptions(86400, // 86400s (1 day)
					   	  new byte[]{ (byte) 0xC0, (byte) 0xA8, (byte) 0x01, (byte) 0x01 }, // 192.168.1.1 DHCP server
					   	  true);
	}
	
	public static Map<DHCPOption,byte[]> getDefaultOptions(int leaseTime, byte[] serverIp, boolean acknowledged){
		ByteBuffer buf = ByteBuffer.allocate(4);
		buf.putInt(leaseTime);
		byte [] t = new byte[4];
		buf.get(t);
		Map<DHCPOption,byte[]> options = new HashMap<DHCPOption,byte[]>();
		if (acknowledged){
			options.put(DHCPOption.DHCPMESSAGETYPE, new byte[] {DHCPMessageType.DHCPACK.getByte()});
		} else {
			options.put(DHCPOption.DHCPMESSAGETYPE, new byte[] {DHCPMessageType.DHCPNAK.getByte()});
		}
		options.put(DHCPOption.IPADDRESSLEASETIME, t);
		options.put(DHCPOption.SERVERIDENTIFIER, serverIp);
		// EXTRA:
		// 
		// DHCP option 1: 255.255.255.0 subnet mask
		// DHCP option 3: 192.168.1.1 router
		// DHCP option 6: DNS servers 9.7.10.15, 9.7.10.16, 9.7.10.18			(see Wikipedia)
		return options;
	}
	
}