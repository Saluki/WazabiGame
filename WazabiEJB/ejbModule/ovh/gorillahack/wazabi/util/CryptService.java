package ovh.gorillahack.wazabi.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidParameterException;

public class CryptService {
	
	private static final int ITERATIONS = 1000;
	private static final String SALT = "FE26EEE87B528135";
	private static final int KEYLENGTH = 64*8;
	private static final String CIPHER = "PBKDF2WithHmacSHA1";

	public static String hash(String s) {
		
		if( s == null ) throw new InvalidParameterException();
				
		PBEKeySpec spec = new PBEKeySpec(s.toCharArray(), SALT.getBytes(), ITERATIONS, KEYLENGTH);
		
		byte[] hash;
		try {
			hash = SecretKeyFactory.getInstance(CIPHER).generateSecret(spec).getEncoded();			
		}
		catch(GeneralSecurityException e) { return null; }
		
		return toHex( hash );
	}
	
	private static String toHex(byte[] array)
    {
        String hex = ( new BigInteger(1, array) ).toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        
        if(paddingLength > 0)
        	return String.format("%0"  +paddingLength + "d", 0) + hex;
        
        return hex;
    }

}
