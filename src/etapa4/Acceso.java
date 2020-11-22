package etapa4;

import etapa1.Token;

public abstract class Acceso extends Operando{
	
	
	private Encadenado encadenado;
	
	public Acceso(Token t){
		this.t = t;
	}

	public Encadenado getEncadenado() {
		return encadenado;
	}

	public void setEncadenado(Encadenado encadenado) {
		this.encadenado = encadenado;
	}
	
	

}
