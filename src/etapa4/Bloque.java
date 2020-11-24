package etapa4;

import java.util.LinkedList;
import java.util.List;
import Excepciones.ErrorSemantico;
import etapa3.Clase;
import etapa3.Metodo;


public class Bloque {
	
	private List<Sentencia> sentencias;


	public Bloque() {
		sentencias = new LinkedList<Sentencia>();
	
		
	
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