package kr.co.star.starchat.server;

import java.io.PrintStream;

import kr.co.star.starchat.server.message.recv.ChatRecvMessage;
import kr.co.star.starchat.server.message.recv.LoginRecvMessage;
import kr.co.star.starchat.server.tcp.TCPClient;

public class PrintAcceptor implements MessageExecuteAcceptor {
	
	private PrintStream out;
	
	public PrintAcceptor(PrintStream out) {
		this.out = out;
	}

	@Override
	public void accept(LoginRecvMessage message, TCPClient tcpClient) {
		System.out.println("[PrintDebug] printAcceptor");
	}

	@Override
	public void accept(ChatRecvMessage message, TCPClient tcpClient) {
		System.out.println("[PrintDebug] chatAcceptor");
		
	}


}
