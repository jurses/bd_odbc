package app;

import controller.*;
import java.sql.SQLException;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import special.Team;

public class Main extends Application {
	private Select_sgbd_controller sgbd_cont;
	private Choose_option_controller option_cont;
	private Insert_team_controller insert_team_cont;
	private Teams_table_controller teams_table_cont;
	private Conn_wrapper cw;

	public static void main(String[] args) throws SQLException {
		launch();
	}

	public Main() {
		sgbd_cont = new Select_sgbd_controller();
		option_cont = new Choose_option_controller();
		insert_team_cont = new Insert_team_controller();
		teams_table_cont = new Teams_table_controller();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Scene sc = new Scene(sgbd_cont.get_view());
		primaryStage.setScene(sc);

		sgbd_cont.get_bt_connect().setOnAction(event -> {
			cw = new Conn_wrapper(sgbd_cont.get_conn(), sgbd_cont.get_user(), sgbd_cont.get_pass(),
					sgbd_cont.get_port(), sgbd_cont.get_bdname(), sgbd_cont.get_selected_sgbd());

			primaryStage.setScene(new Scene(option_cont.get_view()));
		});
		
		/*
		teams_table_cont.get_bt_edit_team().setOnAction(event -> {
			Edit_team_controller etc = new Edit_team_controller();
			etc.getTf_team_name().setText(value);
			etc.getCb_leagues().setText(value);
			etc.getTf_team_name().setText(value);
			primaryStage.setScene(new Scene(etc.get_view()));
		});
		*/

		option_cont.get_bt_show().setOnAction(event -> {
			cw.show_teams();
		});

		option_cont.get_bt_insert_team().setOnAction(event -> {
			primaryStage.setScene(new Scene(insert_team_cont.get_view()));
			insert_team_cont.fill_cb_leagues(cw.get_leagues());
		});

		insert_team_cont.get_bt_submit().setOnAction(event -> {
			cw.insert_team(insert_team_cont.get_team_name(), insert_team_cont.get_codLeague(),
					insert_team_cont.get_location(), insert_team_cont.get_international());
		});
		
		option_cont.get_bt_delete_team().setOnAction(event -> {
			System.out.println("Vista de la tabla.");
			primaryStage.setScene(new Scene(teams_table_cont.get_view()));
			teams_table_cont.fill_table_with_teams(cw.get_teams(), cw.get_leagues());
		});
		
		teams_table_cont.get_bt_remove().setOnAction(event -> {
			Optional<Team> maybe_team = teams_table_cont.get_selected_team();
			if (maybe_team.isPresent())
				cw.remove_team(maybe_team.get().getCodEquipo().get());
			else
				System.out.println("No se seleccionó ningún equipo.");
		});

		primaryStage.show();
	}

}
