package app;

import java.sql.CallableStatement;
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

public class Conn_wrapper {
	protected Connection conn;
	private int sql_mode;
	public static final int MYSQL = 1;
	public static final int ACCESS = 2;
	public static final int MSQL = 3;

	public Conn_wrapper(String url, String user, String pass, String port, String bdname, String sgbd) {
		String jdbc_url = "jdbc:";
		if (sgbd.equals("MySQL")) {
			jdbc_url += "mysql://" + url;
			jdbc_url += ":" + port + "/" + bdname;
			sql_mode = MYSQL;
		} else if (sgbd.equals("Access")) {
			
			jdbc_url += "odbc://" + url;
			sql_mode = ACCESS;
		} else if (sgbd.equals("Microsoft SQL")) {
			jdbc_url += "sqlserver://" + url + ":" + port + ";databaseName=" + bdname + ";user=" + user + ";password=" + pass;
			sql_mode = MSQL;
		}		

		System.out.println(jdbc_url);
		try {
			conn = DriverManager.getConnection(jdbc_url, user, pass);
		} catch (SQLException sql_exception) {
			sql_exception.printStackTrace();
		}
	}
	
	public void modify_team(int codEquipo, String team_name, String league_code, String location, Boolean international)
	{
		try
		{
			PreparedStatement stmnt = null;
			stmnt = conn.prepareStatement("UPDATE equipos SET nomEquipo=?, codLiga=?, localidad=?, internacional=? where codEquipo=?");
			stmnt.execute();
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void alert_result(int equipo_insertado, int liga_existe, String team_name, String codLeague) {
		if (equipo_insertado == 1) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Equipo insertado");
			alert.setHeaderText("Equipo: " + team_name + " insertado");
			alert.setContentText("Equipo insertado con éxito.");
			alert.show();
		} else {
			if (liga_existe == 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Equipo no insertado");
				alert.setHeaderText("Liga no existe");
				alert.setContentText("Código de liga: " + codLeague + " no existe");
				alert.show();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Equipo no insertado");
				alert.setHeaderText("Error desconocido");
				alert.setContentText(
						"No se pudo insertar el equipo: " + team_name + "con código de liga: " + codLeague);
				alert.show();
			}
		}
	}

	public void remove_team(int codEquipo) {
		try {
			PreparedStatement stmnt = null;
			if (sql_mode == MYSQL)
				stmnt = conn.prepareStatement("SELECT COUNT(codContrato) FROM contratos WHERE codEquipo=?");
			else if (sql_mode == MSQL)
				stmnt = conn.prepareStatement("SELECT COUNT(codContrato) FROM contratos WHERE codEquipo=?");
			stmnt.setInt(1, codEquipo);
			ResultSet set = stmnt.executeQuery();
			stmnt = null;
			set.next();
			int num_contratos = set.getInt(1);
			if (num_contratos > 0) {
				boolean toErase = false;
				if (sql_mode == MYSQL)
					stmnt = conn.prepareStatement("SELECT codContrato FROM contratos WHERE codEquipo=?");
				else if (sql_mode == MSQL)
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
					if (sql_mode == MYSQL)
						stmnt = conn.prepareStatement("DELETE FROM contratos WHERE codContrato=?");
					else if (sql_mode == MSQL)
						stmnt = conn.prepareStatement("DELETE FROM contratos WHERE codContrato=?");
					stmnt.setInt(1, set.getInt(1));
					stmnt.execute();
					stmnt = null;
				}
			}
			if (sql_mode == MYSQL)
				stmnt = conn.prepareStatement("DELETE FROM equipos WHERE codEquipo=?");
			else if (sql_mode == MSQL)
				stmnt = conn.prepareStatement("DELETE FROM equipos WHERE codEquipo=?");
			stmnt.setInt(1, codEquipo);
			stmnt.execute();
			stmnt = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Team> get_teams() {
		ArrayList<Team> al = new ArrayList<Team>();
		try {
			PreparedStatement stmnt = null;
			if (sql_mode == MYSQL)
				stmnt = conn.prepareStatement("SELECT * FROM equipos;");
			else if (sql_mode == MSQL)
				stmnt = conn.prepareStatement("SELECT * FROM equipos;");
			ResultSet set = stmnt.executeQuery();
			stmnt = null;
			while (set.next()) {
				if (sql_mode == MYSQL)
					stmnt = conn.prepareStatement("SELECT nomLiga FROM ligas WHERE codLiga=?");
				else if (sql_mode == MSQL)
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
			if (sql_mode == MYSQL) {
				stmnt = conn.prepareStatement("{CALL SP_INSERTAR_EQUIPO(?, ?, ?, ?, @liga_existe, @equipo_insertado)}");
				stmnt.setString(1, team_name);
				stmnt.setString(2, codLeague);
				stmnt.setString(3, location);
				stmnt.setInt(4, international);
				stmnt.execute();
				stmnt = null;
			} else if (sql_mode == MSQL) {
				CallableStatement stmnts = null;
				stmnts = conn.prepareCall("{call dbo.SP_insertar_equipo(?, ?, ?, ?, ?, ?)}");
				stmnts.registerOutParameter(5, java.sql.Types.INTEGER);
				stmnts.registerOutParameter(6, java.sql.Types.INTEGER);
				stmnts.setString(1, team_name);
				stmnts.setString(2, codLeague);
				stmnts.setString(3, location);
				stmnts.setInt(4, international);
				stmnts.execute();
				liga_existe = stmnts.getInt(5);
				equipo_insertado = stmnts.getInt(6);
				
				System.out.println(liga_existe);
				System.out.println(equipo_insertado);
			}

			if (sql_mode == MYSQL) {
				stmnt = conn.prepareStatement("SELECT @liga_existe, @equipo_insertado");
				ResultSet set = stmnt.executeQuery();
				set.next();
				stmnt = null;
				liga_existe = set.getInt(1);
				equipo_insertado = set.getInt(2);
			}

			if (sql_mode != ACCESS) {
				alert_result(equipo_insertado, liga_existe, team_name, codLeague);
			}
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
			if (sql_mode == MYSQL)
				stmnt = conn.prepareStatement("SELECT * FROM ligas;");
			else if (sql_mode == MSQL)
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
			if (sql_mode == MYSQL)
				stmnt = conn.prepareStatement("SELECT * FROM equipos;");
			else if (sql_mode == MSQL)
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
