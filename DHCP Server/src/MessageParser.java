
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class MessageParser {
	public static DHCPMessage parseMessage(byte[]message,int optionLength){
		ByteBuffer buf = ByteBuffer.wrap(message);
		byte opcode = buf.get();
		byte htype = buf.get();
		buf.position(4);
		byte[] xid = new byte[4];
		buf.get(xid);
		short secs = buf.getShort();
		byte[] flagArray = new byte[2];
		buf.get(flagArray);
		boolean flag = false ? Arrays.equals(flagArray,new byte[]{0,0}) : true;
		buf.position(12);
		byte[] ciaddr = new byte[4];
		buf.get(ciaddr);
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
		Map<DHCPOptions,byte[]> parsedOptions  = MessageParser.parseOptions(options);
		byte[] OptionOverload = parsedOptions.get(DHCPOptions.OPTIONOVERLOAD);
		if (OptionOverload != null){
			if ((int)OptionOverload[0]%2 == 1){
				byte[] file = new byte[128];
				buf.position(104);
				buf.get(file);
				parsedOptions.putAll(MessageParser.parseOptions(file,false));
			}
			if ((int)OptionOverload[0]>= 2){
				byte[] sname = new byte[64];
				buf.position(40);
				buf.get(sname);
				parsedOptions.putAll(MessageParser.parseOptions(sname,false));
			}
		}
		System.out.println(DHCPMessage.printByteArrayHexa(options));
		System.out.println(MessageParser.parseOptions(options));
		return new DHCPMessage(DHCPbidirectionalMap.OpcodeMap.getBackward(opcode),DHCPbidirectionalMap.HtypeMap.getBackward(htype),xid,secs,(boolean) flag,ciaddr,yiaddr,siaddr,giaddr,chaddr,parsedOptions);
	}
	public static Map<DHCPOptions,byte[]> parseOptions(byte[] options){
		return MessageParser.parseOptions(options,true);
	}
	public static Map<DHCPOptions,byte[]> parseOptions(byte[] options,boolean magicCookieNeeded){
		ByteBuffer buf = ByteBuffer.wrap(options);
		Map<DHCPOptions,byte[]> result = new HashMap<DHCPOptions, byte[]>();
		if (magicCookieNeeded){
			byte[] magicCookie = new byte[4];
			buf.get(magicCookie);
			if (!Arrays.equals(magicCookie,MessageParser.magicCookie)){
			
			}
		}
		else{
			boolean ended = false;
			while(!ended && buf.position() != options.length  ){
				byte optionCode = buf.get();
				if(optionCode == (byte)0xFF){
					ended = true;
				} 
				else if (optionCode != (byte)0){
					System.out.println(optionCode);
					int length = (int) buf.get() & 0xFF;
					byte[] value = new byte[length];
					buf.get(value);
					result.put(DHCPbidirectionalMap.OptionsMap.getBackward(optionCode), value);
				}
			}
		}
		return result;
	}
	public static byte[] magicCookie = new byte[]{(byte)99,(byte)130,(byte)83,(byte)99};
}
