
public class DHCPbidirectionalMap {
	public static BidirectionalMap<DHCPOpcode, Byte> OpcodeMap = new BidirectionalMap();
	
	static{
		OpcodeMap.add(DHCPOpcode.BOOTREQUEST,DHCPOpcode.BOOTREQUEST.getByte());
		OpcodeMap.add(DHCPOpcode.BOOTREPLY,DHCPOpcode.BOOTREPLY.getByte());
	}
	public static BidirectionalMap<DHCPHtype, Comparable> HtypeMap = new BidirectionalMap();
	static{
		HtypeMap.add(DHCPHtype.ETHERNET,DHCPHtype.ETHERNET.getByte());
		HtypeMap.add(DHCPHtype.IEE802,DHCPHtype.IEE802);
	}
	public static BidirectionalMap<DHCPOptions, Byte> OptionsMap = new BidirectionalMap();
	static{
		OptionsMap.add(DHCPOptions.DHCPMESSAGETYPE, DHCPOptions.DHCPMESSAGETYPE.getByte());
		OptionsMap.add(DHCPOptions.IPADDRESSLEASETIME, DHCPOptions.IPADDRESSLEASETIME.getByte());
		OptionsMap.add(DHCPOptions.REQUESTEDIPADDRESS,DHCPOptions.REQUESTEDIPADDRESS.getByte());
		OptionsMap.add(DHCPOptions.REBINGINGTIME, DHCPOptions.REBINGINGTIME.getByte());
		OptionsMap.add(DHCPOptions.RENEWALTIME, DHCPOptions.RENEWALTIME.getByte());
		OptionsMap.add(DHCPOptions.SERVERIDENTIFIER, DHCPOptions.SERVERIDENTIFIER.getByte());
	}
	public static BidirectionalMap<DHCPMessageType, Byte> MessageTypeMap = new BidirectionalMap();
	static{
		for(DHCPMessageType type : DHCPMessageType.values()){
			MessageTypeMap.add(type,type.getByte());
		};
	}
}
