import java.nio.ByteBuffer;
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
	
	public static byte[] getOptions(){
		byte[] options = new byte[100]; // grootte nog aanpassen?
		ByteBuffer optionBuffer = ByteBuffer.wrap(options);
		optionBuffer.put(new byte[]{ (byte) DHCPOptions.DHCPMESSAGETYPE.getByte() }); // DHCP Message type
		optionBuffer.put(new byte[]{ (byte) 1 }); // length
		optionBuffer.put(new byte[]{ (byte) 7 }); // DHCP Release
		return options;
	}
	
}