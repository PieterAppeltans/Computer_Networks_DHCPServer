import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Map;
import java.util.Random;

public class DHCPClient {
	
	public DHCPClient(InetAddress DHCPServerAdrress,int port){
		udpclient = new UDPClient(DHCPServerAdrress,port);
		state = DHCPClientStates.INIT;
	}
	
	private UDPClient udpclient;
	private DHCPClientStates state;
	
	public void init() {
		int timeout = 5; // temporarily set to avoid rapid message iteration
		while (state == DHCPClientStates.INIT){
			if (timeout != 0){
				Random rand = new Random();
				try{
				Thread.sleep(timeout*1000+(long) (rand.nextFloat()*2000-1000));
				}
				catch(Exception e){}
			}
			DHCPMessage message = new DHCPDiscover(this.getChaddr(),DHCPDiscover.getDefaultOptions()); // xid needs to remain the same?
			byte[] returnMessage = null;
			try{
				returnMessage = udpclient.send(message.generateMessage());
			}	
			catch (Exception e){
			}
			if (returnMessage != null){
				System.out.println("PARSED RECEIVED MESSAGE:");
				DHCPMessage parsedMessage = MessageParser.parseMessage(returnMessage,312); // optionlength unknown?
				parsedMessage.print();
				Map<DHCPOptions, byte[]> parsedOptions = parsedMessage.getOptionsMap();
				byte[] messageType = parsedOptions.get(DHCPOptions.DHCPMESSAGETYPE);
				if (DHCPbidirectionalMap.MessageTypeMap.getBackward(messageType[0]) == DHCPMessageType.DHCPOFFER){
					//TODO Handel offer -> and go to Requesting state
				}
			}
		}
	}
	
	public void run(){
		while (true){
			if (this.state == DHCPClientStates.INIT){
				this.init();
			}
		}
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
			byte[] mac = new byte[] {(byte)0xa1,(byte)0xf3,(byte)0xe2,(byte)0x43,(byte)0x13,(byte)0X78}; // random fixed mac address
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