package kr.co.star.starchat.server.message.send;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import kr.co.star.starchat.server.message.SendMessage;
import kr.co.star.starchat.server.message.recv.ChatRecvMessage;
import kr.co.star.starchat.server.tcp.TCPClient;

public class ChatSendMessage extends SendMessage {
	private String senderUserID;
	private String recvUserID;
	private String chatMessage;	
	
	public ChatSendMessage(ChatRecvMessage recvMessage) throws UnsupportedEncodingException {
		super(chatType, (userIDSize * 2) + recvMessage.getChatMessage().getBytes("utf-8").length);
		senderUserID = recvMessage.getSendUserID();
		recvUserID = recvMessage.getRecvUserID();
		chatMessage = recvMessage.getChatMessage();
	}
	
	public ChatSendMessage(String sendUserID, String recvUserID, String chatMessage) throws UnsupportedEncodingException {
		super(chatType, (userIDSize * 2) + chatMessage.getBytes("utf-8").length);
		this.senderUserID = sendUserID;
		this.recvUserID = sendUserID;
		this.chatMessage = chatMessage;
	}

	@Override
	protected void addBody(byte[] sendBytes) {
		int sendIndex = (clientIntSize * 2);
		int recveIndex = sendIndex + userIDSize;
		int chatIndex = recveIndex + userIDSize;
		
		try {
			strToBytes(sendBytes, senderUserID, userIDSize, sendIndex);
			strToBytes(sendBytes, recvUserID, userIDSize, recveIndex);
			strToBytes(sendBytes, chatMessage, chatMessage.getBytes("utf-8").length, chatIndex);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
