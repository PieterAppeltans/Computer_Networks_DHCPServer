import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

public class MessageBuilder {
	public MessageBuilder(){}
	public byte[] generateMessage(DHCPOpcode opcode,DHCPhtype htype,byte[] xid,short secs,
			byte[] ciaddr,byte[] yiaddr,byte[]siaddr,byte[] giaddr){
		if (xid == null){
			// generate random 32-bit identifier
			xid = new byte[4];
			ByteBuffer bufxid = ByteBuffer.wrap(xid);
			Random rand = new Random();
			int t = rand.nextInt();
			bufxid.putInt(t);
		}
		byte[] result = new byte[1024]; // grootte nog aanpassen?
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
		buf.put(xid);
		// secs (2 bytes)
		buf.putShort(secs);
		// flags (2 bytes, enkel eerste bit zetten)
		buf.put((byte) 0);
		buf.put((byte) 0);
		// client IP address (4 bytes)
		buf.put(ciaddr);
		// your IP address (4 bytes)
		buf.put(yiaddr);
		// server IP address (4 bytes)
		buf.put(siaddr);
		// gateway IP address (4 bytes)
		buf.put(giaddr);
		// client hardware address (16 bytes) -> waarom 16 bytes ipv 6?
		for (int i=0;i<16;i++){
			buf.put((byte)255);
		} 
		// server name (64 bytes) and boot filename (128 bytes) -> veroorzaakt overflow?
		for (int i=0;i<(64+128);i++){
			buf.put((byte)0);
		} 
		// Begin option met magic cookie(99.130.83.99) en eindig met end
		buf.put(MessageBuilder.magicCookie);
		
		// print the byte array
		System.out.println("MESSAGE UNSIGNED INT:\t" + printByteArrayInt(result));
		System.out.println("MESSAGE HEXADECIMAL:\t" + printByteArrayHexa(result));
		
		return null;
	}
	public static byte[] magicCookie = {(byte)99,(byte)130,(byte)83,(byte)99};
	
	public static String printByteArrayInt(byte[] byteArray){
		StringBuilder builder = new StringBuilder();
		for (int i=0;i<byteArray.length;i++){
			builder.append(Byte.toUnsignedInt(byteArray[i]));
			builder.append("\t");
		}

		return builder.toString();
	}
	
	public static String printByteArrayHexa(byte[] byteArray){
		String byteString = DatatypeConverter.printHexBinary(byteArray);
		StringBuilder builder = new StringBuilder();
		for (int i = 0;i<byteString.length();i=i+2){
			builder.append(byteString.substring(i, i+2));
			builder.append("\t");
		}
		return builder.toString();
	}
}
