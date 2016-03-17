public class DHCPbidirectionalMap {
	
	public static BidirectionalMap<DHCPOpcode, Byte> OpcodeMap = new BidirectionalMap<DHCPOpcode, Byte>();
	
	static{
		OpcodeMap.add(DHCPOpcode.BOOTREQUEST,DHCPOpcode.BOOTREQUEST.getByte());
		OpcodeMap.add(DHCPOpcode.BOOTREPLY,DHCPOpcode.BOOTREPLY.getByte());
	}
	
	public static BidirectionalMap<DHCPHtype, Byte> HtypeMap = new BidirectionalMap<DHCPHtype, Byte>();
	
	static{
		HtypeMap.add(DHCPHtype.ETHERNET,DHCPHtype.ETHERNET.getByte());
		HtypeMap.add(DHCPHtype.IEE802,DHCPHtype.IEE802.getByte());
	}
	
	public static BidirectionalMap<DHCPOptions, Byte> OptionsMap = new BidirectionalMap<DHCPOptions, Byte>();
	
	static{
		OptionsMap.add(DHCPOptions.DHCPMESSAGETYPE, DHCPOptions.DHCPMESSAGETYPE.getByte());
		OptionsMap.add(DHCPOptions.IPADDRESSLEASETIME, DHCPOptions.IPADDRESSLEASETIME.getByte());
		OptionsMap.add(DHCPOptions.REQUESTEDIPADDRESS,DHCPOptions.REQUESTEDIPADDRESS.getByte());
		OptionsMap.add(DHCPOptions.REBINDINGTIME, DHCPOptions.REBINDINGTIME.getByte());
		OptionsMap.add(DHCPOptions.RENEWALTIME, DHCPOptions.RENEWALTIME.getByte());
		OptionsMap.add(DHCPOptions.SERVERIDENTIFIER, DHCPOptions.SERVERIDENTIFIER.getByte());
	}
	
	public static BidirectionalMap<DHCPMessageType, Byte> MessageTypeMap = new BidirectionalMap<DHCPMessageType, Byte>();
	
	static{
		for(DHCPMessageType type : DHCPMessageType.values()){
			MessageTypeMap.add(type,type.getByte());
		};
	}
	
}
