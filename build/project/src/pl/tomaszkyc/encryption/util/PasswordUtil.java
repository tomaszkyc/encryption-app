package pl.tomaszkyc.encryption.util;

import java.security.SecureRandom;
import java.util.Random;

public class PasswordUtil {

	private static final int CORRECT_AES_PASSWORD_LENGTH[] = { 16, 24, 32 };

	/**
	 * Method generates password by entered length
	 * 
	 * @param length
	 *            (password length)
	 * @throws Exception
	 *             (incorrect password length)
	 */
	public static String generateAESValidPassword(int length) throws Exception {

		StringBuilder password = new StringBuilder();

		// check if password length is correct
		if (isAESPasswordValidLength(length)) {
			
			Random random = new SecureRandom();
			
			for(int i = 0; i < length; i++){
				
				//getting next chars from ASCII table (decimal range: 33 - 126)
				char sign = (char)(random.nextInt(93) + 33);
				
				password.append( sign );
			}
			
			
			
		} else {
			throw new Exception("AES password should have 16, 24 or 32 chars length!");

		}

		return password.toString();
	}

	/**
	 * method checks if the password length is correct for AES password
	 * 
	 * @param length
	 *            length of password
	 * @return TRUE - if the password has correct length FALSE - if the password
	 *         has incorrect length
	 */
	public static boolean isAESPasswordValidLength(int length) {

		boolean isValid = false;

		for (int i = 0; i < CORRECT_AES_PASSWORD_LENGTH.length; i++) {

			if (length == CORRECT_AES_PASSWORD_LENGTH[i]) {
				return true;
			}
		}

		return isValid;
	}

}
