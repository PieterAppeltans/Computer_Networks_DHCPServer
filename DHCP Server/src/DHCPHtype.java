/**
 * Enumeration containing the possible Hardware types.
 * @author Pieter Appeltans & Hans Cauwenbergh
 *
 */
public enum DHCPHtype {
	
	ETHERNET{
		@Override
		public byte getByte() {
			return (byte)1;
		}},
	IEE802{
		@Override
		public byte getByte() {
			return (byte)6;
		}};
	/**
	 * Returning the corresponding byte used in the htype-field of the DHCPMessage.
	 * @return
	 */
	public abstract byte getByte();
	
}