package ovh.gorillahack.wazabi.utils;

public interface Utils {

	public static final String FLASH_KEY = "flashMessage";
	public static final String COUNT_KEY = "flashCount";
	
	public static boolean checkString(String s) {
		
		return (s!=null && !s.isEmpty());
	}
	
}
