import java.time.LocalDateTime;


public class ErrorPrinter {
	public static void print(String error){
		System.out.println("ERROR " + LocalDateTime.now().toString() +" : "+error);
	}
}
