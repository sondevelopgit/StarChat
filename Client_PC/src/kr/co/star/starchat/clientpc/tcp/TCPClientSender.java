package kr.co.star.starchat.clientpc.tcp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayDeque;
import java.util.Queue;

import javafx.application.Platform;
import kr.co.star.starchat.clientpc.message.SendMessage;
import kr.co.star.starchat.clientpc.message.send.ChatSendMessage;
import kr.co.star.starchat.clientpc.message.send.LoginSendMessage;

public class TCPClientSender extends Thread {
	private static TCPClientSender instance = new TCPClientSender();
	
	private AsynchronousSocketChannel socketChannel;
	
	private boolean isRunning = true;
	private Queue<SendMessage> sendMessageQueue = new ArrayDeque<>();
	
	private TCPClientSender() {
	}
	
	public static TCPClientSender getInstance() {
		return instance;
	}
	
	public void setSocketChannel(AsynchronousSocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}
	
	public void sendByteBuffer(ByteBuffer byteBuffer) {		
		socketChannel.write(byteBuffer, null, new CompletionHandler<Integer, Void>() {
			@Override
			public void completed(Integer result, Void attachment) {
				System.out.println("ClientSender, sendByteBuffer() completed()");
			}
			@Override
			public void failed(Throwable exc, Void attachment) {
				System.out.println("ClientSender, sendByteBuffer() failed()");
			}
		});
	}
	
	public void createLoginMessage(String userID, String userPW) {
		LoginSendMessage message = new LoginSendMessage(userID, userPW);			
		message.sendMessage(this);
    }
	
	public void createChatMessage(String sendUserID, String recvUserID, String text) throws IOException {
        ChatSendMessage message = new ChatSendMessage(sendUserID, recvUserID, text);
        message.sendMessage(this);
    }
	
}
