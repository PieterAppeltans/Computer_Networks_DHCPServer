
public class Test {

	public static void main(String[] args) throws Exception {

		UDPClient client = new UDPClient();
		byte[] bytes = new byte[6 ];
		for (int i = 0; i < 6; i++) {
			bytes[i] = (byte) 0xff; }
		for (int i=1;i<10;i++){
			client.send(bytes);
		}
	}

}
