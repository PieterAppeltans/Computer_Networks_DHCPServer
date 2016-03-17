import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class DHCPRequest extends DHCPMessage {

	public DHCPRequest(int xid,byte[] ciaddr, byte[] chaddr, Map<DHCPOptions,byte[]> options) {
		super(DHCPOpcode.BOOTREQUEST, // opcode
			  DHCPHtype.ETHERNET, // htype
			  xid, // xid
			  (short) 0, // secs
			  false, // flag
			  ciaddr, // ciaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // yiaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 },
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, // giaddr
			  chaddr,
			  options
			  );
	}
	
	public static Map<DHCPOptions,byte[]> getDefaultOptions(){
		return getDefaultOptions(null, null,0);
	}
	
	public static Map<DHCPOptions,byte[]> getDefaultOptions(byte[] requestedIP, byte[] serverIP,int requestedLeaseTime){
		Map<DHCPOptions,byte[]> options = new HashMap<DHCPOptions,byte[]>();
		options.put(DHCPOptions.DHCPMESSAGETYPE, new byte[] {DHCPMessageType.DHCPREQUEST.getByte()});
		if (requestedIP != null){
			options.put(DHCPOptions.REQUESTEDIPADDRESS,requestedIP);
		}
		if (serverIP != null){
			options.put(DHCPOptions.SERVERIDENTIFIER, serverIP);
		}
		if (requestedLeaseTime != 0){
			ByteBuffer b = ByteBuffer.allocate(4);
			b.putInt(requestedLeaseTime);
			options.put(DHCPOptions.IPADDRESSLEASETIME, b.array());
		}
		return options;
	}
	
}