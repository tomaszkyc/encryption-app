package pl.tomaszkyc.encryption.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import pl.tomaszkyc.encryption.MainApp;
import pl.tomaszkyc.encryption.model.EncryptionInfo;

public class SaveFileOverviewController {

	private EncryptionInfo encryptionInfo;

	private MainApp mainApp;

	private Stage dialogStage;

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
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
