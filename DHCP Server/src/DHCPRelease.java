import java.util.HashMap;
import java.util.Map;

/**
 * A DHCPRelease is sent by a client to the server when he wishes to release his leased IP address.
 * 
 * @author	Pieter Appeltans & Hans Cauwenbergh
 */
public class DHCPRelease extends DHCPMessage{

	/**
	 * Constructor for a new DHCPRelease message.
	 * 
	 * @param 	xid 	
	 * 				Transaction ID, a random number chosen by the client, used by the client and server to associate
     *              messages and responses between a client and a server. In a DHCPRelease this xid must be the same
     *              as the messages sent prior by the client to the server.
	 * @param 	ciaddr
	 * 				Client IP address.
	 * @param 	chaddr
	 * 				Client hardware address (e.g. MAC address). In a DHCPRelease this is the same as the chaddr in the client's
	 * 				prior messages to the server.
	 * @param 	options
	 * 				Map containing the options for this message. Must include the option DHCP message type.
	 */
	public DHCPRelease(int xid, byte[] ciaddr, byte[] chaddr, Map<DHCPOption,byte[]> options) {
		super(DHCPOpcode.BOOTREQUEST, 												// opcode
			  DHCPHtype.ETHERNET, 													// htype
			  xid, 																	// xid
			  (short) 0, 															// secs
			  false, 																// flag
			  ciaddr,																// ciaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, 	// yiaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, 	// siaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, 	// giaddr
			  chaddr,																// chaddr
			  options																// options
			  );
	}
	
	/**
	 * Return the default options for a DHCPRelease message.
	 * 
	 * @return	A map containing the default options for a DHCPRelease message.
	 */
	public static Map<DHCPOption,byte[]> getDefaultOptions(){
		Map<DHCPOption,byte[]> options = new HashMap<DHCPOption,byte[]>();
		options.put(DHCPOption.DHCPMESSAGETYPE,new byte[]{DHCPMessageType.DHCPRELEASE.getByte()});
		return options;
	}
	
	/**
	 * Method returning the class name of this message (DHCPRELEASE).
	 * 
	 * @return	A string containing the message type.
	 */
	@Override
	public String getClassName() {
		return "DHCPRELEASE";
	}
	
}