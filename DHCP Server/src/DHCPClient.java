import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Map;
import java.util.Random;



public class DHCPClient {
	public DHCPClient(InetAddress DHCPServerAdrress,int port){
		udpclient = new UDPClient(DHCPServerAdrress,port);
	}
	private UDPClient udpclient ;
	private DHCPClientStates state = DHCPClientStates.INIT;
	public void init() {
		int timeout = 0;
		while (state == DHCPClientStates.INIT){
			if (timeout != 0){
				Random rand = new Random();
				try{
				Thread.sleep(timeout*1000+(long) (rand.nextFloat()*2000-1000));
				}
				catch(Exception e){}
			}
			DHCPMessage message = new DHCPDiscover(this.getChaddr(),DHCPDiscover.getDefaultOptions());
			byte[] returnMessage = null;
			try{
				returnMessage = udpclient.send(message.generateMessage());
			}	
			catch (Exception e){
			}
			if (returnMessage != null){
				DHCPMessage parsedMessage = MessageParser.parseMessage(returnMessage,312);
				Map<DHCPOptions, byte[]> parsedOptions = parsedMessage.getOptionsMap();
				byte[] messageType = parsedOptions.get(DHCPOptions.DHCPMESSAGETYPE);
				if (DHCPbidirectionalMap.MessageTypeMap.getBackward(messageType[0])== DHCPMessageType.DHCPOFFER){
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
