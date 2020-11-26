package special;


public class League {
	private String codLiga;
	private String nomLiga;
	
	public League(String codLiga, String nomLiga)
	{
		this.codLiga = codLiga;
		this.nomLiga = nomLiga;
	}
	
	public String get_codLiga()
	{
		return codLiga;
	}
	
	public String get_nomLiga()
	{
		return nomLiga;
	}
	
	@Override
	public String toString()
	{
		return get_nomLiga();
	}
	
	
}
