package kr.co.star.starchat.server.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import kr.co.star.starchat.server.content.ListContent;

public class TCPServer {
	private static TCPServer instance = new TCPServer();

	private AsynchronousChannelGroup channelGroup;
	private AsynchronousServerSocketChannel serverSocketChannel;
	
	private List<TCPClient> connections = new Vector<>();
	
	private ListContent listContent = ListContent.getInstance();
		
	private TCPServer() {
	}
	
	public static TCPServer getInstance() {
		return instance;
	}
	
	public List<TCPClient> getConnectionList() {
		return connections;
	}

	/* 소켓을 연다 */
	public void openSocketChannel() {
		try {
			channelGroup = AsynchronousChannelGroup.withFixedThreadPool(
				Runtime.getRuntime().availableProcessors(),
				Executors.defaultThreadFactory()
			);
			serverSocketChannel = AsynchronousServerSocketChannel.open(channelGroup);
			serverSocketChannel.bind(new InetSocketAddress(5001));	
		} catch (IOException e) {
			if(serverSocketChannel.isOpen()) {
				shuttdownSocketChannel();
			}
			e.printStackTrace();
		}
	}
	
	/* 커넥션 연결 요청을 기다린다 */
	public void runSocketAccept() {
		Platform.runLater(()->{
			System.out.println("[" + getClass().getSimpleName() + "] " + "서버 시작");
			listContent.printMessage("[" + getClass().getSimpleName() + "] " + "서버 시작");
		});			
		
		serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
			@Override
			public void completed(AsynchronousSocketChannel socketChannel, Void attachment) {
				try {
					String message = "연결 수락: " + socketChannel.getRemoteAddress()  + ": " + Thread.currentThread().getName();
					Platform.runLater(()->{
						System.out.println("[" + getClass().getSimpleName() + "] " + message);
						listContent.printMessage("[" + getClass().getSimpleName() + "] " + message);
					});
				} catch (IOException e) {
				}
				
				TCPClient client = new TCPClient(socketChannel);
				connections.add(client);
				
				Platform.runLater(()->{
					System.out.println("[" + getClass().getSimpleName() + "] " + "연결 개수: " + connections.size());
					listContent.printMessage("[" + getClass().getSimpleName() + "] " + "연결 개수: " + connections.size());
				});
				
				serverSocketChannel.accept(null, this);
			}
			@Override
			public void failed(Throwable exc, Void attachment) {
				if(serverSocketChannel.isOpen()) {
					shuttdownSocketChannel();
				}
			}
		});
	}
	
	public void shuttdownSocketChannel() {
		System.out.println("stopServer");
		try {
			connections.clear();
			if(channelGroup != null && !channelGroup.isShutdown()) {
				channelGroup.shutdownNow();
			}
			Platform.runLater(()->{
				System.out.println("[" + getClass().getSimpleName() + "] " + "서버 멈춤");
				listContent.printMessage("[" + getClass().getSimpleName() + "] " + "서버 멈춤");
			});
		} catch (Exception e) {}
	}	
	
	
} // class
