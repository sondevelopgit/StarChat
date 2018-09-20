package kr.co.star.starchat.clientpc.app.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import kr.co.star.starchat.clientpc.app.MainScene;
import kr.co.star.starchat.clientpc.app.manager.AppManager;
import kr.co.star.starchat.clientpc.hash.MyHash;
import kr.co.star.starchat.clientpc.message.send.ChatSendMessage;
import kr.co.star.starchat.clientpc.tcp.TCPClientSender;
import kr.co.star.starchat.clientpc.tcp.TCPClient;

public class LoginController implements Initializable {
	private TCPClient tcpClientSocket = TCPClient.getInstance();
	private TCPClientSender tCPClientSender = TCPClientSender.getInstance();

    @FXML private Button btnSignIn;
    @FXML private TextField edtUserID;
    @FXML private PasswordField edtUserPW;
   
    @FXML
    private void handleButtonAction(ActionEvent event) {
    	if(event.getTarget() == btnSignIn) {
    		String edtID = edtUserID.getText();
    		String edtPW = edtUserPW.getText().length() > 0 ? MyHash.encrypt(edtUserPW.getText()) : edtUserPW.getText();
    		
    		if(!(edtID.length() > 0 && edtPW.length() > 0)) {
    			AppManager.createDialog(AlertType.WARNING, "Error", "로그인 불가", "아이디와 비밀번호를 적으셔야합니다!");
    			return;
    		}
    		
			tCPClientSender.createLoginMessage(edtID, edtPW);
    	}
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	System.out.println("controller " + "init");   	
    	
    	tcpClientSocket.openSocketChannel();
    	tcpClientSocket.connectSocketChannel();
    }
}
