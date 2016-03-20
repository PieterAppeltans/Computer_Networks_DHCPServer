import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * DHCPAck is send by the DHCPServer in response to a client DHCPRequest.
 * Please note that it's possible to create both ACK (positive response) and NAK (negative response) by adding the correct option.
 * 
 * @author	Pieter Appeltans & Hans Cauwenbergh
 */
public class DHCPAck extends DHCPMessage {
	
	/**
	 * Constructor for a new DHCPAck message.
	 * 
	 * @param 	xid 	
	 * 				Transaction ID, a random number chosen by the client, used by the client and server to associate
     *              messages and responses between a client and a server. In a DHCPAck this xid must be the same
     *              as the one sent by the client in the DHCPRequest.
	 * @param 	ciaddr
	 * 				Client IP address.
	 * @param 	yiaddr
	 * 				The IP address that is (not) acknowledged.
	 * @param 	siaddr
	 * 				IP address of next server to use.
	 * @param 	chaddr
	 * 				Client hardware address (e.g. MAC address). In a DHCPAck this is the same as the chaddr in the client's
	 * 				DHCPRequest.
	 * @param 	options
	 * 				Map containing the options for this message. Both ACK as NAK must include the option DHCP message type.
	 * 				An ACK must also include the option IP address lease time with a given lease time (4 bytes).
	 */
	public DHCPAck(int xid, byte[] ciaddr, byte[] yiaddr, byte[] siaddr, byte[] chaddr, Map<DHCPOption,byte[]> options) {
		super(DHCPOpcode.BOOTREPLY, 												// opcode
			  DHCPHtype.ETHERNET, 													// htype
			  xid, 																	// xid
			  (short) 0, 															// secs
			  false, 																// flag
			  ciaddr,																// ciaddr
			  yiaddr,																// yiaddr
			  siaddr,																// siaddr
			  new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 },	// giaddr
			  chaddr,																// chaddr
			  options																// options
			  );
		this.options = options;
	}
	
	/**
	 * A map containing the options of the DHCPAck/Nak message.
	 */
	private Map<DHCPOption,byte[]> options;
	
	/**
	 * Return the default options for a DHCPAck message with a lease time of one day and server IP 127.0.0.1.
	 * 
	 * @return	A map containing the default options for a DHCPAck message with a lease time of one day and server IP 127.0.0.1.
	 */
	public static Map<DHCPOption,byte[]> getDefaultOptions(){
		return DHCPAck.getDefaultOptions(86400,
					   	  new byte[]{ (byte) 127, (byte) 0, (byte) 0, (byte) 1 },
					   	  true);
	}
	
	/**
	 * Return the default options for a DHCPAck/Nak message with a given lease time and server IP.
	 * 
	 * @param 	leaseTime
	 * 				The amount of seconds the IP is leased to the client.
	 * @param 	serverIp
	 * 				Identifier for the server sending this message. Optional in ACK/NAK messages.
	 * @param 	acknowledged
	 * 				If true the options for an ACK are returned, if false the options for a NAK are returned.
	 * @return	A map containing the default options for a DHCPAck/Nak message with a given lease time and server IP.
	 */
	public static Map<DHCPOption,byte[]> getDefaultOptions(int leaseTime, byte[] serverIp, boolean acknowledged){
		byte[] t = ByteBuffer.allocate(4).putInt(leaseTime).array();
		Map<DHCPOption,byte[]> options = new HashMap<DHCPOption,byte[]>();
		if (acknowledged){
			options.put(DHCPOption.DHCPMESSAGETYPE, new byte[] {DHCPMessageType.DHCPACK.getByte()});
			options.put(DHCPOption.IPADDRESSLEASETIME, t);
		} else {
			options.put(DHCPOption.DHCPMESSAGETYPE, new byte[] {DHCPMessageType.DHCPNAK.getByte()});
		}
		if (serverIp != null){
			options.put(DHCPOption.SERVERIDENTIFIER, serverIp);
		}
		return options;
	}
	
	/**
	 * Return the message type of this message (either DHCPACK or DHCPNAK).
	 * 
	 * @return	A string containing the message type.
	 */
	@Override
	public String getClassName() {
		if (options.get(DHCPOption.DHCPMESSAGETYPE)[0] == DHCPMessageType.DHCPACK.getByte()){
			return "DHCPACK";
		} else {
			return "DHCPNAK";
		}
	}
	
}