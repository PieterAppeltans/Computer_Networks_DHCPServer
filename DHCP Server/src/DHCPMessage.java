import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Random;
import javax.xml.bind.DatatypeConverter;

/**
 * A DHCP message is sent by a client to a DHCP server or the other way around.
 * 
 * @author	Pieter Appeltans & Hans Cauwenbergh
 */
public class DHCPMessage {
	
	/**
	 * Constructor for a new DHCP message.
	 * 
	 * @param 	opcode
	 * 				Code indicating the message type. The message is either a request or a reply.
	 * @param 	htype
	 * 				Hardware address type.
	 * @param 	xid
	 * 				Transaction ID, a random number chosen by the client, used by the client and server to associate
     *              messages and responses between a client and a server.
	 * @param 	secs
	 * 				Filled in by client, seconds elapsed since client began address acquisition or renewal process.
	 * @param 	flag
	 * 				Boolean indicating whether the message should be broadcast.
	 * @param 	ciaddr
	 * 				Client IP address.
	 * @param	yiaddr
	 * 				The IP address that's leased by the server to a client.
	 * @param 	siaddr
	 * 				IP address of next server to use in bootstrap.
	 * @param 	giaddr
	 * 				Relay agent IP address.
	 * @param 	chaddr
	 * 				Client hardware address (e.g. MAC address).
	 * @param 	options
	 * 				Map containing the options for this message.
	 */
	public DHCPMessage(DHCPOpcode opcode,DHCPHtype htype,int xid,short secs, boolean flag,
			byte[] ciaddr,byte[] yiaddr,byte[]siaddr,byte[] giaddr, byte[] chaddr, Map<DHCPOption,byte[]> options){
		this.opcode = opcode;
		this.htype = htype;
		this.xid = xid;
		this.secs = secs;
		this.flag = flag;
		this.ciaddr = ciaddr;
		this.yiaddr = yiaddr;
		this.siaddr = siaddr;
		this.giaddr = giaddr;
		this.chaddr = chaddr;
		this.options = options;
	}
	
	/**
	 * Code indicating the message type. The message is either a request or a reply.
	 */
	private DHCPOpcode opcode;
	
	/**
	 * Hardware address type.
	 */
	private DHCPHtype htype;
	
	/**
	 * Transaction ID, a random number chosen by the client, used by the client and server to associate
     * messages and responses between a client and a server.
	 */
	private int xid;
	
	/**
	 * Filled in by client, seconds elapsed since client began address acquisition or renewal process.
	 */
	private short secs;
	
	/**
	 * Boolean indicating whether the message should be broadcast.
	 */
	private boolean flag;
	
	/**
	 * Client IP address.
	 */
	private byte[] ciaddr;
	
	/**
	 * The IP address that's leased by the server to a client.
	 */
	private byte[] yiaddr;
	
	/**
	 * IP address of next server to use in bootstrap.
	 */
	private byte[] siaddr;
	
	/**
	 * Relay agent IP address.
	 */
	private byte[] giaddr;
	
	/**
	 * Client hardware address (e.g. MAC address).
	 */
	private byte[] chaddr;
	
	/**
	 * Map containing the options for this message.
	 */
	private Map<DHCPOption,byte[]> options;
	
	/**
	 * Return the transaction ID of the message.
	 * 
	 * @return	An integer representing the transaction ID of the message.
	 */
	public int getXid(){
		return this.xid;
	}
	
	/**
	 * Return the IP address of the next server to use in bootstrap.
	 * 
	 * @return	A byte array representing the IP address of the next server to use in bootstrap.
	 */
	public byte[] getSiaddr(){
		return this.siaddr;
	}
	
	/**
	 * Return the client hardware address.
	 * 
	 * @return	A byte array representing the client hardware address.
	 */
	public byte[] getChaddr(){
		return this.chaddr;
	}
	
	/**
	 * Return the leased IP address.
	 * 
	 * @return	A byte array representing the leased IP address.
	 */
	public byte[] getYiaddr(){
		return this.yiaddr;
	}
	
	/**
	 * Return the client's IP address.
	 * 
	 * @return	A byte array representing the client's IP address.
	 */
	public byte[] getCiaddr() {
		return this.ciaddr;
	}
	
	/**
	 * Return the options of the message.
	 * 
	 * @return	A map containing the DHCP options of the message.
	 */
	public Map<DHCPOption,byte[]> getOptionsMap(){
		return options;
	}
	
	/**
	 * Generate the DHCP message as an array of bytes.
	 * 
	 * @return	An array of bytes representing the DHCP message.
	 */
	public byte[] generateMessage(){
		if (this.xid == 0){
			// generate random 32-bit identifier
			Random rand = new Random();
			this.xid = rand.nextInt();
		}
		byte[] result = new byte[576]; // TODO: 576 as max message?
		ByteBuffer buf = ByteBuffer.wrap(result);
		// opcode (1 byte)
		buf.put(opcode.getByte());
		// htype (1 byte)
		buf.put(htype.getByte());
		// hlen (1 byte)
		buf.put((byte) 6);
		// hops (1 byte)
		buf.put((byte) 0);
		// xid (4 bytes)
		buf.putInt(xid);
		// secs (2 bytes)
		buf.putShort(secs);
		// flags (2 bytes, only set first bit)
		if (flag){
			buf.put((byte) 0x80);
		} else {
			buf.put((byte) 0);
		}
		buf.put((byte) 0);
		// client IP address (4 bytes)
		buf.put(ciaddr);
		// your IP address (4 bytes)
		buf.put(yiaddr);
		// server IP address (4 bytes)
		buf.put(siaddr);
		// gateway IP address (4 bytes)
		buf.put(giaddr);
		// client hardware address (16 bytes)
		buf.put(chaddr);
		// server name (64 bytes) and boot filename (128 bytes)
		for (int i=0;i<(64+128);i++){
			buf.put((byte)0);
		} 
		// Begin option with magic cookie (99.130.83.99)
		buf.put(DHCPMessage.magicCookie);
		// options
		for (Map.Entry<DHCPOption, byte[]> entry : options.entrySet()) {
		    DHCPOption key = entry.getKey();
		    byte[] value = entry.getValue();
		    buf.put(key.getByte());
		    buf.put((byte) value.length);
		    buf.put(value);
		}
		// End option with 255
		buf.put(new byte[]{ (byte) 0xFF });		
		return result;
	}
	
	/**
	 * Print the DHCP message in a clear way.
	 * 
	 * @param 	clientToServer
	 * 				A boolean representing if the message was sent from a client to a server or the other way around.
	 */
	public void print(boolean clientToServer){
		if (clientToServer){
			System.out.println("----------------------------------------- CLIENT TO SERVER -----------------------------------------");
		} else {
			System.out.println("----------------------------------------- SERVER TO CLIENT -----------------------------------------");
		}
		if (this.getClassName() != ""){
			System.out.println(this.getClassName());
		}
		System.out.println("opcode:\t " + opcode);
		System.out.println("htype:\t " + htype);
		System.out.println("xid:\t " + xid);
		System.out.println("secs:\t " + secs);
		System.out.println("flag:\t " + flag);
		System.out.println("ciaddr:\t " + DHCPMessage.printByteArrayHexa(ciaddr));
		System.out.println("yiaddr:\t " + DHCPMessage.printByteArrayHexa(yiaddr));
		System.out.println("siaddr:\t " + DHCPMessage.printByteArrayHexa(siaddr));
		System.out.println("giaddr:\t " + DHCPMessage.printByteArrayHexa(giaddr));
		System.out.println("chaddr:\t " + DHCPMessage.printByteArrayHexa(chaddr));
		System.out.println("options:\t ");
		for (Map.Entry<DHCPOption, byte[]> entry : options.entrySet()) {
		    DHCPOption key = entry.getKey();
		    byte[] value = entry.getValue();
		    System.out.println("\t" + key + "\t" + DHCPMessage.printByteArrayHexa(value));
		}
		System.out.println("----------------------------------------------------------------------------------------------------");
	}
	
	/**
	 * A byte array (99.130.83.99) used to mark the beginning of the option field.
	 */
	public static byte[] magicCookie = {(byte)99,(byte)130,(byte)83,(byte)99};
	
	/**
	 * Transform a byte array to a string containing a sequence of integers.
	 * 
	 * @param 	byteArray
	 * 				The byte array to print.
	 * @return	A string containing the sequence of integers corresponding to the given bytes in the array.
	 */
	public static String printByteArrayInt(byte[] byteArray){
		StringBuilder builder = new StringBuilder();
		for (int i=0;i<byteArray.length;i++){
			builder.append(Byte.toUnsignedInt(byteArray[i]));
			builder.append("\t");
		}
		return builder.toString();
	}
	
	/**
	 * Transform a byte array to a string containing a sequence of hexadecimal values.
	 * 
	 * @param 	byteArray
	 * 				The byte array to print.
	 * @return	A string containing the sequence of hexadecimal values corresponding to the given bytes in the array.
	 */
	public static String printByteArrayHexa(byte[] byteArray){
		String byteString = DatatypeConverter.printHexBinary(byteArray);
		StringBuilder builder = new StringBuilder();
		for (int i = 0;i<byteString.length();i=i+2){
			builder.append(byteString.substring(i, i+2));
			builder.append("\t");
		}
		return builder.toString();
	}
	
	/**
	 * Method returning the class name of this message.
	 * 
	 * @return	A string containing the message type.
	 */
	public String getClassName() {
		byte messageType = options.get(DHCPOption.DHCPMESSAGETYPE)[0];
		System.out.println(DHCPbidirectionalMap.MessageTypeMap.getBackward(messageType));
		return "";
	}
	
}