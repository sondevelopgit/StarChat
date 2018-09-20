package kr.co.star.starchat.server.message.recv;

import java.io.IOException;
import java.nio.ByteBuffer;
import kr.co.star.starchat.server.MessageExecuteAcceptor;
import kr.co.star.starchat.server.message.RecvMessage;
import kr.co.star.starchat.server.tcp.TCPClient;

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
	public void execute(MessageExecuteAcceptor acceptor, TCPClient tcpClient) {
		acceptor.accept(this, tcpClient);
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
