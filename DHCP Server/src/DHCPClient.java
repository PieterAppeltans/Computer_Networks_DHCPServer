import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.chrono.MinguoChronology;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Map;
import java.util.Random;

import org.omg.CORBA.TIMEOUT;

public class DHCPClient {
	
	public DHCPClient(InetAddress DHCPServerAdrress,int port){
		udpclient = new UDPClient(DHCPServerAdrress,port);
		state = DHCPClientStates.INIT;
	}
	
	private UDPClient udpclient;
	private DHCPClientStates state;
	private byte[] serverIdentifier;
	private byte[] offeredAddress;
	private byte[] receivedAddress;
	private int xid;

	private LocalDateTime renewalTime;
	private LocalDateTime rebindingTime;
	private LocalDateTime leaseTime;
	

	public void init() {
		int timeout = 0; 
		Random rand = new Random();
		while (state == DHCPClientStates.INIT){
			if (timeout != 0){				
				try{
					Thread.sleep(timeout*1000+(long) (rand.nextFloat()*2000-1000)); // why random fluctuation (+- 1s) ?
				}
				catch(Exception e){
					
				}
			}
			else{
				try {
					Thread.sleep((long)(rand.nextFloat()*9000+1000)); //  The client SHOULD wait a random time between one and ten seconds to desynchronize the use of DHCP at startup.
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
			this.xid = rand.nextInt();
			DHCPMessage message = new DHCPDiscover(xid,this.getChaddr(),DHCPDiscover.getDefaultOptions());
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
				if (DHCPbidirectionalMap.MessageTypeMap.getBackward(messageType[0]) == DHCPMessageType.DHCPOFFER 
						&& parsedMessage.getXid() == this.xid ){
					this.serverIdentifier = parsedMessage.getSiaddr();
					this.offeredAddress = parsedMessage.getYiaddr();
					this.state = DHCPClientStates.REQUESTING;
					System.out.println("TO REQUESTING STATE");	
				}
				else{
					if (timeout == 0){
						timeout = 4; // delay before the first retransmission SHOULD be 4 seconds
					}
					else{
						timeout = Math.min(timeout*2, 64); // The retransmission delay SHOULD be doubled with subsequent retransmissions up to a maximum of 64 seconds
					}
				}
			}
			else{
				if (timeout == 0){
					timeout = 4; // delay before the first retransmission SHOULD be 4 seconds
				}
				else{
					timeout = Math.min(timeout*2, 64); // The retransmission delay SHOULD be doubled with subsequent retransmissions up to a maximum of 64 seconds
				}
			}
		}
	}
	
	public void request(){
		int timeout = 0; 
		Random rand = new Random();
		while (this.state == DHCPClientStates.REQUESTING){
			if (timeout != 0){				
				try{
				Thread.sleep(timeout*1000+(long) (rand.nextFloat()*2000-1000));
				}
				catch(Exception e){}
			}
			DHCPMessage message = new DHCPRequest(this.xid,new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 },this.getChaddr(),DHCPRequest.getDefaultOptions(this.offeredAddress,this.serverIdentifier));
			byte[] returnMessage = null;
			LocalDateTime startTime = LocalDateTime.now();
			try{
				returnMessage = udpclient.send(message.generateMessage());
			}	
			catch (Exception e){
			}
			if (returnMessage != null){
				DHCPMessage parsedMessage = MessageParser.parseMessage(returnMessage,312); // optionlength unknown?
				System.out.println("PARSED RECEIVED MESSAGE:");
				parsedMessage.print();
				Map<DHCPOptions, byte[]> parsedOptions = parsedMessage.getOptionsMap();
				byte[] messageType = parsedOptions.get(DHCPOptions.DHCPMESSAGETYPE);
				if (DHCPbidirectionalMap.MessageTypeMap.getBackward(messageType[0]) == DHCPMessageType.DHCPACK
						&& parsedMessage.getXid() == this.xid ){
					byte[] t1= parsedOptions.get(DHCPOptions.RENEWALTIME);
					ByteBuffer buf1 = ByteBuffer.wrap(t1);
					this.renewalTime = startTime.plusSeconds(toUnsigned(buf1.getInt()));
					byte[] t2 = parsedOptions.get(DHCPOptions.REBINDINGTIME);
					ByteBuffer buf2 = ByteBuffer.wrap(t2);

					this.rebindingTime = startTime.plusSeconds(toUnsigned(buf2.getInt()));
					byte[] t3 = parsedOptions.get(DHCPOptions.IPADDRESSLEASETIME);
					ByteBuffer buf3 = ByteBuffer.wrap(t3);
					this.leaseTime = startTime.plusSeconds(toUnsigned(buf3.getInt()));
					this.state = DHCPClientStates.BOUND;
					this.receivedAddress = parsedMessage.getYiaddr();
					System.out.println("TO BOUND STATE");
				}
				else if (DHCPbidirectionalMap.MessageTypeMap.getBackward(messageType[0]) == DHCPMessageType.DHCPNAK
						&& parsedMessage.getXid() == this.xid ){
					ErrorPrinter.print("Returning to INIT state due to DHCPNAK.");
					this.state = DHCPClientStates.INIT;
				}
				else{ // if the client receives neither a DHCPACK or a DHCPNAK message.  The client retransmits the DHCPREQUEST
					if (timeout == 0){
						timeout = 4;
					}
					else if (timeout >= 64){
						/*If the client
					     receives neither a DHCPACK or a DHCPNAK message after employing the
					     retransmission algorithm, the client reverts to INIT state and
					     restarts the initialization process.*/
						this.state = DHCPClientStates.INIT;   
						// The client SHOULD notify the user that the initialization process has failed and is restarting.
						ErrorPrinter.print("Returning to INIT state due to not receiving an answer.");
					}
					else{
						timeout = timeout*2;
					}
				}
			}
			else{
				if (timeout == 0){
					timeout = 4;
				}
				else if (timeout >= 64){
					/*If the client
				     receives neither a DHCPACK or a DHCPNAK message after employing the
				     retransmission algorithm, the client reverts to INIT state and
				     restarts the initialization process.*/
					this.state = DHCPClientStates.INIT;
					// The client SHOULD notify the user that the initialization process has failed and is restarting.
					ErrorPrinter.print("Returning to INIT state due to not receiving an answer.");
				}
				else{
					timeout = timeout*2;
				}
			}
		}
	}
	
	public void release(){
		if (this.state == DHCPClientStates.BOUND){
			DHCPMessage message = new DHCPRelease(this.xid,this.receivedAddress,this.getChaddr(),DHCPRelease.getOptions());
			byte[] returnMessage = null;
			try{
				returnMessage = udpclient.send(message.generateMessage()); // Does the server return anything at all?
			}	
			catch (Exception e){
			}
		}
	}
	
	public void renew(){
		int timeout = 0;
		System.out.println("TO RENEWING STATE");
		this.state = DHCPClientStates.RENEWING;
		while (this.state == DHCPClientStates.RENEWING){
			if (timeout != 0){				
				try{
					Thread.sleep(timeout*1000);
				}
				catch(Exception e){}
			}
			// This message will be unicast.
			DHCPMessage message = new DHCPRequest(this.xid,this.receivedAddress,this.getChaddr(),DHCPRequest.getDefaultOptions());
			byte[] returnMessage = null;
			LocalDateTime startTime = LocalDateTime.now();
			try{
				returnMessage = udpclient.send(message.generateMessage());
			}	
			catch (Exception e){
			}
			if (returnMessage != null){
				
				// The server may choose not to extend the lease (as a policy decision by
			    // the network administrator), but should return a DHCPACK message regardless. -> 0 when not extended?
				
				DHCPMessage parsedMessage = MessageParser.parseMessage(returnMessage,312); // optionlength unknown?
				System.out.println("PARSED RECEIVED MESSAGE:");
				parsedMessage.print();
				Map<DHCPOptions, byte[]> parsedOptions = parsedMessage.getOptionsMap();
				byte[] messageType = parsedOptions.get(DHCPOptions.DHCPMESSAGETYPE);
				if (DHCPbidirectionalMap.MessageTypeMap.getBackward(messageType[0]) == DHCPMessageType.DHCPACK
						&& parsedMessage.getXid() == this.xid ){
					byte[] t1= parsedOptions.get(DHCPOptions.RENEWALTIME);
					ByteBuffer buf1 = ByteBuffer.wrap(t1);
					this.renewalTime = startTime.plusSeconds(toUnsigned(buf1.getInt()));
					byte[] t2 = parsedOptions.get(DHCPOptions.REBINDINGTIME);
					ByteBuffer buf2 = ByteBuffer.wrap(t2);
					this.rebindingTime = startTime.plusSeconds(toUnsigned(buf2.getInt()));
					byte[] t3 = parsedOptions.get(DHCPOptions.IPADDRESSLEASETIME);
					ByteBuffer buf3 = ByteBuffer.wrap(t3);
					this.leaseTime = startTime.plusSeconds(toUnsigned(buf3.getInt()));
					this.state = DHCPClientStates.BOUND;
					System.out.println("TO BOUND STATE");
				}
			} else {
				// If the client receives no response to its DHCPREQUEST message, the client SHOULD wait one-half
				// of the remaining time until T2 (in RENEWING state), down to a minimum of
				// 60 seconds, before retransmitting the DHCPREQUEST message.
				timeout = Math.max( 60, (int) (0.5*LocalDateTime.now().until(this.rebindingTime, ChronoUnit.SECONDS)) );
				// If no DHCPACK arrives before time T2, the client moves to REBINDING
				// state and sends (via broadcast) a DHCPREQUEST message to extend its
				// lease.
				if (this.rebindingTime.isBefore(LocalDateTime.now())){
					this.rebind();
				}
			}
		}
	}
	
	public void rebind(){
		int timeout = 0;
		System.out.println("TO REBINDING STATE");
		this.state = DHCPClientStates.REBINDING;
		while (this.state == DHCPClientStates.REBINDING){
			if (timeout != 0){				
				try{
					Thread.sleep(timeout*1000);
				}
				catch(Exception e){}
			}
			// This message MUST be broadcast to the 0xffffffff IP broadcast address.
			DHCPMessage message = new DHCPRequest(this.xid,this.receivedAddress,this.getChaddr(),DHCPRequest.getDefaultOptions());
			byte[] returnMessage = null;
			LocalDateTime startTime = LocalDateTime.now();
			try{
				returnMessage = udpclient.send(message.generateMessage());
			}	
			catch (Exception e){
			}
			if (returnMessage != null){			
				DHCPMessage parsedMessage = MessageParser.parseMessage(returnMessage,312); // optionlength unknown?
				System.out.println("PARSED RECEIVED MESSAGE:");
				parsedMessage.print();
				Map<DHCPOptions, byte[]> parsedOptions = parsedMessage.getOptionsMap();
				byte[] messageType = parsedOptions.get(DHCPOptions.DHCPMESSAGETYPE);
				if (DHCPbidirectionalMap.MessageTypeMap.getBackward(messageType[0]) == DHCPMessageType.DHCPACK
						&& parsedMessage.getXid() == this.xid ){
					byte[] t1= parsedOptions.get(DHCPOptions.RENEWALTIME);
					ByteBuffer buf1 = ByteBuffer.wrap(t1);
					this.renewalTime = startTime.plusSeconds(toUnsigned(buf1.getInt()));
					byte[] t2 = parsedOptions.get(DHCPOptions.REBINDINGTIME);
					ByteBuffer buf2 = ByteBuffer.wrap(t2);
					this.rebindingTime = startTime.plusSeconds(toUnsigned(buf2.getInt()));
					byte[] t3 = parsedOptions.get(DHCPOptions.IPADDRESSLEASETIME);
					ByteBuffer buf3 = ByteBuffer.wrap(t3);
					this.leaseTime = startTime.plusSeconds(toUnsigned(buf3.getInt()));
					this.state = DHCPClientStates.BOUND;
					System.out.println("TO BOUND STATE");
				}
			} else {
				// If the client receives no response to its DHCPREQUEST message, the client SHOULD wait one-half of
				// the remaining lease time (in REBINDING state), down to a minimum of
				// 60 seconds, before retransmitting the DHCPREQUEST message.
				timeout = Math.max( 60, (int) (0.5*LocalDateTime.now().until(this.leaseTime, ChronoUnit.SECONDS)) );
				// If the lease expires before the client receives a DHCPACK, the client
				// moves to INIT state.
				if (this.leaseTime.isBefore(LocalDateTime.now())){
					this.state = DHCPClientStates.INIT;
				}
			}
		}
	}
	private long toUnsigned(int signed){
		if (signed<0){
			return (long) (2**31 - Math.abs(signed)) + (long) Math.pow(2,31);
		}
		else{
			return signed;
		}
	}
	
	public void run(){
		while (true){
			if (this.state == DHCPClientStates.INIT){ // already gets checked within init()?
				this.init();
				this.request();
			} else {
				if (this.renewalTime.isBefore(LocalDateTime.now())){
					this.renew();
				}		
			}
			else if (this.state == DHCPClientStates.REQUESTING){
				this.request();
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