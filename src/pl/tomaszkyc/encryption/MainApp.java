package pl.tomaszkyc.encryption;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.tomaszkyc.encryption.model.EncryptionInfo;
import pl.tomaszkyc.encryption.util.CryptoUtil;
import pl.tomaszkyc.encryption.view.EncryptionOverviewController;
import pl.tomaszkyc.encryption.view.SaveFileOverviewController;

public class MainApp extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayer;
	
	private static final String APP_VERSION = "1.1";
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Encryption App " + APP_VERSION);
		this.primaryStage.setResizable(false);
		
		//add icon
		this.primaryStage.getIcons().add(new Image("file:resources/images/app_icon3.png"));
		
		
		initRootLayout();
		                                                       
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	
	


	public void initRootLayout() {
		// TODO Auto-generated method stub
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/EncryptionOverview.fxml"));
			rootLayer = (AnchorPane) loader.load();
			
			Scene scene = new Scene(rootLayer);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			EncryptionOverviewController controller = loader.getController();
			controller.setMainApp(this);
			
			
			
		}
		catch(Exception e){
			System.out.println("Error when init root layout: " + e);
			e.printStackTrace();
		}
		
	}
	
	public void showSaveFileWindow(EncryptionInfo encryptionInfo){
		
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SaveFileOverview.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Stage saveFileStage = new Stage();
			saveFileStage.setTitle("Save file...");
			saveFileStage.initModality(Modality.WINDOW_MODAL);
			saveFileStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			saveFileStage.setScene(scene);
			
			SaveFileOverviewController controller = loader.getController();
			controller.setDialogStage(saveFileStage);
			controller.setEncryptionInfo(encryptionInfo);
			
			//FileChooser
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save file");

			//if this file is encrypted -> add file filter
			if ( encryptionInfo.isEncryptingFile() ){
		        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ENCRYPTION files (*.encryption)", "*.encryption");
		        fileChooser.getExtensionFilters().add(extFilter);
			}
			
			
			File dest = fileChooser.showSaveDialog(this.getPrimaryStage());
			
			if (dest != null) {
				
				EncryptionInfo encryptionInfoTmp = encryptionInfo;
				encryptionInfoTmp.setOutputFilePath( dest.getPath() );
				
				controller.setEncryptionInfo(encryptionInfoTmp);
				
				
				if ( encryptionInfo.isEncryptingFile() ){
					try {
						CryptoUtil.encryptFile(encryptionInfoTmp.getPassword(),
												encryptionInfoTmp.getInputFilePath(), encryptionInfoTmp.getOutputFilePath());
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
				else{
					try {
						CryptoUtil.decryptFile(encryptionInfoTmp.getPassword(),
								encryptionInfoTmp.getInputFilePath(), encryptionInfoTmp.getOutputFilePath());
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
				
				
				
				
				
				
				saveFileStage.showAndWait();
			}
			else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("No file choosen");
				alert.setHeaderText(null);
				alert.setContentText("No file choosen - please specify file path and try again");
				alert.showAndWait();
			}


			
		}
		catch(Exception e){
			System.out.println("Error when showing save file window: " + e.getMessage());
			e.printStackTrace();
		}
		

		
		
		
		
	}
	
	
	
	public static void main(String[] args){
		launch(args);
	}
	

	
	

}
