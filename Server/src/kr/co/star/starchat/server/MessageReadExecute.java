package kr.co.star.starchat.server;

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
import kr.co.star.starchat.server.content.ListContent;
import kr.co.star.starchat.server.db.DatabaseHandler;
import kr.co.star.starchat.server.message.BaseMessage;
import kr.co.star.starchat.server.message.RecvMessage;
import kr.co.star.starchat.server.message.SendMessage;
import kr.co.star.starchat.server.message.recv.ChatRecvMessage;
import kr.co.star.starchat.server.message.recv.LoginRecvMessage;
import kr.co.star.starchat.server.message.send.ChatSendMessage;
import kr.co.star.starchat.server.message.send.LoginSendMessage;
import kr.co.star.starchat.server.sending.SendingThreadManager;
import kr.co.star.starchat.server.tcp.TCPClient;
import kr.co.star.starchat.server.tcp.TCPServer;

public class MessageReadExecute implements MessageExecuteAcceptor {
	private MessageExecuteAcceptor printAcceptor = new PrintAcceptor(System.out);
	private TCPServer tcpServer = TCPServer.getInstance();
	
	private DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
	
	private ListContent listContent = ListContent.getInstance();
	
	private static MessageReadExecute instance = new MessageReadExecute();
	
	private Hashtable<String, TCPClient> connectionsByUserIDTable = 
			new Hashtable<String, TCPClient>();
	
	private MessageReadExecute() {
	}
	
	public static MessageReadExecute getInstace() {
		return instance;
	}
	
	public void onExecute(TCPClient client, ByteBuffer buffer) throws IOException {
		System.out.println("call onExecute");
		
		RecvMessage recvMessage = RecvMessage.recvMessage(client, buffer);
		
		if(recvMessage != null) {
			recvMessage.execute(printAcceptor, client);
			recvMessage.execute(this, client);
		}
		else {
			System.out.println("[디버그] 패킷 유실");
		}
	}

	@Override
	public void accept(LoginRecvMessage message, TCPClient tcpClient) {
		System.out.println("[ExecuteDebug] accept Login" + " / " + message.getUserID() + " / " + message.getUserPW());
		
		if(DatabaseHandler.CheckLoginUser(message.getUserID(), message.getUserPW()) == true) {
			System.out.println("첫번째 로그인 성공");
			LoginSendMessage sendMessage;
			sendMessage = new LoginSendMessage(LoginSendMessage.loginSucess);
			System.out.println("[[로그인 메시지]] " + sendMessage.getLoginResult());
			sendMessage.sendMessage(tcpClient);
			System.out.println("loginSucess Send");
		} else {
			System.out.println("첫번째 로그인 실패");
			LoginSendMessage sendMessage;
			sendMessage = new LoginSendMessage(LoginSendMessage.loginFailed);
			sendMessage.sendMessage(tcpClient);
			System.out.println("loginFailed Send");
		}
	}

	@Override
	public void accept(ChatRecvMessage message, TCPClient tcpClient) {
		System.out.println("[ExecuteDebug] accept chat");
		
		Platform.runLater(()->{
			listContent.printMessage("[chat] " + message.getSendUserID().trim() + " -> " + message.getRecvUserID().trim() + " : " + message.getChatMessage().trim());
		});
		
		ChatSendMessage sendMessage;
		try {
			sendMessage = new ChatSendMessage(message);
			sendMessage(message.getRecvUserID(), sendMessage);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	private void sendMessage(String userID, SendMessage message) {
		TCPClient client = connectionsByUserIDTable.get(userID);
		SendingThreadManager sendingThreadManager = SendingThreadManager.getInstance();
		sendingThreadManager.sendingMessage(client, message);
	}
}
