package kr.co.star.starchat.clientpc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import kr.co.star.starchat.clientpc.app.MainScene;
import kr.co.star.starchat.clientpc.app.manager.AppManager;
import kr.co.star.starchat.clientpc.message.RecvMessage;
import kr.co.star.starchat.clientpc.message.recv.ChatRecvMessage;
import kr.co.star.starchat.clientpc.message.recv.LoginRecvMessage;

public class MessageReadExecute implements MessageAcceptor {
	private static MessageReadExecute instance = new MessageReadExecute();
	
	private AppManager screenManager = AppManager.getInstance();
	
	private MessageReadExecute() {
	}
	
	public static MessageReadExecute getInstace() {
		return instance;
	}
	
	public void onExecute(ByteBuffer buffer) throws IOException {	
		RecvMessage recvMessage = RecvMessage.recvMessage(buffer);
		
		if(recvMessage != null) {
			recvMessage.execute(this);
		}
		else {
			System.out.println("[디버그] 패킷 유실");
		}
	}

	@Override
	public void accept(LoginRecvMessage message) {
		System.out.println("[ExecuteDebug] accept Login");
		
		if(message.getLoginResult() == message.loginSucess) {
			System.out.println("loginSucess");
			screenManager.changeScreen(new MainScene(), screenManager.getStage());
		}
		else if(message.getLoginResult() == message.loginFailed) {
			System.out.println("loginFail");
			AppManager.createDialog(AlertType.WARNING, "Error", "계정 오류", "아이디와 비밀번호를 제대로 적으세요!");
		}
		else {
			System.out.println("loginNULL");
		}
	}

	@Override
	public void accept(ChatRecvMessage message) {
		System.out.println("[ExecuteDebug] accept chat");
		
	}
}
