package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Choose_option_controller {

    @FXML
    private GridPane root;

    @FXML
    private Button bt_insert_team;

    @FXML
    private Button bt_delete_team;
    
    @FXML
    private Button bt_show;

    @FXML
    private Button bt_modify_team;
    
    public Button get_bt_delete_team()
    {
    	return bt_delete_team;
    }
    
    public Button get_bt_insert_team()
    {
    	return bt_insert_team;
    }
    
    public Button get_bt_show()
    {
    	return bt_show;
    }
    
    public Button get_bt_edit_team()
    {
    	return bt_modify_team;
    }
    
    @FXML
    public GridPane get_view()
    {
    	return root;
    }

    public Choose_option_controller()
    {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
	    	loader.setController(this);
	    	loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
