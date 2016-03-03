import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

public class MessageBuilder {
	public MessageBuilder(){}
	public static void main(String arg[]){
		Random rand = new Random();
		byte[] result = new byte[100];
		ByteBuffer buf = ByteBuffer.wrap(result);
		// opcode
		buf.put((byte) 1);
		// htype
		buf.put((byte) 6);
		// hlen
		buf.put((byte) 6);
		//  hops 
		buf.put((byte) 0);
		//xid 
		buf.putInt(rand.nextInt());
		// secs
		buf.put((byte) 0);
		buf.put((byte) 0);
		// flags
		buf.put((byte) 0);
		buf.put((byte) 0);
		System.out.println(Arrays.toString(result));
	}
}
