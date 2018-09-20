package kr.co.star.starchat.clientpc.message;

import java.io.IOException;
import java.nio.ByteBuffer;

import kr.co.star.starchat.clientpc.tcp.TCPClientSender;
import kr.co.star.starchat.clientpc.tcp.TCPClient;

public abstract class SendMessage extends BaseMessage {
	private byte[] sendBytes;
	
	public SendMessage(int messageType, int messageSize) {
		super(messageType, messageSize);
		sendBytes = new byte[messageSize + (clientIntSize * 2)];
	}
	
	protected void addHeader() throws IOException {		
		intToBytes(sendBytes, messageType, 0);
		intToBytes(sendBytes, messageSize, clientIntSize);
	}

	protected abstract void addBody(byte[] sendBytes);
	
	public static void intToBytes(byte[] sendBytes, int value, int copyIndex) throws IOException {
		byte[] intByte = new byte[4];
		intByte[0] = (byte)(value >> 24);
		intByte[1] = (byte)(value >> 16);
		intByte[2] = (byte)(value >> 8);
		intByte[3] = (byte)value;

		System.arraycopy(intByte, 0, sendBytes, copyIndex, clientIntSize);
	}
	
	public static void strToBytes(byte[] sendBytes, String value, int length, int copyIndex) throws IOException {
		byte[] stringByte = new byte[length];
		
		if(value.getBytes("utf-8").length < length) {
			System.arraycopy(value.getBytes("utf-8"), 0, stringByte, 0, value.getBytes("utf-8").length);

			for(int i = value.getBytes("utf-8").length; i < length; i++) {
				stringByte[i] = 0;
			}
		}
		else {
			System.arraycopy(value.getBytes("utf-8"), 0, stringByte, 0, length);
		}
		
		System.arraycopy(stringByte, 0, sendBytes, copyIndex, length);
	}
	
	public void sendMessage(TCPClientSender clientSender) {
		try {
			addHeader();
			addBody(sendBytes);
			writeByteBuffer(clientSender, sendBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeByteBuffer(TCPClientSender clientSender, byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		clientSender.sendByteBuffer(buffer);
	}

} // class
