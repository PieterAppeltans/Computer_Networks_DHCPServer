import java.util.HashMap;
import java.util.Map;

public class DHCPRequest extends DHCPMessage {

	public DHCPRequest(int xid,byte[] siaddr, byte[] chaddr, Map<DHCPOptions,byte[]> options) {
		super(DHCPOpcode.BOOTREQUEST, // opcode
			  DHCPHtype.ETHERNET, // htype
			  xid, // xid
			  (short) 0, // secs
			  false, // flag
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // ciaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // yiaddr
			  siaddr,
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // giaddr
			  chaddr,
			  options
			  );
	}
	
	public static Map<DHCPOptions,byte[]> getDefaultOptions(){
		return getDefaultOptions(new byte[]{ (byte) 0xC0, (byte) 0xA8, (byte) 0x01, (byte) 0x64 }, // 192.168.1.100 requested
					   	  new byte[]{ (byte) 0xC0, (byte) 0xA8, (byte) 0x01, (byte) 0x01 }); // 192.168.1.1 DHCP server
	}
	
	public static Map<DHCPOptions,byte[]> getDefaultOptions(byte[] requestedIP, byte[] serverIP){
		Map<DHCPOptions,byte[]> options = new HashMap<DHCPOptions,byte[]>(); // grootte nog aanpassen?
		options.put(DHCPOptions.DHCPMESSAGETYPE, new byte[] {DHCPMessageType.DHCPREQUEST.getByte()});
		options.put(DHCPOptions.REQUESTEDIPADDRESS,requestedIP); // Requested IP Address
		options.put(DHCPOptions.SERVERIDENTIFIER, serverIP); // Server identifier
		return options;
	}
	
}