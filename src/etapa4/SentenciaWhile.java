package etapa4;

public class SentenciaWhile extends Sentencia{

	private Expresion condicion;
	private Sentencia cuerpoWhile;
	
	
	public SentenciaWhile(Expresion condicion, Sentencia cuerpoWhile) {

		this.condicion = condicion;
		this.cuerpoWhile = cuerpoWhile;
	}
	
	
	
}
