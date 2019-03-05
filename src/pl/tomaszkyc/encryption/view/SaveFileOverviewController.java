package pl.tomaszkyc.encryption.view;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import pl.tomaszkyc.encryption.MainApp;
import pl.tomaszkyc.encryption.model.EncryptionInfo;

public class SaveFileOverviewController {
	
	@FXML
	private Label windowTitleLabel;
	
	@FXML
	private Label fileLocationLabel;
	
	
	

	private EncryptionInfo encryptionInfo;

	private MainApp mainApp;

	private Stage dialogStage;
	
	@FXML
	public void handleCloseWindowButton() {
		
		dialogStage.close();
		
	}
	

	public SaveFileOverviewController() {

	}

	public Stage getDialogStage() {
		return dialogStage;
	}

	public EncryptionInfo getEncryptionInfo() {
		return encryptionInfo;
	}

	public MainApp getMainApp() {
		return mainApp;
	}
	
	

	@FXML
	private void initialize() {
		

		
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setEncryptionInfo(EncryptionInfo encryptionInfo) {
		this.encryptionInfo = encryptionInfo;
		
		// set window title
		windowTitleLabel.setMaxWidth(Double.MAX_VALUE);
		AnchorPane.setLeftAnchor(windowTitleLabel, 0.0);
		AnchorPane.setRightAnchor(windowTitleLabel, 0.0);
		windowTitleLabel.setAlignment(Pos.TOP_CENTER);
		windowTitleLabel.setTextAlignment(TextAlignment.CENTER);

		
		String message = "FILE SUCCESSFULLY @ACTION@";
		
		if ( getEncryptionInfo().isEncryptingFile() ){
			
			message = message.replaceAll("@ACTION@", "ENCRYPTED");
			
		}
		else{
			message = message.replaceAll("@ACTION@", "DECRYPTED");
		}

		windowTitleLabel.setText(message);
		
		//set file location label
		fileLocationLabel.setText( getEncryptionInfo().getOutputFilePath() );
		
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
