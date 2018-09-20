package kr.co.star.starchat.server.sending;

import java.util.Enumeration;
import java.util.Hashtable;

import kr.co.star.starchat.server.message.SendMessage;
import kr.co.star.starchat.server.tcp.TCPClient;

public class SendingThreadManager {
	
	private static SendingThreadManager instance = new SendingThreadManager();
	
	private Hashtable<TCPClient, SendingThread> tcpClientHashtable =
			new Hashtable<> ();
	
	private SendingThreadManager() {
	}
	
	public static SendingThreadManager getInstance() {
		return instance;
	}

	private SendingThread getSendingThread(TCPClient tcpClient) {
		SendingThread sendingThread = tcpClientHashtable.get(tcpClient);
		
		if(sendingThread == null) {
			sendingThread = new SendingThread(tcpClient);
			tcpClientHashtable.put(tcpClient, sendingThread);
			sendingThread.start();
			return sendingThread;
		}
		else {
			return sendingThread;
		}
	}
	
	public void sendingMessage(TCPClient tcpClient, SendMessage sendMessage){
		SendingThread sendingThread = getSendingThread(tcpClient);
		sendingThread.putMessage(sendMessage);
	}
	
	public void removeSendingThread(TCPClient tcpClient){
		SendingThread sendingThread = tcpClientHashtable.get(tcpClient);
		sendingThread.finish();
		tcpClientHashtable.remove(tcpClient);
	}
}
