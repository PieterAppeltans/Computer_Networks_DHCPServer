/**
 * Enumeration containing the different possible DHCP options.
 * 
 * @author 	Pieter Appeltans & Hans Cauwenbergh
 */
public enum DHCPOption {
	
	REQUESTEDIPADDRESS{
		@Override
		public byte getByte() {
			return (byte) 50;
		}
	},
	IPADDRESSLEASETIME{
		@Override
		public byte getByte() {
			return (byte) 51;
		}
	},
	OPTIONOVERLOAD{
		@Override
		public byte getByte() {
			return (byte) 52;
		}
	},
	DHCPMESSAGETYPE{
		@Override
		public byte getByte() {
			return (byte) 53;
		}
	},
	RENEWALTIME{
		@Override
		public byte getByte() {
			return (byte) 58;
		}
	},
	REBINDINGTIME{
		@Override
		public byte getByte() {
			return (byte) 59;
		}
	},
	SERVERIDENTIFIER{
		@Override
		public byte getByte() {
			return (byte) 54;
		}
	};
	
	
	/**
	 * Returning the corresponding byte used in the DHCP option field of the DHCPMessage.
	 * 
	 * @return	The byte associated with the given option.
	 */
	public abstract byte getByte();
	
}