package etapa4;


import etapa1.Token;


public abstract class Operando extends Expresion{
	
	protected Token t;
	
	
	public String getNombre(){
		return t.getLexema();
	}
	
	public int getLinea(){
		return t.getNroLinea();
	}
	
	


}
