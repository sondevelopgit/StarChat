package kr.co.star.starchat.server.tcp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.List;
import javafx.application.Platform;
import kr.co.star.starchat.server.MessageExecuteAcceptor;
import kr.co.star.starchat.server.MessageReadExecute;
import kr.co.star.starchat.server.content.ListContent;
import kr.co.star.starchat.server.message.BaseMessage;
import kr.co.star.starchat.server.message.RecvMessage;

public class TCPClient {
	public TCPClient tcpClient;
	
	private ListContent listContent = ListContent.getInstance();
	private MessageReadExecute messagerExecuteListener = MessageReadExecute.getInstace();
	private TCPServer tcpServer = TCPServer.getInstance();
	
	private AsynchronousSocketChannel socketChannel;
	
	public TCPClient(AsynchronousSocketChannel socketChannel) {
		this.socketChannel = socketChannel;
		tcpClient = this;
		receive();
	}
	
	public AsynchronousSocketChannel getSockgetChannel() {
		return socketChannel;
	}
	
	private void receive() {
		ByteBuffer byteBuffer = ByteBuffer.allocate(512);
		socketChannel.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
			@Override
			public void completed(Integer result, ByteBuffer attachmentByteBuffer) {
				try {
					String message = "요청 처리: " + socketChannel.getRemoteAddress() + ": " + Thread.currentThread().getName();
					
					Platform.runLater(()->{
						System.out.println("[" + getClass().getSimpleName() + "] " + message);
						listContent.printMessage("[" + getClass().getSimpleName() + "] " + message);
					});
					
					messagerExecuteListener.onExecute(tcpClient, attachmentByteBuffer);
					
					ByteBuffer byteBuffer = ByteBuffer.allocate(512);
					socketChannel.read(byteBuffer, byteBuffer, this);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			@Override
			public void failed(Throwable exc, ByteBuffer attachmentByteBuffer) {
				try {
					String message = "클라이언트 통신 안됨: " + socketChannel.getRemoteAddress() + ": " + Thread.currentThread().getName();
					
					Platform.runLater(()->{
						System.out.println("[" + getClass().getSimpleName() + "] " + message);
						listContent.printMessage("[" + getClass().getSimpleName() + "] " + message);
					});
					
					List<TCPClient> connections = tcpServer.getConnectionList();
					connections.remove(TCPClient.this);
					
					socketChannel.close();
				} catch (IOException e) {
				}
			}
		});
	}
	
	public void sendByteBufffer(ByteBuffer byteBuffer) {
		System.out.println("TCP Client Write Start " + "buffer capacity: " + byteBuffer.capacity() + " , buffer lemaining: " + byteBuffer.remaining());
		socketChannel.write(byteBuffer, null, new CompletionHandler<Integer, Void>() {
			@Override
			public void completed(Integer result, Void attachmentVoid) {
				System.out.println("ClientSender, sendByteBuffer() completed()");
			}
			
			@Override
			public void failed(Throwable exc, Void attachmentVoid) {
				System.out.println("ClientSender, sendByteBuffer() failed()");
				try {
					String message = "클라이언트 통신 안됨: " + socketChannel.getRemoteAddress() + ": " + Thread.currentThread().getName();
					
					Platform.runLater(()->{
						System.out.println("[" + getClass().getSimpleName() + "] " + message);
						listContent.printMessage("[" + getClass().getSimpleName() + "] " + message);
					});
					
					List<TCPClient> connections = tcpServer.getConnectionList();
					connections.remove(TCPClient.this);
					
					socketChannel.close();
				} catch (IOException e) {}
			}
		});
	}
	
	public static int byteToInt(byte[] value) {
		return ((((int)value[0] & 0xff) << 24) | (((int)value[1] & 0xff) << 16) | (((int)value[2] & 0xff) << 8) | (((int)value[3] & 0xff)));
	}
	
	/*
	public void send(String data) {
		Charset charset = Charset.forName("utf-8");
		ByteBuffer byteBuffer = charset.encode(data);
		socketChannel.write(byteBuffer, null, new CompletionHandler<Integer, Void>() {
			@Override
			public void completed(Integer result, Void attachment) {
			}
			@Override
			public void failed(Throwable exc, Void attachment) {
				try {
					String message = "클라이언트 통신 안됨: " + socketChannel.getRemoteAddress() + ": " + Thread.currentThread().getName();
					
					Platform.runLater(()->{
						System.out.println("[" + getClass().getSimpleName() + "] " + message);
						listContent.printMessage("[" + getClass().getSimpleName() + "] " + message);
					});
					
					List<TCPClient> connections = tcpServer.getConnectionList();
					connections.remove(TCPClient.this);
					
					socketChannel.close();
				} catch (IOException e) {}
			}
		});
	}
	 */
} // class
