import java.time.LocalDateTime;
/**
 * Class to print error messages to the console.
 * 
 * 
 * @author Pieter Appeltans & Hans Cauwenbergh
 */
public class ErrorPrinter {
	/**
	 * Method that prints the given error to the output screen. 
	 * @param error String containing the error message.
	 */
	public static void print(String error){
		System.out.println("ERROR " + LocalDateTime.now().toString() + " : " +error);
	}
}