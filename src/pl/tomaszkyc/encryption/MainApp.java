package pl.tomaszkyc.encryption;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.tomaszkyc.encryption.view.EncryptionOverviewController;

public class MainApp extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayer;
	
	private static final String APP_VERSION = "1.0";
	
	
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
	
	public static void main(String[] args){
		launch(args);
	}
	

	
	

}
