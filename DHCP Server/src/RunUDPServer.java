public class RunUDPServer {
	
	public static void main(String[] args) throws Exception {
		DHCPServer server = new DHCPServer(9000,new byte[]{0,0,0,1},new byte[]{0,0,0,(byte) 0Xff},new byte[]{10,12,0,0},10,1000,100);
		server.run();
	}
	
}