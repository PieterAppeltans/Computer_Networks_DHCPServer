/**
 * Enumeration containing the possible hardware types.
 * 
 * @author	Pieter Appeltans & Hans Cauwenbergh
 */
public enum DHCPHtype {
	
	ETHERNET{
		@Override
		public byte getByte() {
			return (byte)1;
		}
	},
	IEE802{
		@Override
		public byte getByte() {
			return (byte)6;
		}
	};
		
	/**
	 * Return the corresponding byte used in the htype field of the DHCPMessage.
	 * 
	 * @return	A byte representing the htype.
	 */
	public abstract byte getByte();
	
}