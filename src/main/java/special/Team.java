package special;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Team {
	private IntegerProperty codEquipo;
	private StringProperty nomEquipo;
	private StringProperty localidad;
	private BooleanProperty internacional;
	private ObjectProperty<League> liga;
	
	public Team(int codEquipo, String nomEquipo, League liga, String localidad, Boolean internacional)
	{
		this.nomEquipo = new SimpleStringProperty(nomEquipo);
		this.codEquipo = new SimpleIntegerProperty(codEquipo);
		this.localidad = new SimpleStringProperty(localidad);
		this.liga = new SimpleObjectProperty<League>(liga);
	}
	
	public ObjectProperty<League> getLiga()
	{
		return liga;
	}
	
	public StringProperty getLocalidad()
	{
		return localidad;
	}


	public IntegerProperty getCodEquipo() {
		return codEquipo;
	}
	
	public StringProperty getNomEquipo() {
		return nomEquipo;
	}
	
	@Override
	public String toString()
	{
		return nomEquipo.get();
	}
	
	public BooleanProperty getInternational()
	{
		return internacional;
	}
}
