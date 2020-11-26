package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import special.League;

public class Insert_team_controller
{
	public Insert_team_controller() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/insert_team.fxml"));
	    	loader.setController(this);
	    	loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Button get_bt_submit()
	{
		return bt_submit;
	}
	
	public String get_team_name()
	{
		return tf_team_name.getText();
	}
	
	public String get_location()
	{
		return tf_location.getText();
	}
	
	public String get_codLeague()
	{
		return cb_leagues.getSelectionModel().getSelectedItem().get_codLiga();
	}
	
	public int get_international()
	{
		return chb_int.isSelected() ? 1 : 0;
	}
	
	public void fill_cb_leagues(ArrayList<League> alpss)
    {
		for(League o : alpss)
			cb_leagues.getItems().add(o);
		
		cb_leagues.getSelectionModel().select(0);
    }

    @FXML
    private TextField tf_team_name;

    @FXML
    private ComboBox<League> cb_leagues;

    @FXML
    private TextField tf_location;

    @FXML
    private CheckBox chb_int;

    @FXML
    private Button bt_submit;

    @FXML
    private VBox root;
    
    public VBox get_view()
    {
    	return root;
    }
}