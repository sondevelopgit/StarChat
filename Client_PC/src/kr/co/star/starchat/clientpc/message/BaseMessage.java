package kr.co.star.starchat.clientpc.message;

import java.nio.charset.Charset;

public abstract class BaseMessage {
	protected int messageType;
	protected int messageSize;
	
	public static final Charset charset = Charset.forName("utf-8");
	
	public static final int clientIntSize = 4;
	public static final int userIDSize = 50;
	public static final int userPWSize = 64;
	
	public static final int base = 1000;
	public static final int loginType = base + 1;
	public static final int getUsersListType = base + 2;
	public static final int chatType = base + 3;
	
	public BaseMessage(int messageType, int messageSize){
		this.messageType = messageType;
		this.messageSize = messageSize;
	}
}
