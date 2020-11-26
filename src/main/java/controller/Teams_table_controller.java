package controller;

import special.Team;

import java.awt.TextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import special.League;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;

public class Teams_table_controller {
	@FXML
	private TableColumn<Team, Number> tvc_codEquipo;

	@FXML
	private TableColumn<Team, String> tvc_nomEquipo;

	@FXML
	private TableColumn<Team, League> tvc_liga;

	@FXML
	private TableColumn<Team, String> tvc_localidad;

	@FXML
	private TableColumn<Team, Boolean> tvc_internacional;

	public Teams_table_controller() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view_team.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private VBox root;

	@FXML
	private TableView<Team> tv_tabledb;

	public void fill_table_with_teams(ArrayList<Team> teams, ArrayList<League> leagues) {
		ListProperty<Team> lp_teams = new SimpleListProperty<Team>(FXCollections.observableArrayList(teams));

		tv_tabledb.itemsProperty().bind(lp_teams);

		tvc_nomEquipo.setCellValueFactory(v -> v.getValue().getNomEquipo());
		tvc_localidad.setCellValueFactory(v -> v.getValue().getLocalidad());
		tvc_codEquipo.setCellValueFactory(v -> v.getValue().getCodEquipo());
		tvc_internacional.setCellValueFactory(v -> v.getValue().getInternational());
		tvc_liga.setCellValueFactory(v -> v.getValue().getLiga());
		
		tvc_localidad.setCellFactory(TextFieldTableCell.forTableColumn());
		//tvc_codEquipo.setCellFactory(TextFieldTableCell.forTableColumn());
		tvc_liga.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(leagues)));
		tvc_internacional.setCellFactory(CheckBoxTableCell.forTableColumn(tvc_internacional));
	}

	@FXML
	private Button bt_remove;
	
	@FXML
	private Button bt_edit_team;
	
	public Button get_bt_edit_team()
	{
		return bt_edit_team;
	}

	public Button get_bt_remove() {
		return bt_remove;
	}

	public Optional<Team> get_selected_team() {
		Team t = tv_tabledb.getSelectionModel().getSelectedItem();
		return Optional.of(t);
	}

	public VBox get_view() {
		return root;
	}
}
