package kr.co.star.starchat.server.sending;

import java.util.ArrayDeque;
import java.util.Queue;
import kr.co.star.starchat.server.message.SendMessage;
import kr.co.star.starchat.server.tcp.TCPClient;

public class SendingThread extends Thread {
	
	private TCPClient tcpClient;
	private Queue<SendMessage> sendMessageQueue = new ArrayDeque<>();
	private boolean isRunning = true;
	
	SendingThread(TCPClient tcpClient) {
		this.tcpClient = tcpClient;
	}
	
	void putMessage(SendMessage message) {
		synchronized(sendMessageQueue) {
			sendMessageQueue.offer(message);
		}
	}
	
	private SendMessage getMessage(){
		SendMessage message;
		synchronized(sendMessageQueue){
			message = sendMessageQueue.poll();
		}
		return message;
	}

	@Override
	public void run() {
		while(isRunning) {
			try {
				SendMessage message = getMessage();
				if(message != null) {
					message.sendMessage(tcpClient);
				}
				else {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	void finish(){
		isRunning = false;
	}
}
