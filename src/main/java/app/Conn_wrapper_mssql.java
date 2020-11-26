package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import special.League;
import special.Team;

public class Conn_wrapper_mssql extends Conn_wrapper {

	public Conn_wrapper_mssql(String url, String user, String pass, String port, String bdname, String sgbd) {
		super(url, user, pass, port, bdname, sgbd);
		// TODO Auto-generated constructor stub
	}

	public void modify_team(int codEquipo, )
	{
		try
		{
			PreparedStatement stmnt = null;
			if (sql_mode == MYSQL)
				stmnt = conn.prepareStatement("SELECT COUNT(codContrato) FROM contratos WHERE codEquipo=?");
			else if (sql_mode == MSQL)
				stmnt = conn.prepareStatement("SELECT COUNT(codContrato) FROM contratos WHERE codEquipo=?");
		} catch()
	}

	public void remove_team(int codEquipo) {
		try {
			PreparedStatement stmnt = null;
			stmnt = conn.prepareStatement("SELECT COUNT(codContrato) FROM contratos WHERE codEquipo=?");
			stmnt.setInt(1, codEquipo);
			ResultSet set = stmnt.executeQuery();
			stmnt = null;
			set.next();
			int num_contratos = set.getInt(1);
			if (num_contratos > 0) {
				boolean toErase = false;
				stmnt = conn.prepareStatement("SELECT codContrato FROM contratos WHERE codEquipo=?");
				set = stmnt.executeQuery();
				stmnt = null;
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Este equipo tiene contratos");
				alert.setHeaderText("Este equipo tiene contratos asociados");
				alert.setContentText("¿Quiere borrarlos?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.isPresent())
					toErase = (result.get() == ButtonType.OK);

				while (set.next() && toErase) {
					stmnt = conn.prepareStatement("DELETE FROM contratos WHERE codContrato=?");
					stmnt.setInt(1, set.getInt(1));
					stmnt.execute();
					stmnt = null;
				}
			}
			stmnt = conn.prepareStatement("DELETE FROM equipos WHERE codEquipo=?");
			stmnt.setInt(1, codEquipo);
			stmnt.execute();
			stmnt = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Team get_team(int codTeam)
	{
		try
		{
			PreparedStatement stmnt = null;
			
			stmnt = conn.prepareStatement("SELECT * FROM equipos WHERE codEquipo=?");
			stmnt.setString(1, Integer.toString(codTeam));
			ResultSet set = stmnt.executeQuery();
			
			
			int codEquipo= set.getInt(1);;
			String nomEquipo = set.getString(2);
			League liga get_league(set.getInt(3));
			String localidad;
			Boolean internacional;
			
			return new Team();
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public League get_league(int codLiga)
	{
		try
		{
			PreparedStatement stmnt = null;
			
			stmnt = conn.prepareStatement("SELECT * FROM ligas WHERE codEquipo=?");
			stmnt.setString(1, Integer.toString(codLiga));
			ResultSet set = stmnt.executeQuery();
			
			
			int codEquipo= set.getInt(1);;
			String nomEquipo = set.getString(2);
			League liga get_league(set.getInt(3));
			String localidad;
			Boolean internacional;
			
			return new Team();
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public ArrayList<Team> get_teams() {
		ArrayList<Team> al = new ArrayList<Team>();
		try {
			PreparedStatement stmnt = null;
			stmnt = conn.prepareStatement("SELECT * FROM equipos;");
			ResultSet set = stmnt.executeQuery();
			stmnt = null;
			while (set.next()) {
				stmnt = conn.prepareStatement("SELECT nomLiga FROM ligas WHERE codLiga=?");
				stmnt.setString(1, set.getString("codLiga"));
				ResultSet resultliga = stmnt.executeQuery();
				stmnt = null;
				resultliga.next();

				int codEquipo = set.getInt("codEquipo");
				String nomEquipo = set.getString("nomEquipo");
				League league = new League(set.getString("codLiga"), resultliga.getString("nomLiga"));
				String localidad = set.getString("localidad");
				Boolean internacional = set.getBoolean("internacional");
				Team t = new Team(codEquipo, nomEquipo, league, localidad, internacional);
				al.add(t);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
	}

	public void insert_team(String team_name, String codLeague, String location, int international) {
		try {
			PreparedStatement stmnt = null;
			int liga_existe = 0;
			int equipo_insertado = 0;
			stmnt = conn.prepareStatement("{CALL SP_INSERTAR_EQUIPO(?, ?, ?, ?, @liga_existe, @equipo_insertado)}");
			stmnt.setString(1, team_name);
			stmnt.setString(2, codLeague);
			stmnt.setString(3, location);
			stmnt.setInt(4, international);
			stmnt.execute();
			stmnt = null;

			stmnt = conn.prepareStatement("SELECT @liga_existe, @equipo_insertado");
			ResultSet set = stmnt.executeQuery();
			set.next();
			stmnt = null;
			liga_existe = set.getInt(1);
			equipo_insertado = set.getInt(2);

			super.alert_result(equipo_insertado, liga_existe, team_name, codLeague);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public ArrayList<League> get_leagues() {
		ArrayList<League> alpss = new ArrayList<League>();
		try {
			PreparedStatement stmnt = null;
			stmnt = conn.prepareStatement("SELECT * FROM ligas;");
			ResultSet set = stmnt.executeQuery();
			stmnt = null;
			while (set.next()) {
				String codLiga = set.getString("codLiga");
				String nomLiga = set.getString("nomLiga");
				League n_par = new League(codLiga, nomLiga);
				alpss.add(n_par);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alpss;
	}

	public void show_teams() {
		try {
			PreparedStatement stmnt = null;
			stmnt = conn.prepareStatement("SELECT * FROM equipos;");
			ResultSet set = stmnt.executeQuery();
			stmnt = null;
			while (set.next()) {
				int codEquipo = set.getInt("codEquipo");
				String nomEquipo = set.getString("nomEquipo");
				String codLiga = set.getString("codLiga");
				String localidad = set.getString("localidad");
				int internacional = set.getInt("internacional");
				System.out.println(String.join(", ", Integer.toString(codEquipo), nomEquipo, codLiga, localidad,
						Integer.toString(internacional)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
