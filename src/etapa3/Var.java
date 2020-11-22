package etapa3;

import etapa1.Token;

public abstract class Var {
	protected Tipo tipo;
	protected Token k;


	protected Var(Token k, Tipo tipo) {
		this.k = k;
		this.tipo = tipo;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public String getNombre() {
		return k.getLexema();
	}
	
	public int getLinea(){
		return k.getNroLinea();
	}


}
