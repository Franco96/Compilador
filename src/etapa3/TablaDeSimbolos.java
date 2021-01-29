package etapa3;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa4.Bloque;
import etapa5.GCI;




public class TablaDeSimbolos {
	
	//Unica instancia y global, patron singleton
	private static final TablaDeSimbolos INSTANCE = new TablaDeSimbolos();
	
	private Map<String, Clase> clases;
	private Clase claseActual;
	private Metodo metActual;
	private Bloque bloqueActual;
	private CSystem system;
	
	private TablaDeSimbolos(){
		
	}
	
	 public static TablaDeSimbolos getTablaDeSimbolos() {
	        return INSTANCE;
	    }
	
	public void limpiar() throws ErrorSemantico{
		clases =  new HashMap<String,Clase>();
		claseActual = null;
		metActual = null;
		insetarClaseObject();
		system = new CSystem();
		system.init();
		
	}
	
	public void setClaseActual(Clase c){
		claseActual = c;
	}
	
	public Clase getClaseActual(){
		return claseActual;
	}
	
	

	public Metodo getMetodoActual() {
		return metActual;
	}

	public void setMetodoActual(Metodo metActual) {
		this.metActual = metActual;
	}
	
	public Bloque getBloqueActual() {
		return bloqueActual;
	}

	public void setBloqueActual(Bloque bloqueActual) {
		this.bloqueActual = bloqueActual;
	}

	public Map<String, Clase> getClases() {
		return clases;
	}

	private void insetarClaseObject() throws ErrorSemantico {
		Clase c = new Clase(new Token("idClase","Object",0));	
		c.insertarConstructor(new Token("idClase", c.getNombre(), 0),  new TipoClase(new Token("idClase", c.getNombre(), 0)));
		clases.put("Object", c);

	}
	
	
	public void insertarClase(Clase c) throws ErrorSemantico{
		
		if(!clases.containsKey(c.getNombre()))
			clases.put(c.getNombre(), c);
		else 
			throw new ErrorSemantico(c.getLinea()+" : ya existe una clase con el nombre \""+c.getNombre()+"\""
					+"\n\n[Error:"+c.getNombre()+"|"+
					c.getLinea()+"]");
			
		
	}

	
	
	
	public void controlDeclaracion() throws ErrorSemantico {
		
	
		
		for (Clase c : clases.values()) {
			c.controlDeclaracion();
		}
		
		mainDeclarado();
	}
	
	public void controlSentencia() throws ErrorSemantico {
		
		/* Protocolo de inicializacion */
		Date d = new Date();
		
		//GCI.gen().comment("# Codigo genenerado por el compilador minijava");
		
		for (Clase c : clases.values()) {
			c.controlSentencia();
		}
		

	}
	
	
	
	private void mainDeclarado() throws ErrorSemantico{
		
		for (Clase c : clases.values()) {
			
			if(c.tieneMain())
				return;
				
		}
		
		throw new ErrorSemantico(0+" : No se ha declarado el metodo \"main\" en ninguna clase"
				+"\n\n[Error:main|"+
				0+"]");
		
	}

	
	
	
}
