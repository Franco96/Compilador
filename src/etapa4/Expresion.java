package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Tipo;

public abstract class Expresion {
	
	protected Token operador;

	public Token getOperador() {
		return operador;
	}

	public void setOperador(Token operador) {
		this.operador = operador;
	}
	
	public int getLinea(){
		
		return operador.getNroLinea();
	}
	
	public String getNombre(){
		return operador.getLexema();
	}

	public abstract Tipo chequear(Clase clase,Metodo metodo) throws ErrorSemantico;
}
