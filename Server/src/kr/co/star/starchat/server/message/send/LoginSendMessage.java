package kr.co.star.starchat.server.message.send;

import java.io.IOException;

import kr.co.star.starchat.server.message.SendMessage;
import kr.co.star.starchat.server.tcp.TCPClient;

public class LoginSendMessage extends SendMessage {
	
	public static final int loginSucess = 10;
	public static final int loginFailed = 20;
	
	private int loginResult = 0;

	public LoginSendMessage(int loginResult) {
		super(loginType, clientIntSize);
		this.loginResult = loginResult;
	}
	
	public int getLoginResult() {
		return loginResult;
	}

	@Override
	protected void addBody(byte[] sendBytes) {
		int index = (clientIntSize * 2);
		try {
			intToBytes(sendBytes, loginResult, index);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
