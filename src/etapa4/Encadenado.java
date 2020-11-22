package etapa4;

import etapa1.Token;

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
	
	
	
}
