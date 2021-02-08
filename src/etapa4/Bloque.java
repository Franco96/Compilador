package etapa4;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Parametro;
import etapa3.TablaDeSimbolos;
import etapa3.TipoClase;
import etapa3.Variable;
import Excepciones.ErrorSemantico;



public class Bloque {
	
	private List<Sentencia> sentencias;
	private Map<String,Variable> varLocales;
	

	public Bloque() {
		sentencias = new LinkedList<Sentencia>();
		varLocales = new HashMap<String, Variable>();
	}
	
	
	
	public Map<String, Variable> getVariablesLocales() {
		return this.varLocales;
	}
	
	public void insertarVariable(Variable v) throws ErrorSemantico{
		
		Map<String, Parametro> params = TablaDeSimbolos.getTablaDeSimbolos().getMetodoActual().getParametros();
		
		if(!params.containsKey(v.getNombre()) && !varLocales.containsKey(v.getNombre()))
				varLocales.put(v.getNombre(), v);
		
		else
			throw new ErrorSemantico(v.getLinea()+" : ya existe una variable o un parametro con el mismo nombre \""+v.getNombre()+"\""+" en el metodo \""
					+TablaDeSimbolos.getTablaDeSimbolos().getMetodoActual().getNombre()+"\""
					+"\n\n[Error:"+v.getNombre()+"|"+
					v.getLinea()+"]");
			
	}
	
	
	public void pasarVarDelBloquePadre(Bloque bloquePadre){
		
			for(Variable v : bloquePadre.getVariablesLocales().values())
		
						this.varLocales.put(v.getNombre(), v);
	
		
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
		
		if(! (clase.getNombre().equals("System")) ){	
			
			for (Variable variable : this.varLocales.values()) {
				
				if (variable.getTipo() instanceof TipoClase){
						
					if(TablaDeSimbolos.getTablaDeSimbolos().getClases().get(variable.getTipo().getNombre())==null){
						
							throw new ErrorSemantico(variable.getLinea()+" : la clase \""+variable.getTipo().getNombre()+"\" no fue declarada"
									+"\n\n[Error:"+variable.getTipo().getNombre()+"|"+
									variable.getLinea()+"]");
						}
				}
			}	
		}
		
		
		
		
		
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