package ovh.gorillahack.wazabi.util;

import java.util.Date;

public interface Utils {

	public static boolean checkString(String s) {
		
		return (s!=null && !s.isEmpty());
	}
	
	public static void c(String message) {
		
		String dateTime = new Date().toString();
		System.out.println("[" + dateTime + "] " + message);
	}
	
}
