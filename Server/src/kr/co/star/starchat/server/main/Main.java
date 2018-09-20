package kr.co.star.starchat.server.main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import kr.co.star.starchat.server.content.ListContent;
import kr.co.star.starchat.server.db.DatabaseHandler;
import kr.co.star.starchat.server.tcp.TCPServer;


public class Main extends Application implements Initializable {		
	//private ListContent listContent = ListContent.getInstance();
	private TCPServer tcpServer = TCPServer.getInstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void start(Stage stage) {
		System.out.println("[디버그] " + "start");
		startStage(stage);

	}
	
	@Override
	public void stop() {
		tcpServer.shuttdownSocketChannel();
	}
	
	public static void main(String[] args) {
		System.out.println("[디버그] " + "main");
		launch(args);
	}
	
	private void startStage(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../../../../../../resources/layout/Main.fxml"));
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
}



