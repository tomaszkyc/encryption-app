package pl.tomaszkyc.encryption.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EncryptionInfo {
	
	private final StringProperty password;
	private final StringProperty filePath;
	private final StringProperty chooseOption;
	public String getPassword() {
		return password.get();
	}
	public String getFilePath() {
		return filePath.get();
	}
	public String getChooseOption() {
		return chooseOption.get();
	}
	
	public void setPassword(String password){
		this.password.set(password);
	}
	
	public void setFilePath(String filePath){
		this.filePath.set(filePath);
	}
	
	public void setChooseOption(String chooseOption){
		this.chooseOption.set(chooseOption);
	}
	
	public EncryptionInfo(String password, String filePath, String chooseOption){
		this.password = new SimpleStringProperty(password);
		this.filePath = new SimpleStringProperty(filePath);
		this.chooseOption = new SimpleStringProperty(chooseOption);
		
		
	}

	public EncryptionInfo(){
		this("", "", "");
	}

}
