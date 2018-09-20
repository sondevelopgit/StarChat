package kr.co.star.starchat.server.main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import kr.co.star.starchat.server.content.ListContent;
import kr.co.star.starchat.server.tcp.TCPServer;

public class MainController implements Initializable {
	private TCPServer tcpServer = TCPServer.getInstance();
	private ListContent listContent = ListContent.getInstance();
	
	private ObservableList<String> listItems;
	@FXML private ListView<String> listviewContent;
    @FXML private Button ButtonServerOn;
    @FXML private Button ButtonServerOff;
	
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if(event.getTarget() == ButtonServerOn) {
        	System.out.println("server_on");
        	tcpServer.openSocketChannel();
        	tcpServer.runSocketAccept();
        	listContent.printMessage("Server On");
        }
        else if(event.getTarget() == ButtonServerOff) {
        	System.out.println("server_off");
        	tcpServer.shuttdownSocketChannel();
        	listContent.printMessage("Server Off");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    	
    	listItems = listviewContent.getItems();
		listContent.setListView(listviewContent);
		listContent.setObservableList(listItems);
    }
    
}
