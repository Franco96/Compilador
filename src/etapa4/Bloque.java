package etapa4;

import java.util.LinkedList;
import java.util.List;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;


public class Bloque {
	private Token token;
	private List<Sentencia> sentencias;


	public Bloque(Token token) {
		sentencias = new LinkedList<Sentencia>();
	
		this.token=token;
	
	}
	
	
	
	public void addSent(Sentencia sent) {
		sentencias.add(sent);

	}
	
	
	public List<Sentencia> getSentencias(){
		
		return this.sentencias;
	}


	
	
	
	public void controlSentencias(Clase clase,Metodo metodo) throws ErrorSemantico {
		
		

		for (Sentencia sentencia : sentencias) 				
		
				sentencia.controlSentencia(clase,metodo); 
			
	
	}
	


}