package pl.tomaszkyc.encryption.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtil {

	/**
	 * Method generated ouput file by crypto mode , key nad input file
	 * 
	 * @param cipMode
	 *            setting what to do with input file (Cipher.ENCRYPT_MODE or
	 *            Cipher.DECRYPT_MODE)
	 * @param secureKey
	 *            password for file (16 bits, 24bits or 32bits)
	 * @param inputFile
	 *            object of input file
	 * @param outputFile
	 *            object of output file
	 * @return
	 * @throws Exception 
	 */
	private static boolean doCrypto(int cipMode, String secureKey, File inputFile, File outputFile) throws Exception {

		boolean isSuccessful = false;

		try {
			Key secretKey = new SecretKeySpec(secureKey.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(cipMode, secretKey);

			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);

			byte[] outputBytes = cipher.doFinal(inputBytes);

			FileOutputStream outputStream = new FileOutputStream(outputFile);
			outputStream.write(outputBytes);

			inputStream.close();
			outputStream.close();

			// put flag to true
			isSuccessful = true;

		} catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException
				| IllegalBlockSizeException | IOException e) {
			throw new Exception( "There was a problem during processing file. Message: " + e.getMessage() );
		}

		return isSuccessful;
	}
	
	public static void encryptFile(String secureKey, String inputFilePath, String outputFilePath) throws Exception{
		
		boolean isSuccessful = false;
		

		//checking file properties
		checkifFileIsValid( inputFilePath, true, false );
		//checkifFileIsValid( outputFilePath, false, true );
		
		
		
		//create file objects
		File inputFile = new File(inputFilePath);
		File outputFile = new File(outputFilePath);
		
		
		isSuccessful = doCrypto(Cipher.ENCRYPT_MODE, secureKey, inputFile, outputFile);
		
		//if something is wrong
		if (!isSuccessful){
			throw new Exception("There was a problem on encrypting file from path: " + inputFilePath);
		}
		
		
		
	}
	
	
	
	
	public static void decryptFile(String secureKey, String inputFilePath, String outputFilePath) throws Exception{
		
		boolean isSuccessful = false;
		

		//checking file properties
		checkifFileIsValid( inputFilePath, true, false );
		//checkifFileIsValid( outputFilePath, false, true );
		
		//create file objects
		File inputFile = new File(inputFilePath);
		File outputFile = new File(outputFilePath);
		
		
		isSuccessful = doCrypto(Cipher.DECRYPT_MODE, secureKey, inputFile, outputFile);
		
		//if something is wrong
		if (!isSuccessful){
			throw new Exception("There was a problem on encrypting file from path: " + inputFilePath);
		}
		
		
		
	}
	
	
	
	
	
	private static void checkifFileIsValid(String filepath, boolean needReadFile, boolean needWriteFile) throws IllegalArgumentException {
		
		File file = new File(filepath);
		
		
		//if file is not null but doesn't exist
		if (file != null
				&& !file.exists()){
			
			throw new IllegalArgumentException( String.format("File in specified path: %s doesn't exist. Please check if file path "
					+ "is correct and try again", file.getPath() ) );
		}
		
		//if file is dir
		if (file != null 
				&& file.isDirectory()){
			
			throw new IllegalArgumentException( String.format("File in specified path: %s is a directory. "
					+ "Please select file not directory and try again", file.getPath() ) );
		}
		
		
		//if we need to read file but we can't
		if (file != null
				&& needReadFile
				&& !file.canRead()){
			
			throw new IllegalArgumentException( String.format("Cannot read from file in location: %s ."
					+ " Check if you have permissions to this file (READ permissions) and try again", file.getPath() ) );
			
		}
		
		//if we need to write to file but we can't
		if (file != null
				&& needWriteFile
				&& !file.canWrite()){
			
			throw new IllegalArgumentException( String.format("Cannot write to file in location: %s ."
					+ " Check if you have permissions to this file (WRITE permissions) and try again", file.getPath() ) );
			
		}
		

		
		
		
		
	}
	
	
	
	

}
