package kr.co.star.starchat.server.message.recv;

import java.io.IOException;
import java.nio.ByteBuffer;

import kr.co.star.starchat.server.MessageExecuteAcceptor;
import kr.co.star.starchat.server.message.RecvMessage;
import kr.co.star.starchat.server.tcp.TCPClient;

public class LoginRecvMessage extends RecvMessage {
	private String userID;
	private String userPW;

	public LoginRecvMessage(ByteBuffer buffer, int messageSize) throws IOException {
		super(chatType, messageSize);
		
		userID = readString(buffer, userIDSize).trim();
		userPW = readString(buffer, userPWSize);
	}
	
	@Override
	public void execute(MessageExecuteAcceptor acceptor, TCPClient tcpClient) {
		acceptor.accept(this, tcpClient);
	}
	
	public String getUserID(){
		return userID;
	}
	
	public String getUserPW(){
		return userPW;
	}

} // class
