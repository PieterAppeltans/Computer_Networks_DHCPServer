import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;


public class DHCPDiscover extends DHCPMessage {

	public DHCPDiscover(byte[] chaddr, Map<DHCPOptions,byte[]> options) {
		
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
	public static Map<DHCPOptions,byte[]> getDefaultOptions(){
		return DHCPDiscover.getDefaultOptions(new byte[]{ (byte) 0xC0, (byte) 0xA8, (byte) 0x01, (byte) 0x64 }); // 192.168.1.100 requested
	}
	
	public static Map<DHCPOptions,byte[]> getDefaultOptions(byte[] requestedIP){
		Map<DHCPOptions,byte[]> options = new HashMap<DHCPOptions,byte[]>(); // grootte nog aanpassen?
		options.put(DHCPOptions.DHCPMESSAGETYPE,new byte[]{DHCPMessageType.DHCPDISCOVER.getByte()}); // DHCP Message type
		if (requestedIP != null){
			options.put(DHCPOptions.REQUESTEDIPADDRESS,requestedIP);
		}
		// EXTRA:
		//
		// DHCP option 55: Parameter Request List:
		//  			   Request Subnet Mask (1), Router (3), Domain Name (15), Domain Name Server (6)			(see Wikipedia)
		return options;
	}
}
