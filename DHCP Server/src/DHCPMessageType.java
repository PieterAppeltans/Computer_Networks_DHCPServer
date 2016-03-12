public enum DHCPMessageType {
	
	  DHCPDISCOVER{
		@Override
		public byte getByte() {
			return 1;
		}},
      DHCPOFFER{
		@Override
		public byte getByte() {
			return 2;
		}},
      DHCPREQUEST{
		@Override
		public byte getByte() {
			return 3;
		}},
      DHCPDECLINE{
		@Override
		public byte getByte() {
			return 4;
		}},
	  DHCPACK{
		@Override
		public byte getByte() {
			return 5;
		}},
	  DHCPNAK{
		@Override
		public byte getByte() {
			return 6;
		}},
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
	
      public abstract byte getByte();
      
}
