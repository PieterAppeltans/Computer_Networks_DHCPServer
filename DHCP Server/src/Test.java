import java.net.InetAddress;
import java.nio.ByteBuffer;

public class Test {

	public static void main(String[] args) throws Exception {

		// Test sending bytes to UDPServer
		ByteBuffer buf = ByteBuffer.allocate(8);
		buf.putLong((long) (Math.pow(2, 32)-3));
		buf.position(4);
		int i =buf.getInt();
		System.out.println(buf.toString());
		System.out.println(i);
		System.out.println((long)(i) &0x00000000ffffffffL);
		//DHCPClient client = new DHCPClient(InetAddress.getByName("10.33.14.246"),1234);
		//DHCPClient client = new DHCPClient(InetAddress.getByName("localhost"),9000);
		//client.init();
		//IPAddressKeeper k = new IPAddressKeeper(new byte[]{0,0,0,1},new byte[]{0,0,0,(byte) 0Xff},new byte[]{10,12,0,0},12,12,12);
		//System.out.println(k.generateNewInetAddress(new byte[]{0x12,0x43,0x17,(byte) 0xf3,0x32,(byte) 0xF1}));
	}
	
}
