package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Tipo;

public abstract class Encadenado {

	protected Encadenado siguiente;
	protected Token token;
	
	public Encadenado(Token token){
		this.token = token;
	}
	
	public Encadenado getSiguiente() {
		return siguiente;
	}
	public void setSiguiente(Encadenado siguiente) {
		this.siguiente = siguiente;
	}
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	
	public String getNombre(){
		return token.getLexema();
	}
	
	public Encadenado getUltimoDelEncadenado(){
		
		Encadenado encaToReturn = this;
		
		
		while(encaToReturn.getSiguiente()!=null)
			encaToReturn = encaToReturn.getSiguiente();
		
		return encaToReturn;
		
	}
	
	
	public abstract Tipo chequear(Clase clase,Metodo metodo) throws ErrorSemantico;
	
}
