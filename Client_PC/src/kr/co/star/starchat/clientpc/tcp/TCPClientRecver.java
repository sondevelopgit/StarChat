package kr.co.star.starchat.clientpc.tcp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;

import javafx.application.Platform;
import kr.co.star.starchat.clientpc.MessageReadExecute;

public class TCPClientRecver {
	private static TCPClientRecver instance = new TCPClientRecver();
	
	private AsynchronousSocketChannel socketChannel;
	private MessageReadExecute messageReadExecute = MessageReadExecute.getInstace();
	
	private TCPClientRecver() {
	}
	
	public static TCPClientRecver getInstance() {
		return instance;
	}
	
	public void setSocketChannel(AsynchronousSocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}
	
	void recvByteBuffer() {
		ByteBuffer byteBuffer = ByteBuffer.allocate(512);
		socketChannel.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
			@Override
			public void completed(Integer result, ByteBuffer attachmentByteBuffer) {
				System.out.println("ClientRecver, recvByteBuffer() completed() , buffer.capacity: " + attachmentByteBuffer.capacity() + " , buffer.lemining: " + attachmentByteBuffer.remaining() + " , buffer.limit: " + attachmentByteBuffer.limit());
		
				try {
					messageReadExecute.onExecute(byteBuffer);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				ByteBuffer byteBuffer = ByteBuffer.allocate(512);
				socketChannel.read(byteBuffer, byteBuffer, this);
			}
			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				System.out.println("ClientRecver, recvByteBuffer() failed()");
			}
		});
	}
}
