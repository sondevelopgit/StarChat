package kr.co.star.starchat.clientpc.app.viewmodel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class FriendsListItemViewCell extends ListCell<FriendsListItem> {
	private FXMLLoader mLLoader;
	
	@FXML private Label txvTitle;
    @FXML private Label txvName;
    @FXML private ImageView imvProfile;
    @FXML private AnchorPane anchorPane;

    @Override
    protected void updateItem(FriendsListItem item, boolean empty) {
        super.updateItem(item, empty);
        
        if(empty || item == null) {
            setText(null);
            setGraphic(null);
        }
        else {
        	if(mLLoader == null) {
        		if(item.getIsUser() == true) {
        			mLLoader = new FXMLLoader(getClass().getResource("../../../../../../../resources/layout/ItemFriendsListUser.fxml"));
        		}
        		else {
        			mLLoader = new FXMLLoader(getClass().getResource("../../../../../../../resources/layout/ItemFriendsListDotted.fxml"));
        		}
        		mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        	
        	if(item.getIsUser() == true) {
        		txvName.setText(String.valueOf(item.getName()));
            	FileInputStream input = null;
            	
            	try {
    				input = new FileInputStream(this.getClass().getResource("../../../../../../../resources/drawable/user.png").getPath());
    			} catch (FileNotFoundException e) {
    				e.printStackTrace();
    			}
            	imvProfile.setImage(new Image(input));
        	}
        	else {
        		txvTitle.setText(String.valueOf(item.getName()));
        	}
        	
        	setText(null);
            setGraphic(anchorPane);
        }
        //xvName.setText(item.getName().toString());
        //txvName.setText(String.valueOf(item.getName()));
        
        
        
    }
    
}
