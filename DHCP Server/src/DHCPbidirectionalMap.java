/**
 * Two way searchable maps for fields used by DHCP messages.
 *
 * @author 	Pieter Appeltans & Hans Cauwenbergh
 */
public class DHCPbidirectionalMap {
	
	/**
	 * A bidirectonial map of the possible opcodes for a DHCP message.
	 */
	public static BidirectionalMap<DHCPOpcode, Byte> OpcodeMap = new BidirectionalMap<DHCPOpcode, Byte>();
	
	static{
		OpcodeMap.add(DHCPOpcode.BOOTREQUEST,DHCPOpcode.BOOTREQUEST.getByte());
		OpcodeMap.add(DHCPOpcode.BOOTREPLY,DHCPOpcode.BOOTREPLY.getByte());
	}
	
	/**
	 * A bidirectonial map of the possible htypes for a DHCP message.
	 */
	public static BidirectionalMap<DHCPHtype, Byte> HtypeMap = new BidirectionalMap<DHCPHtype, Byte>();
	
	static{
		HtypeMap.add(DHCPHtype.ETHERNET,DHCPHtype.ETHERNET.getByte());
		HtypeMap.add(DHCPHtype.IEE802,DHCPHtype.IEE802.getByte());
	}
	
	/**
	 * A bidirectonial map of the possible option fields for a DHCP message.
	 */
	public static BidirectionalMap<DHCPOption, Byte> OptionsMap = new BidirectionalMap<DHCPOption, Byte>();
	
	static{
		OptionsMap.add(DHCPOption.DHCPMESSAGETYPE, DHCPOption.DHCPMESSAGETYPE.getByte());
		OptionsMap.add(DHCPOption.IPADDRESSLEASETIME, DHCPOption.IPADDRESSLEASETIME.getByte());
		OptionsMap.add(DHCPOption.REQUESTEDIPADDRESS,DHCPOption.REQUESTEDIPADDRESS.getByte());
		OptionsMap.add(DHCPOption.REBINDINGTIME, DHCPOption.REBINDINGTIME.getByte());
		OptionsMap.add(DHCPOption.RENEWALTIME, DHCPOption.RENEWALTIME.getByte());
		OptionsMap.add(DHCPOption.SERVERIDENTIFIER, DHCPOption.SERVERIDENTIFIER.getByte());
	}
	
	/**
	 * A bidirectonial map of the possible message types for a DHCP message.
	 */
	public static BidirectionalMap<DHCPMessageType, Byte> MessageTypeMap = new BidirectionalMap<DHCPMessageType, Byte>();
	
	static{
		for(DHCPMessageType type : DHCPMessageType.values()){
			MessageTypeMap.add(type,type.getByte());
		};
	}
	
}