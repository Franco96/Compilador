package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Tipo;

public class AccesoParentizado extends Acceso{
	
	private Expresion expresion;

	public AccesoParentizado(Token t, Expresion expresion) {
		super(t);
		this.expresion = expresion;
	}

	@Override
	public Tipo chequear(Clase clase, Metodo metodo) throws ErrorSemantico {
		
		return expresion.chequear(clase, metodo);
		
	}

	
}
