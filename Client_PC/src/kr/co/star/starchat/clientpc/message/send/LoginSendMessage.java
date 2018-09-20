package kr.co.star.starchat.clientpc.message.send;

import java.io.IOException;
import kr.co.star.starchat.clientpc.message.SendMessage;

public class LoginSendMessage extends SendMessage {
	private String userID;
	private String userPW;

	public LoginSendMessage(String userID, String userPW) {
		super(loginType, userIDSize + userPWSize);
		this.userID = userID;
		this.userPW = userPW;
	}

	@Override
	protected void addBody(byte[] sendBytes) {
		int userIDIndex = (clientIntSize * 2);
		int userPWIndex = userIDIndex + userIDSize;
		
		try {
			strToBytes(sendBytes, userID, userIDSize, userIDIndex);
			strToBytes(sendBytes, userPW, userPWSize, userPWIndex);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
