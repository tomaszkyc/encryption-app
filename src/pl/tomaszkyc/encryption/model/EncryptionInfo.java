package pl.tomaszkyc.encryption.model;

public class EncryptionInfo {

	private String password;

	private String inputFilePath;
	
	private String outputFilePath;

	public EncryptionInfo() {

	}

	public EncryptionInfo(String password, String inputFilePath, String outputFilePath) {
		super();
		this.password = password;
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;
	}

	public String getOutputFilePath() {
		
		return outputFilePath;
	}

	public String getPassword() {
		return password;
	}

	public void setOutputFilePath(String outputFilePath) {
		this.outputFilePath = outputFilePath;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
