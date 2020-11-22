package etapa3;

import etapa1.Token;

public class Parametro extends Var {
	
	private int indice;
	
	public Parametro(Token k,Tipo tipo, int indice){
		super(k,tipo);
		this.indice=indice;
	}

	public int getIndice(){
		return indice;
	}
	
	

}
