import java.nio.ByteBuffer;


public class MessageParser {
	public static DHCPMessage parseMessage(byte[]message,int optionLength){
		ByteBuffer buf = ByteBuffer.wrap(message);
		byte opcode = buf.get();
		buf.position(4);
		byte[] xid = new byte[4];
		buf.get(xid);
		buf.position(12);
		byte[] ciaddr = new byte[4];
		buf.get(ciaddr);
		System.out.println(buf.position());
		byte[] yiaddr = new byte[4];
		buf.get(yiaddr);
		byte[] siaddr = new byte[4];
		buf.get(siaddr);
		byte[] giaddr = new byte[4];
		buf.get(giaddr);
		System.out.println(buf.position());
		byte[] chaddr = new byte[16];
		buf.get(chaddr);
		System.out.println(DHCPMessage.printByteArrayHexa(chaddr));
		buf.position(buf.position()+64+128);
		byte[] options = new byte[optionLength];
		buf.get(options);
		System.out.println(DHCPMessage.printByteArrayHexa(options));
		return null;
	}
}
