package kr.co.star.starchat.clientpc;

import kr.co.star.starchat.clientpc.message.recv.ChatRecvMessage;
import kr.co.star.starchat.clientpc.message.recv.LoginRecvMessage;

public interface MessageAcceptor {
	public void accept(LoginRecvMessage message);
	public void accept(ChatRecvMessage message);
}
