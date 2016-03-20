import java.util.HashMap;
import java.util.Map;

/**
 * A DHCPDiscover is sent by a client to start up the procedure to get an IP address.
 * 
 * @author	Pieter Appeltans & Hans Cauwenbergh
 */
public class DHCPDiscover extends DHCPMessage {
	
	/**
	 * Constructor for a new DHCPDiscover message.
	 * 
	 * @param 	xid 
	 * 				Transaction ID, a random number chosen by the client, used by the client and server to associate
     *              messages and responses between a client and a server.
	 * @param 	chaddr 
	 * 				Client hardware address (e.g. MAC address).
	 * @param 	options
	 * 				Map containing the options for this message. Must include the option DHCP message type.
	 */
	public DHCPDiscover(int xid,byte[] chaddr, Map<DHCPOption,byte[]> options) {
		super(DHCPOpcode.BOOTREQUEST, 												// opcode
			  DHCPHtype.ETHERNET, 													// htype
			  xid, 																	// xid
			  (short) 0, 															// secs
			  true, 																// flag
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 },	// ciaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, 	// yiaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, 	// siaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, 	// giaddr
			  chaddr,																// chaddr
			  options																// options
			  );
	}
	
	/**
	 * Get the default options with no requested IP address.
	 * 
	 * @return 	The default options for a DHCPDiscover message.
	 */
	public static Map<DHCPOption,byte[]> getDefaultOptions(){
		return DHCPDiscover.getDefaultOptions(null);
	}
	
	/**
	 * Method returning the default options with a requested IP address.
	 * 
	 * @param 	requestedIP
	 * 				The IP the client wants to get from the DHCPServer.
	 * @return 	The default options for a DHCPDiscover message, given the requested IP.
	 */
	public static Map<DHCPOption,byte[]> getDefaultOptions(byte[] requestedIP){
		Map<DHCPOption,byte[]> options = new HashMap<DHCPOption,byte[]>();
		options.put(DHCPOption.DHCPMESSAGETYPE,new byte[]{DHCPMessageType.DHCPDISCOVER.getByte()});
		if (requestedIP != null){
			options.put(DHCPOption.REQUESTEDIPADDRESS,requestedIP);
		}
		return options;
	}
	
	/**
	 * Method returning the class name of this message (DHCPDISCOVER).
	 * 
	 * @return	A string containing the message type.
	 */
	@Override
	public String getClassName() {
		return "DHCPDISCOVER";
	}
	
}