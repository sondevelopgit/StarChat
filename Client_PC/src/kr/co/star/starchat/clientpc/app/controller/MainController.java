package kr.co.star.starchat.clientpc.app.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kr.co.star.starchat.clientpc.app.viewmodel.FriendsListItem;
import kr.co.star.starchat.clientpc.app.viewmodel.FriendsListItemViewCell;

public class MainController implements Initializable {
    @FXML private ListView<FriendsListItem> lvFriendsList;
    private ObservableList<FriendsListItem> ovFriendsList;

    public MainController() {
    	ovFriendsList = FXCollections.observableArrayList();
    	ovFriendsList.addAll(
    			new FriendsListItem("내 프로필", false),
    			new FriendsListItem("손조성", true),
    			new FriendsListItem("친구 101", false),
    			new FriendsListItem("문태영", true),
    			new FriendsListItem("테스트", true),
    			new FriendsListItem("김성일", true),
    			new FriendsListItem("홍길동", true)
    	);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	lvFriendsList.setItems(ovFriendsList);
    	lvFriendsList.setCellFactory(listView -> new FriendsListItemViewCell());
    	
    }
    
    @FXML
    private void load(ActionEvent event) {
    	//
    }
    
    public void createDialog(AlertType alertType, String title, String header, String content) {
    	Platform.runLater(()-> {
    		Alert alert = new Alert(alertType);
        	alert.setTitle(title);
        	alert.setHeaderText(header);
        	alert.setContentText(content);
        	alert.showAndWait();
    	});
    	//AlertType.ERROR
    }
}
