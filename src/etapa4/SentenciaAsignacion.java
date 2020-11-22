package etapa4;

import etapa1.Token;

public class SentenciaAsignacion extends Sentencia{

	private Token tipoAsignacio;
	private Acceso ladoIzq;
	private Expresion ladoDer;
	
	
	
	public SentenciaAsignacion(Token tipoAsignacio, Acceso ladoIzq, Expresion ladoDer) {
		this.tipoAsignacio = tipoAsignacio;
		this.ladoIzq = ladoIzq;
		this.ladoDer = ladoDer;
	}
	
	
	
	
}
