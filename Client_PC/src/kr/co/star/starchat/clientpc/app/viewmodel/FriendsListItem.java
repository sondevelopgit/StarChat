package kr.co.star.starchat.clientpc.app.viewmodel;

import javafx.scene.image.Image;

public class FriendsListItem {
	private boolean isUser;
	private int itemIndex;
	private String name;
	private Image image;
	
	public FriendsListItem(String name, boolean isUser) {
		this.name = name;
		this.isUser = isUser;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getIsUser() {
		return isUser;
	}
}
