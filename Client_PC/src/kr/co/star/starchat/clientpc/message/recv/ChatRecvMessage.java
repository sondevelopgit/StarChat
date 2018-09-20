package kr.co.star.starchat.clientpc.message.recv;

import java.io.IOException;
import java.nio.ByteBuffer;

import kr.co.star.starchat.clientpc.MessageAcceptor;
import kr.co.star.starchat.clientpc.message.RecvMessage;

public class ChatRecvMessage extends RecvMessage {
	private String senderUserID;
    private String recvUserID;
    private String chatMessage;
	
	public ChatRecvMessage(ByteBuffer buffer, int messageSize) throws IOException {
		super(chatType, messageSize);

		senderUserID = readString(buffer, userIDSize).trim();
		recvUserID = readString(buffer, userIDSize).trim();
		chatMessage = readString(buffer, messageSize - (userIDSize * 2));
	}
	
	@Override
	public void execute(MessageAcceptor acceptor) {
		acceptor.accept(this);
	}

	public void setSendUserID(String senderUserID) {
		this.senderUserID = senderUserID;
	}
	
	public void setRecvUserID(String recvUserID) {
		this.recvUserID = recvUserID;
	}
	
	public void setChatMessage(String chatMessage) {
		this.chatMessage = chatMessage;
	}
	
	public String getSendUserID() {
		return senderUserID;
	}
	
	public String getRecvUserID() {
		return recvUserID;
	}
	
	public String getChatMessage() {
		return chatMessage;
	}

}
