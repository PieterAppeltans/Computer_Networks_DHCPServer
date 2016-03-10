import java.net.InetAddress;
import java.net.NetworkInterface;



public class DHCPClient {
	public DHCPClient(InetAddress DHCPServerAdrress,int port){
		udpclient = new UDPClient(DHCPServerAdrress,port);
	}
	private UDPClient udpclient ;
	private DHCPClientStates state = DHCPClientStates.INIT;
	public void init() throws Exception{
		DHCPMessage message = new DHCPDiscover(this.getChaddr(),DHCPDiscover.getOptions());
		byte[] returnMessage = udpclient.send(message.generateMessage());
		MessageParser.parseMessage(returnMessage,312);
		
	}
	public byte[] getChaddr(){
		try{
			InetAddress ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			byte[] chaddr = new byte[16];
			byte[] empty = new byte[] { (byte) 0x00, (byte) 0x00,
				 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
			System.arraycopy(mac, 0, chaddr, 0, 6);
			System.arraycopy(empty, 0, chaddr, 6, 10);
			return chaddr;
		}
		catch (Exception e){
			byte[] mac = new byte[] {(byte)0xa1,(byte)0xf3,(byte)0xe2,(byte)0x43,(byte)0x13,(byte)0X78};
			byte[] chaddr = new byte[16];
			byte[] empty = new byte[] { (byte) 0x00, (byte) 0x00,
				 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
			System.arraycopy(mac, 0, chaddr, 0, 6);
			System.arraycopy(empty, 0, chaddr, 6, 10);
			return chaddr;
		}
	}
}
