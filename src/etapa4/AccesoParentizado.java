package etapa4;

import etapa1.Token;

public class AccesoParentizado extends Acceso{
	
	private Expresion expresion;

	public AccesoParentizado(Token t, Expresion expresion) {
		super(t);
		this.expresion = expresion;
	}

}
