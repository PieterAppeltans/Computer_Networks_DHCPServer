
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
	public abstract byte getByte();
	
}
