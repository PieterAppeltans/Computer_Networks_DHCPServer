import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * DHCPRequest is send by the client in response to a server DHCPOffer.
 * 
 * @author	Pieter Appeltans & Hans Cauwenbergh
 */
public class DHCPRequest extends DHCPMessage {

	/**
	 * Constructor for a new DHCPRequest message.
	 * 
	 * @param 	xid 	
	 * 				Transaction ID, a random number chosen by the client, used by the client and server to associate
     *              messages and responses between a client and a server. In a DHCPRequest this xid must be the same
     *              as the one sent by the server in the DHCPOffer.
	 * @param 	ciaddr
	 * 				Client IP address, must be {0,0,0,0} when no IP assigned. The clients IP in bound, renew or
	 * 				rebound state.
	 * @param 	chaddr
	 * 				Client hardware address (e.g. MAC address). In a DHCPRequest this is the same as the chaddr in the client's
	 * 				prior messages to the server.
	 * @param 	options
	 * 				Map containing the options for this message. Must include the option DHCP message type. When selecting,
	 * 				the options must also include the requested IP address and the server identifier in contrary to bound,
	 *  			renew or rebound state. It may include a suggested lease time.
	 */
	public DHCPRequest(int xid,byte[] ciaddr, byte[] chaddr, Map<DHCPOption,byte[]> options) {
		super(DHCPOpcode.BOOTREQUEST, 												// opcode
			  DHCPHtype.ETHERNET, 													// htype
			  xid, 																	// xid
			  (short) 0, 															// secs
			  false, 																// flag
			  ciaddr, 																// ciaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, 	// yiaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 },	// siaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, 	// giaddr
			  chaddr,																// chaddr
			  options																// options
			  );
	}
	
	/**
	 * Return the default options for a DHCPRequest message with no requested IP, no server IP and no requested lease time.
	 * 
	 * @return	A map containing the default options for a DHCPRequest message with no requested IP, no server IP and no requested lease time.
	 */
	public static Map<DHCPOption,byte[]> getDefaultOptions(){
		return getDefaultOptions(null, null,0);
	}
	
	/**
	 * Return the default options for a DHCPRequest message with a requested IP, server IP and a requested lease time.
	 * 
	 * @param 	requestedIp
	 * 				The requested IP address. Needed in selecting state, not in bound,renew or rebound state.
	 * @param 	serverIp
	 * 				Identifier for the server to send the message to. Needed in selecting state, not in bound,renew or rebound state.
	 * @param 	requestedLeaseTime
	 * 				The requested amount of seconds the IP is leased to the client. May be included
	 * @return	A map containing the default options for a DHCPRequest message with a requested IP, server IP and a requested lease time.
	 */
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
	
	/**
	 * Method returning the class name of this message (DHCPREQUEST).
	 * 
	 * @return	A string containing the message type.
	 */
	@Override
	public String getClassName() {
		return "DHCPREQUEST";
	}
	
}