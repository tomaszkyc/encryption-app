package pl.tomaszkyc.encryption.view;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.stage.FileChooser;
import pl.tomaszkyc.encryption.MainApp;
import pl.tomaszkyc.encryption.model.EncryptionInfo;
import pl.tomaszkyc.encryption.util.ClipboardUtil;
import pl.tomaszkyc.encryption.util.CryptoUtil;
import pl.tomaszkyc.encryption.util.PasswordUtil;

public class EncryptionOverviewController {

	@FXML
	private Label passwordLengthLabel;

	@FXML
	private Label filePathLabel;

	@FXML
	private RadioButton encryptRadioButton;

	@FXML
	private RadioButton decryptRadioButton;

	@FXML
	private PasswordField passwordPasswordField;
	
	@FXML
	private Button runButton;

	private File file;

	private MainApp mainApp;
	
	private static final String ENCRYPTION_FILE_NAME_PATTERN = "@ACTUAL_FILE_NAME@.encrypted";
	
	private static final String DECRYPTION_FILE_NAME_PATTERN = "@ACTUAL_FILE_NAME@"; 
	
	private static final String RUN_BUTTON_LABEL = "Run @ACTION@";
	
	private static String convertFileNameToEncryption(final String filename){
		
		return ENCRYPTION_FILE_NAME_PATTERN.replaceAll("@ACTUAL_FILE_NAME@", filename);
	}

	

	public EncryptionOverviewController() {

	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		// add password field change listener
		passwordPasswordField.textProperty().addListener((obs, oldText, newText) -> {

			// check if newText is not null
			if (newText != null) {

				// update label value
				passwordLengthLabel.setText(Integer.toString(newText.length()));

			}

		});
		
		//set encryption button as selected
		encryptRadioButton.setSelected( true );
		
		//add label to run button
		//change run button label
		String textToSet = RUN_BUTTON_LABEL.replaceAll("@ACTION@", "encryption");
		runButton.setText( textToSet );

	}

	@FXML
	private void handleChooseFile() {

		FileChooser fileChooser = new FileChooser();
		
		//if decryption radio button is selected
		if ( decryptRadioButton.isSelected() ){
			
			//add filter to encryption files
	        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ENCRYPTION files (*.encryption)", "*.encryption");
	        fileChooser.getExtensionFilters().add(extFilter);
			
		}


		
		
		file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

		if (file != null) {
			filePathLabel.setText(file.getPath());
		} else {
			filePathLabel.setText("No file selected...");
		}

		System.out.println("Wybrany plik: " + filePathLabel.getText());

	}

	/**
	 * Method clear the File Password field
	 */
	@FXML
	private void handleClearButton() {

		if (passwordPasswordField != null) {
			passwordPasswordField.clear();
			updatePasswordLength();
		}

	}

	@FXML
	private void handleDecryptRadioButton() {
		System.out.println("Decrypt button clicked");
		encryptRadioButton.setSelected(false);
		decryptRadioButton.setSelected(true);
		
		//change run button label
		String textToSet = RUN_BUTTON_LABEL.replaceAll("@ACTION@", "decryption");
		runButton.setText( textToSet );
		

	}

	@FXML
	private void handleEncryptRadioButton() {
		System.out.println("Encrypt button clicked");
		decryptRadioButton.setSelected(false);
		encryptRadioButton.setSelected(true);
		
		//change run button label
		String textToSet = RUN_BUTTON_LABEL.replaceAll("@ACTION@", "encryption");
		runButton.setText( textToSet );
	}

	/**
	 * Method generates 16-bit password and show it to User
	 */
	@FXML
	private void handleGeneratePassword() {

		try {
			// generate password
			String generatedPassword = PasswordUtil.generateAESValidPassword(16);

			// set password to field
			passwordPasswordField.setText(generatedPassword);

			// update password length label
			updatePasswordLength();

			// show message
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Password generated!");
			alert.setHeaderText(null);
			alert.setContentText(String.format(
					"Generated password is: %s . \n\nWARNING: Please save this password to use it in the future. \n\nDo you want to save password to the clipboard?",
					generatedPassword));

			// create custom buttons
			ButtonType savePassword = new ButtonType("Save password to the clipboard");
			ButtonType dontSavePassword = new ButtonType("Don't save password to the clipboard");

			alert.getButtonTypes().clear();
			alert.getButtonTypes().addAll(savePassword, dontSavePassword);

			// choosen option
			Optional<ButtonType> option = alert.showAndWait();

			// if somebody click on "Save password" button then save password
			if (option.get() != null && option.get() == savePassword) {

				// copy generated password to clipboard
				ClipboardUtil.copyTextToClipboadr(generatedPassword);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String exceptionText = sw.toString();

			// show error message
			// show message
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Something went wrong...");
			alert.setHeaderText(null);
			alert.setContentText("There was a problem when generating password:\n " + exceptionText);

			alert.showAndWait();
		}

	}
	
	@FXML
	public void handleRunButton() {
		
		
		//for test purposes only
		
		
		
		
		

		//password
		String password = passwordPasswordField.getText();
		
		
		//filepath
		String filePath = filePathLabel.getText();
		
		
		// generates right file String
		filePath = filePath.replaceAll("\\\\", "\\\\\\\\");
		
		
		
		//if password has incorrect length
		if ( !PasswordUtil.isAESPasswordValidLength( password.length() ) ){
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Something went wrong...");
			alert.setHeaderText(null);
			alert.setContentText("Incorrect password length. AES password should have 16, 24 or 32 chars length."
					+ " Your password has: " + password.length() + " chars");

			alert.showAndWait();
			return;
		}
		
		
		
		//check if File path label has value "No file spelected"
		if (filePathLabel.getText().equals("No file selected...")){
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Something went wrong...");
			alert.setHeaderText(null);
			alert.setContentText("Please specify file path. To do this - click the button \"Choose file\"");

			alert.showAndWait();
			return;
			
		}
		
		
		
		
		//if encryption is selected
		if ( encryptRadioButton.isSelected() ){

			
			//generate encryption file path
			String encryptionFilePath = convertFileNameToEncryption(filePath);
			
			
			//create an object
			EncryptionInfo encryptionInfo = new EncryptionInfo();
			encryptionInfo.setPassword(password);
			encryptionInfo.setEncryptingFile(encryptRadioButton.isSelected());
			encryptionInfo.setInputFilePath(filePath);
			
			mainApp.showSaveFileWindow(encryptionInfo);
			
			
			
			
		}
		//if decryption is selected
		else{
			
			//generate encryption file path
			//String encryptionFilePath = convertFileNameToDecryption(filePath);
			
			
			//create an object
			EncryptionInfo encryptionInfo = new EncryptionInfo();
			encryptionInfo.setPassword(password);
			encryptionInfo.setEncryptingFile(encryptRadioButton.isSelected());
			encryptionInfo.setInputFilePath(filePath);
			
			mainApp.showSaveFileWindow(encryptionInfo);
			
		}
		
		
		/*
		//if encryption is selected
		if ( encryptRadioButton.isSelected() ){
			
			String encryptionFilePath = convertFileNameToEncryption(filePath);
			
			try {
				CryptoUtil.encryptFile(password, filePath, encryptionFilePath);
			} catch (Exception ex) {
				ex.printStackTrace();
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String exceptionText = sw.toString();

				System.out.println("Error message: " + ex.getMessage());
				
				// show error message
				// show message
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Something went wrong...");
				alert.setHeaderText(null);
				alert.setContentText(ex.getMessage());

				alert.showAndWait();
			}
			
		}
		//if decryption is selected
		else{
			
			String decryptionFIlePath = convertFileNameToDecryption( filePath );
			
			try {
				CryptoUtil.decryptFile(password, filePath, decryptionFIlePath);
			} catch (Exception ex) {
				ex.printStackTrace();
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String exceptionText = sw.toString();

				System.out.println("Error message: " + ex.getMessage());
				
				// show error message
				// show message
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Something went wrong...");
				alert.setHeaderText(null);
				alert.setContentText(ex.getMessage());

				alert.showAndWait();
			}
			
		}
		
		
		
		*/
		
	}
	
	
	

	@FXML
	public void handlePasswordInput() {

		// every time something changed in Password field - update password
		// length
		updatePasswordLength();

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	private void updatePasswordLength() {

		passwordLengthLabel.setText(Integer.toString(passwordPasswordField.getLength()));

	}
	
	

	

}
