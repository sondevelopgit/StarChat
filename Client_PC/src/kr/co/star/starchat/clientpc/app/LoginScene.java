package kr.co.star.starchat.clientpc.app;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kr.co.star.starchat.clientpc.app.manager.AppManager;

public class LoginScene extends Application {
	//private TCPClient tcpClient = TCPClient.getInstance();
	private AppManager screenManager = AppManager.getInstance();

	@Override
	public void start(Stage stage) {
		try {
			screenManager.setStage(stage);
			Parent root = FXMLLoader.load(getClass().getResource("../../../../../../resources/layout/LoginScene.fxml"));
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() {
		System.out.println("Login " + "stop");
	}
	
	public static void main(String[] args) {
		System.out.println("Login " + "main");
		launch(args);
	}
	
}
