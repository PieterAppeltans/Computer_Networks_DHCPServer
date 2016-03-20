/**
 * An enumeration storing the different DHCP message types with their byte value.
 * 
 * @author	Pieter Appeltans & Hans Cauwenbergh
 */
public enum DHCPMessageType {
	
	DHCPDISCOVER{
		@Override
		public byte getByte() {
			return 1;
		}
	},
	DHCPOFFER{
		@Override
		public byte getByte() {
			return 2;
		}
	},
	DHCPREQUEST{
		@Override
		public byte getByte() {
			return 3;
		}
	},
	DHCPDECLINE{
		@Override
		public byte getByte() {
			return 4;
		}
	},
	DHCPACK{
		@Override
		public byte getByte() {
			return 5;
		}
	},
	DHCPNAK{
		@Override
		public byte getByte() {
			return 6;
		}
	},
	DHCPRELEASE{
		@Override
		public byte getByte() {
			return 7;
		}  
	},
	DHCPINFORM{
		@Override
		public byte getByte() {
			return 8;
		}  	  
	};

	/**
	 * Method that returns the byte associated with each message type.
	 * 
	 * @return	The byte associated with the given message type.
	 */
	public abstract byte getByte();
      
}