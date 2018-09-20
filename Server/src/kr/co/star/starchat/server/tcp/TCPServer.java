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

	/* ������ ���� */
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
	
	/* Ŀ�ؼ� ���� ��û�� ��ٸ��� */
	public void runSocketAccept() {
		Platform.runLater(()->{
			System.out.println("[" + getClass().getSimpleName() + "] " + "���� ����");
			listContent.printMessage("[" + getClass().getSimpleName() + "] " + "���� ����");
		});			
		
		serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
			@Override
			public void completed(AsynchronousSocketChannel socketChannel, Void attachment) {
				try {
					String message = "���� ����: " + socketChannel.getRemoteAddress()  + ": " + Thread.currentThread().getName();
					Platform.runLater(()->{
						System.out.println("[" + getClass().getSimpleName() + "] " + message);
						listContent.printMessage("[" + getClass().getSimpleName() + "] " + message);
					});
				} catch (IOException e) {
				}
				
				TCPClient client = new TCPClient(socketChannel);
				connections.add(client);
				
				Platform.runLater(()->{
					System.out.println("[" + getClass().getSimpleName() + "] " + "���� ����: " + connections.size());
					listContent.printMessage("[" + getClass().getSimpleName() + "] " + "���� ����: " + connections.size());
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
				System.out.println("[" + getClass().getSimpleName() + "] " + "���� ����");
				listContent.printMessage("[" + getClass().getSimpleName() + "] " + "���� ����");
			});
		} catch (Exception e) {}
	}	
	
	
} // class
