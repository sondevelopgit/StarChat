package kr.co.star.starchat.clientpc.message.recv;

import java.io.IOException;
import java.nio.ByteBuffer;
import kr.co.star.starchat.clientpc.MessageAcceptor;
import kr.co.star.starchat.clientpc.message.RecvMessage;

public class LoginRecvMessage extends RecvMessage {
	public static final int loginSucess = 10;
	public static final int loginFailed = 20;
	
	private int loginResult;

	public LoginRecvMessage(ByteBuffer buffer, int messageSize) throws IOException {
		super(chatType, messageSize);
		loginResult = readInt(buffer);
		System.out.println("[[로그인 메시지]] " + loginResult);
	}

//	@Override
//	public String toString() {
//		return "LoginRecvMessage (userID : " + userID + ")";
//	}
	
	@Override
	public void execute(MessageAcceptor acceptor) {
		acceptor.accept(this);
	}
	
	public int getLoginResult() {
		return loginResult;
	}

} // class
