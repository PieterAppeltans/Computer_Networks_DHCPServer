import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * A DHCPOffer is sent by a server to offer an IP address to a client.
 * 
 * @author	Pieter Appeltans & Hans Cauwenbergh
 */
public class DHCPOffer extends DHCPMessage {
	
	/**
	 * Constructor for a new DHCPOffer message.
	 * 
	 * @param 	xid 	
	 * 				Transaction ID, a random number chosen by the client, used by the client and server to associate
     *              messages and responses between a client and a server. In a DHCPOffer this xid must be the same
     *              as the one sent by the client in the DHCPDiscover.
	 * @param 	yiaddr
	 * 				The IP address that is offered.
	 * @param 	siaddr
	 * 				IP address of next server to use.
	 * @param 	chaddr
	 * 				Client hardware address (e.g. MAC address). In a DHCPOffer this is the same as the chaddr in the client's
	 * 				DHCPDiscover.
	 * @param 	options
	 * 				Map containing the options for this message. Must include the option DHCP message type, IP address lease time
	 *  			with a given lease time (4 bytes) and the server identifier.
	 */
	public DHCPOffer(int xid, byte[] yiaddr, byte[] siaddr, byte[] chaddr, Map<DHCPOption,byte[]> options) {
		super(DHCPOpcode.BOOTREPLY, 												// opcode
			  DHCPHtype.ETHERNET, 													// htype
			  xid, 																	// xid
			  (short) 0, 															// secs
			  false, 																// flag
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 },	// ciaddr
			  yiaddr,																// yiaddr
			  siaddr,																// siaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, 	// giaddr
			  chaddr,																// chaddr
			  options																// options
			  );
	}
	
	/**
	 * Return the default options for a DHCPOffer message with a lease time of one day and server IP 127.0.0.1.
	 * 
	 * @return	A map containing the default options for a DHCPOffer message with a lease time of one day and server IP 127.0.0.1.
	 */
	public static Map<DHCPOption,byte[]> getDefaultOptions(){
		return getDefaultOptions(86400,
					   	  new byte[]{ (byte) 127, (byte) 0, (byte) 0, (byte) 1 });
	}
	
	/**
	 * Return the default options for a DHCPOffer message with a given lease time and server IP.
	 * 
	 * @param 	leaseTime
	 * 				The amount of seconds the IP is leased to the client.
	 * @param 	serverIp
	 * 				Identifier for the server sending this message. Optional in ACK/NAK messages.
	 * @return	A map containing the default options for a DHCPOffer message with a given lease time and server IP.
	 */
	public static Map<DHCPOption,byte[]> getDefaultOptions(int leaseTime, byte[] serverIp){
		Map<DHCPOption,byte[]> options = new HashMap<DHCPOption,byte[]>();
		options.put(DHCPOption.DHCPMESSAGETYPE, new byte[] {DHCPMessageType.DHCPOFFER.getByte()});
		ByteBuffer b = ByteBuffer.allocate(4);
		b.putInt(leaseTime);
		options.put(DHCPOption.IPADDRESSLEASETIME, b.array());
		options.put(DHCPOption.SERVERIDENTIFIER, serverIp);
		return options;
	}
	
	/**
	 * Method returning the class name of this message (DHCPOFFER).
	 * 
	 * @return	A string containing the message type.
	 */
	@Override
	public String getClassName() {
		return "DHCPOFFER";
	}

}