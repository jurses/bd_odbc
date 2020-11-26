package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Edit_team_controller {

    @FXML
    private VBox root;

    @FXML
    private TextField tf_team_name;

    @FXML
    private ComboBox<?> cb_leagues;

    @FXML
    private TextField tf_location;

    @FXML
    private CheckBox chb_int;

    @FXML
    private Button bt_submit;
    
    public Edit_team_controller()
    {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/modify_team.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public VBox get_view() {
		return root;
	}

	public TextField getTf_team_name() {
		return tf_team_name;
	}

	public ComboBox<?> getCb_leagues() {
		return cb_leagues;
	}

	public TextField getTf_location() {
		return tf_location;
	}

	public CheckBox getChb_int() {
		return chb_int;
	}

	public Button getBt_submit() {
		return bt_submit;
	}

	public void setTf_team_name(TextField tf_team_name) {
		this.tf_team_name = tf_team_name;
	}

	public void setCb_leagues(ComboBox<?> cb_leagues) {
		this.cb_leagues = cb_leagues;
	}

	public void setTf_location(TextField tf_location) {
		this.tf_location = tf_location;
	}

	public void setChb_int(CheckBox chb_int) {
		this.chb_int = chb_int;
	}

	public void setBt_submit(Button bt_submit) {
		this.bt_submit = bt_submit;
	}

}
