package etapa4;

import etapa1.Token;

public abstract class Expresion {
	
	protected Token operador;

	public Token getOperador() {
		return operador;
	}

	public void setOperador(Token operador) {
		this.operador = operador;
	}

	//public abstract TipoMetodo check() throws Exception;
}
