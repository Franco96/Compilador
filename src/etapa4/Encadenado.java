package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Tipo;

public abstract class Encadenado {

	protected Encadenado siguiente;
	protected Token token;
	protected boolean ladoIzquierdo;
	protected boolean seguidoDeThis;
	
	public Encadenado(Token token){
		this.token = token;
		this.ladoIzquierdo = false;
		this.seguidoDeThis = false;
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
	
	

	public boolean isLadoIzquierdo() {
		return ladoIzquierdo;
	}

	public void setLadoIzquierdo(boolean ladoIzquierdo) {
		this.ladoIzquierdo = ladoIzquierdo;
	}
	
	

	public boolean isSeguidoDeThis() {
		return seguidoDeThis;
	}

	public void setSeguidoDeThis(boolean seguidoDeThis) {
		this.seguidoDeThis = seguidoDeThis;
	}

	public Encadenado getUltimoDelEncadenado(){
		
		Encadenado encaToReturn = this;
		
		
		while(encaToReturn.getSiguiente()!=null)
			encaToReturn = encaToReturn.getSiguiente();
		
		return encaToReturn;
		
	}
	
	
	public abstract Tipo chequear(Clase clase,Metodo metodo) throws ErrorSemantico;
	
	
	public abstract void generarCodigo();
	
	
}
