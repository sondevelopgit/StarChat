package kr.co.star.starchat.server;

import kr.co.star.starchat.server.message.recv.ChatRecvMessage;
import kr.co.star.starchat.server.message.recv.LoginRecvMessage;
import kr.co.star.starchat.server.tcp.TCPClient;

public interface MessageExecuteAcceptor {
	public void accept(LoginRecvMessage message, TCPClient tcpClient);
	public void accept(ChatRecvMessage message, TCPClient tcpClient);
}
