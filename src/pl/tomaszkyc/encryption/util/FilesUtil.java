package pl.tomaszkyc.encryption.util;

import java.io.File;

public class FilesUtil {
	
	private static boolean checkIfFileExists(String filePath){
		
		boolean isFileExist = true;
		
		if (filePath == null || filePath.isEmpty()){
			return false;
		}
		
		File file = new File(filePath);
		
		if (!file.exists()){
			return false;
		}		
		
		
		
		return isFileExist;
	}
	
	
	

}
