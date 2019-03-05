package pl.tomaszkyc.encryption.model;

public class EncryptionInfo {

	private String password;

	private String inputFilePath;

	private String outputFilePath;

	private boolean isEncryptingFile;

	public EncryptionInfo() {

	}

	public EncryptionInfo(String password, String inputFilePath, String outputFilePath, boolean isEncryptingFile) {
		super();
		this.password = password;
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;
		this.isEncryptingFile = isEncryptingFile;
	}

	public String getInputFilePath() {
		return inputFilePath;
	}

	public String getOutputFilePath() {
		return outputFilePath;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * method to check if file is now decrrypting We don't need to use another
	 * boolean field in class because this can be done with opposite to field
	 * isEncryptingFile
	 * 
	 * @return TRUE if file is decrypting FALSE if file is not decrypting ->
	 *         encrypting
	 */
	public boolean isDecryptingFile() {
		return !isEncryptingFile();
	}

	public boolean isEncryptingFile() {
		return isEncryptingFile;
	}

	public void setEncryptingFile(boolean isEncryptingFile) {
		this.isEncryptingFile = isEncryptingFile;
	}

	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}

	public void setOutputFilePath(String outputFilePath) {
		this.outputFilePath = outputFilePath;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
