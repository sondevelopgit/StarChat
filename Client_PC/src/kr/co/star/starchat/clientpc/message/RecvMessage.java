package kr.co.star.starchat.clientpc.message;

import java.io.IOException;
import java.nio.ByteBuffer;
import kr.co.star.starchat.clientpc.MessageAcceptor;
import kr.co.star.starchat.clientpc.message.recv.ChatRecvMessage;
import kr.co.star.starchat.clientpc.message.recv.LoginRecvMessage;

public abstract class RecvMessage extends BaseMessage {
    public RecvMessage(int messageType, int messageSize) {
        super(messageType, messageSize);
    }

    public abstract void execute(MessageAcceptor acceptor);

    public static RecvMessage recvMessage(ByteBuffer buffer) throws IOException {    	
		//buffer.flip();
		buffer.position(0);
		buffer.limit(buffer.capacity());
		
		if(buffer.remaining() < (clientIntSize * 2)) {
			return null;
		}
		
		int messageType = readInt(buffer);
		int messageSize = readInt(buffer);
		
		//System.out.print("prev capacity: " + buffer.capacity() + " / ");
		//System.out.print("remaining: " + buffer.remaining() + " / ");
		//System.out.print("positon: " + buffer.position() + " / ");
		//System.out.println("limit: " + buffer.limit());
		
		//buffer.compact();
		//buffer.flip();
		buffer.limit((clientIntSize * 2) + messageSize);

		if(buffer.remaining() < messageSize) {
			return null;
		}
		
		switch(messageType) {
			case loginType:
				return new LoginRecvMessage(buffer, messageSize);
				
			case getUsersListType:
				//return new GetUsersListRecvMessage(serverConnection, messageSize);
				return null;
				
			case chatType:
				return new ChatRecvMessage(buffer, messageSize);
				
			default:
				//return new UnkownedRecvMessage(serverConnection, messageType, messageSize);	
				return null;
		}
	}

    protected static String readString(ByteBuffer buffer, int length) throws IOException {
		byte[] tempBytes = new byte[length];
		buffer.get(tempBytes);
		return new String(tempBytes, charset);
	}
	
	public static int readInt(ByteBuffer buffer) throws IOException {		
		int value = buffer.getInt();		
		return value;
	}

}