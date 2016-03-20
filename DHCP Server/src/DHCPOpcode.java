/**
 * Enumeration containing the different possible DHCP opcodes.
 * @author Pieter Appeltans & Hans Cauwenbergh
 */
public enum DHCPOpcode {
	
	BOOTREQUEST{

		@Override
		public byte getByte() {
			return (byte)1;
		}
		
	},
	BOOTREPLY{

		@Override
		public byte getByte() {
			return (byte)2;
		}
		
	};
	/**
	 * Returning the corresponding byte used in the dhcpopcode-field of the DHCPMessage.
	 * @return
	 */
	public abstract byte getByte();
	
}
