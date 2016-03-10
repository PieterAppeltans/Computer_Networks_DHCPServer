
public enum DHCPOptions {
	REQUESTEDIPADDRESS{
		@Override
		public byte getByte() {
			return (byte)50;
		}
		
	},
	IPADDRESSLEASETIME{
		@Override
		public byte getByte() {
			return (byte) 51;
		}},
	OPTIONOVERLOAD{
		@Override
		public byte getByte() {
			return (byte)52;
		}},
	DHCPMESSAGETYPE{
		@Override
		public byte getByte() {
			return (byte)53;
		}},
	RENEWALTIME{
		@Override
		public byte getByte() {
			return (byte)58;
		}},
	REBINGINGTIME{

		@Override
		public byte getByte() {
			return (byte)59;
		}};
	public abstract byte getByte();
}
