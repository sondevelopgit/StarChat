package kr.co.star.starchat.clientpc.app.manager;

import java.io.IOException;
import java.util.HashMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kr.co.star.starchat.clientpc.app.LoginScene;
import kr.co.star.starchat.clientpc.app.MainScene;

public class AppManager {
	private static AppManager instance = new AppManager();

	private Stage currentStage;
    
	private AppManager() {
	}
	
	public static AppManager getInstance() {
		return instance;
	}
	
	public static void changeScreen(Application app, Stage prevStage) {
		Platform.runLater(()->{
			try {
				app.start(new Stage());
				prevStage.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public static void createDialog(AlertType alertType, String title, String header, String content) {
    	Platform.runLater(()-> {
    		Alert alert = new Alert(alertType);
        	alert.setTitle(title);
        	alert.setHeaderText(header);
        	alert.setContentText(content);
        	alert.showAndWait();
    	});
    	//AlertType.ERROR
    }
	
	public void setStage(Stage stage) {
		this.currentStage = stage;
	}
	
	public Stage getStage() {
		return currentStage;
	}

}
