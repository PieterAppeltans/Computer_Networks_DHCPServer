import java.net.InetAddress;


public class NotAvailable extends Exception{

	private static final long serialVersionUID = 1L;

	public NotAvailable(InetAddress ip){
		super("Following IP-address is not available: " + ip.toString());
	}
}
