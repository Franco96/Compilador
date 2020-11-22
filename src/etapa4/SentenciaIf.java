package etapa4;

public class SentenciaIf extends Sentencia{

private Expresion condicion;
private Sentencia cuerpoIf;
private Sentencia cuerpoElse;


public SentenciaIf(Expresion condicion, Sentencia cuerpoIf, Sentencia cuerpoElse) {
	this.condicion = condicion;
	this.cuerpoIf = cuerpoIf;
	this.cuerpoElse = cuerpoElse;
}
	
	

	
}
