package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;


public class Select_sgbd_controller {	
    @FXML
    private GridPane root;
    
    @FXML
    private TextField tf_port;

    @FXML
    private TextField tf_conn;

    @FXML
    private PasswordField pf_pass;

    @FXML
    private TextField tf_user;

    @FXML
    private Button bt_accept;

    @FXML
    private ToggleGroup selected_sgbd;
    
    @FXML
    private TextField tf_bdname;
    
    public Select_sgbd_controller()
    {
    	
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/connection.fxml"));
	    	loader.setController(this);
	    	loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public GridPane get_view()
    {
    	return root;
    }    
    
    public String get_bdname()
    {
    	return tf_bdname.getText();
    }
    
    public String get_selected_sgbd()
    {
    	RadioButton rb = (RadioButton) selected_sgbd.getSelectedToggle();
    	return rb.getText();
    }
    
    public String get_port()
    {
    	return tf_port.getText();
    }
    
    public String get_user()
    {
    	return tf_user.getText();
    }
    
    public String get_conn()
    {
    	return tf_conn.getText();
    }
    
    public String get_pass()
    {
    	return pf_pass.getText();
    }
    
    public Button get_bt_connect()
    {
    	return bt_accept;
    }
}