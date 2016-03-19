import java.util.HashMap;
import java.util.Map;

public class DHCPDiscover extends DHCPMessage {

	public DHCPDiscover(int xid,byte[] chaddr, Map<DHCPOption,byte[]> options) {
		
		super(DHCPOpcode.BOOTREQUEST, // opcode
			  DHCPHtype.ETHERNET, // htype
			  xid, // xid
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
	
	public static Map<DHCPOption,byte[]> getDefaultOptions(){
		return DHCPDiscover.getDefaultOptions(null); // no specific IP requested
	}
	
	public static Map<DHCPOption,byte[]> getDefaultOptions(byte[] requestedIP){
		Map<DHCPOption,byte[]> options = new HashMap<DHCPOption,byte[]>();
		options.put(DHCPOption.DHCPMESSAGETYPE,new byte[]{DHCPMessageType.DHCPDISCOVER.getByte()});
		if (requestedIP != null){
			options.put(DHCPOption.REQUESTEDIPADDRESS,requestedIP);
		}
		// EXTRA:
		//
		// DHCP option 55: Parameter Request List:
		//  			   Request Subnet Mask (1), Router (3), Domain Name (15), Domain Name Server (6)			(see Wikipedia)
		return options;
	}
	
	@Override
	public String getClassName() {
		return "DHCPDISCOVER";
	}
	
}