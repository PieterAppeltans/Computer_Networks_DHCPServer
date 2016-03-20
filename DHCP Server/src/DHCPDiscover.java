import java.util.HashMap;
import java.util.Map;
/**
 * A DHCPDiscover is sent by a client to start up a procedure to get an IP-address.
 * @author Pieter Appeltans & Hans Cauwenbergh
 */
public class DHCPDiscover extends DHCPMessage {
	/**
	 * Constructor for a new DHCPDiscover message.
	 * @param xid 
	 * 				Transaction ID, a random number chosen by the client, used by the client and server to associate
                    	messages and responses between a client and a  server.
	 * @param chaddr 
	 * 				Client hardware address. Eg. MAC address.
	 * @param options
	 * 				Map containing the options for this message. Must include the option DHCPMessageType 
	 */
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
	/**
	 * Get the default options with requestedIP = null
	 * @return The default options
	 */
	public static Map<DHCPOption,byte[]> getDefaultOptions(){
		return DHCPDiscover.getDefaultOptions(null); // no specific IP requested
	}
	/**
	 * Method returning the default options
	 * @param requestedIP The IP the client want to get from the DHCPServer
	 * @return The default options given the requested IP.
	 */
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
	/**
	 * Method returning the class name of this message. (DHCPDISCOVER)
	 */
	@Override
	public String getClassName() {
		return "DHCPDISCOVER";
	}
	
}