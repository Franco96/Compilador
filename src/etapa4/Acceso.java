package etapa4;

import etapa1.Token;


public abstract class Acceso extends Operando{
	
	
	protected Encadenado encadenado;
	
	public Acceso(Token t){
		this.t = t;
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
	
	

}
