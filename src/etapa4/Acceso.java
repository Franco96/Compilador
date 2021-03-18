package etapa4;

import etapa1.Token;


public abstract class Acceso extends Operando{
	
	
	protected Encadenado encadenado;
	
	protected boolean esLadoIzquierdo;
	
	public Acceso(Token t){
		this.t = t;
		
		esLadoIzquierdo = false;
	}

	public Encadenado getEncadenado() {
		return encadenado;
	}

	public void setEncadenado(Encadenado encadenado) {
		this.encadenado = encadenado;
	}
	
	public String getNombre(){
		return t.getLexema();
	}
	
	public boolean tieneEncadenado(){
		
		return encadenado!=null;
	}

	public boolean isLadoIzquierdo() {
		return esLadoIzquierdo;
	}

	public void setEsLadoIzquierdo(boolean esLadoIzquierdo) {
		this.esLadoIzquierdo = esLadoIzquierdo;
	}
	
	
	
	

}
