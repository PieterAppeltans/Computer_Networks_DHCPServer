import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class DHCPRequest extends DHCPMessage {

	public DHCPRequest(int xid,byte[] ciaddr, byte[] chaddr, Map<DHCPOption,byte[]> options) {
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
	
	public static Map<DHCPOption,byte[]> getDefaultOptions(){
		return getDefaultOptions(null, null,0);
	}
	
	public static Map<DHCPOption,byte[]> getDefaultOptions(byte[] requestedIP, byte[] serverIP,int requestedLeaseTime){
		Map<DHCPOption,byte[]> options = new HashMap<DHCPOption,byte[]>();
		options.put(DHCPOption.DHCPMESSAGETYPE, new byte[] {DHCPMessageType.DHCPREQUEST.getByte()});
		if (requestedIP != null){
			options.put(DHCPOption.REQUESTEDIPADDRESS,requestedIP);
		}
		if (serverIP != null){
			options.put(DHCPOption.SERVERIDENTIFIER, serverIP);
		}
		if (requestedLeaseTime != 0){
			ByteBuffer b = ByteBuffer.allocate(4);
			b.putInt(requestedLeaseTime);
			options.put(DHCPOption.IPADDRESSLEASETIME, b.array());
		}
		return options;
	}
	
	@Override
	public String getClassName() {
		return "DHCPREQUEST";
	}
	
}