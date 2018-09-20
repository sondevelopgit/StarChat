package kr.co.star.starchat.server.content;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class ListContent {
	private ListView<String> listView1;
	private ObservableList<String> items;
	
	private static ListContent instance = new ListContent();
	
	private ListContent() {
	}
	
	public static ListContent getInstance() {
		return instance;
	}
	
	public void setListView(ListView<String> listview) {
		listView1 = listview;
	}
	
	public void setObservableList(ObservableList<String> item) {
		items = item;
	}
	
	public void printMessage(String message) {
		items.add(message);
		
		
		//listView1.notify();
		//items.notify();
		//items.notifyAll();
		//listView1.notify();
		//listView1.notifyAll();
	}
	
	public ListView<String> getListView() {
		return listView1;
	}
	
}
