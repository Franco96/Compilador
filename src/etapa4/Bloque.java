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
	
	
	private boolean hayRetorno(Sentencia sentencia){
		
		if(sentencia instanceof SentenciaReturn)
			return true;
		if((sentencia instanceof SentenciaBloque) && ((SentenciaBloque) sentencia).tieneRetorno())
			return true;
		
		if((sentencia instanceof SentenciaIf) && ((SentenciaIf) sentencia).tieneRetorno())
			return true;
		
		return false;
	}
	
	
	public boolean tieneRetorno(){
		
		boolean hayRetorno = false;
		
		for(Sentencia sentencia : sentencias){
			
			 hayRetorno = hayRetorno(sentencia);
			 
			 if(hayRetorno)
				 return true;
			
		}
		
		return hayRetorno;
	}
	
	
	
	public void addSent(Sentencia sent) {
		sentencias.add(sent);

	}
	
	
	public List<Sentencia> getSentencias(){
		
		return this.sentencias;
	}


	
	
	
	public void controlSentencias(Clase clase,Metodo metodo) throws ErrorSemantico {
		
		boolean hayReturn = false;

		for (Sentencia sentencia : sentencias){ 
				
				if(hayReturn)
					throw new ErrorSemantico(metodo.getLinea()+" : se detecto codigo muerto en el metodo \""+metodo.getNombre()+"\""
							+"\n\n[Error:"+metodo.getNombre()+"|"+
							metodo.getLinea()+"]");
		
				sentencia.controlSentencia(clase,metodo); 
				
				
					hayReturn = hayRetorno(sentencia);			//La siguiente sentencia si hay retorno sera codigo muerto
		}
	
	}
	


}