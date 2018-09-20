package kr.co.star.starchat.clientpc.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;

public class TCPClient {
	private static TCPClient instance = new TCPClient();

	private String host = "";
	private int port = -1;
	private InetSocketAddress adress;
	
	private AsynchronousChannelGroup channelGroup;
	private AsynchronousSocketChannel socketChannel;
	
	private boolean isConnection = false;
	private int request = 0;
	
	private TCPClient() {
	}
	
	public static TCPClient getInstance() {
		return instance;
	}
	
	public void setHostPort(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	/* 소켓을 연다 */
	public void openSocketChannel() {
		try {
			channelGroup = AsynchronousChannelGroup.withFixedThreadPool(
					Runtime.getRuntime().availableProcessors(),
					Executors.defaultThreadFactory()
			);
			
			socketChannel = AsynchronousSocketChannel.open(channelGroup);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* 커넥션 연결을 요청한다 */
	public void connectSocketChannel() {		
		adress = new InetSocketAddress("localhost", 5001);

		socketChannel.connect(adress, null, new CompletionHandler<Void, Void>() {
			@Override
			public void completed(Void result, Void attachment) {
				//socketChannel.getRemoteAddress()
				System.out.println("connectSocketChannel: complate()");
				isConnection = true;
				
				TCPClientRecver clientRecver = TCPClientRecver.getInstance();
				clientRecver.setSocketChannel(socketChannel);
				clientRecver.recvByteBuffer();
				
				TCPClientSender clientSender = TCPClientSender.getInstance();
				clientSender.setSocketChannel(socketChannel);
			}
			@Override
			public void failed(Throwable e, Void attachment) {
				System.out.println("connectSocketChannel: failed()");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				if(request++ < 10) {
					socketChannel.connect(adress, null, this);
				}
				else {
					System.out.println("Because there is no Internet connection, communication is terminated.");
					shuttdownSocketChannel();
				}
			}
		});
	}
	
	/* 소켓을 끈다 */
	public void shuttdownSocketChannel() {
		isConnection = false;
		if(socketChannel.isOpen()) {
			if(channelGroup != null && !channelGroup.isShutdown()) {
				try {
					channelGroup.shutdownNow();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public AsynchronousChannelGroup getChannelGroup() {
		return channelGroup;
	}
	
	public AsynchronousSocketChannel getSocketChannel() {
		return socketChannel;
	}
	
}
