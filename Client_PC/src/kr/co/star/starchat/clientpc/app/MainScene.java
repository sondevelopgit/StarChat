package kr.co.star.starchat.clientpc.app;
	
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.co.star.starchat.clientpc.app.manager.AppManager;


public class MainScene extends Application implements Initializable {
	//private TCPClient tcpClient = TCPClient.getInstance();
	private AppManager screenManager = AppManager.getInstance();

	@Override
	public void start(Stage stage) {
		try {
			screenManager.setStage(stage);
			Parent root = FXMLLoader.load(getClass().getResource("../../../../../../resources/layout/MainScene.fxml"));
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() {
		System.out.println("stop");
	}
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	System.out.println("initialize");
    }
    
    public static void main(String[] args) {
		System.out.println("Login " + "main");
		launch(args);
	}
    
}
