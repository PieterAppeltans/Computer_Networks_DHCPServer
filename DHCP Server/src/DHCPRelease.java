import java.util.HashMap;
import java.util.Map;

public class DHCPRelease extends DHCPMessage{

	public DHCPRelease(int xid,byte[] ciaddr, byte[] chaddr, Map<DHCPOptions,byte[]> options) {
		
		super(DHCPOpcode.BOOTREQUEST, // opcode
			  DHCPHtype.ETHERNET, // htype
			  xid, // xid
			  (short) 0, // secs
			  false, // flag
			  ciaddr, // ciaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // yiaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // siaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // giaddr
			  chaddr,
			  options
			  );
	}
	
	public static Map<DHCPOptions,byte[]> getOptions(){
		Map<DHCPOptions,byte[]> options = new HashMap<DHCPOptions,byte[]>();
		options.put(DHCPOptions.DHCPMESSAGETYPE,new byte[]{DHCPMessageType.DHCPRELEASE.getByte()});
		return options;
	}
	
}