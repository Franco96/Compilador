package etapa3;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa4.Bloque;
import etapa5.Generador;





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
	
	
	private Metodo getMain(){
		
		Metodo metodoMain;
		
		for (Clase clase : clases.values()) {
			
			metodoMain = clase.getMain();
			
			if(metodoMain != null) return metodoMain;
				
		}
		
		
		return null;
		
	}

	
	
	
	public void generarCodigo(String nombreArchivo) throws IOException{
		
		Generador.getGenerador().comentario("<<INICIO SECCIÓN DE CÓDIGO>>");
		Generador.getGenerador().saltoDeLinea();
		Generador.getGenerador().code();
		Generador.getGenerador().gen("PUSH "+getMain().getEtiqueta(), "# recupero la etiqueta del metodo main");
		Generador.getGenerador().gen("CALL","# Realizo la llamada a main, usando la referencia a este en el tope de la pila");
		Generador.getGenerador().gen("HALT","# Finalizo la ejecución");	
		
		Generador.getGenerador().saltoDeLinea();
		Generador.getGenerador().saltoDeLinea();
		
		Generador.getGenerador().comentario("Rutina para la gestion del heap");
		// Generacion del codigo de la rutina para la gestion del heap provista por la catedra
		Generador.getGenerador().encabezadoMet("simple_malloc");
		Generador.getGenerador().gen("LOADFP", "");
		Generador.getGenerador().gen("LOADSP", "");
		Generador.getGenerador().gen("STOREFP", "");
		Generador.getGenerador().gen("LOADHL", "");
		Generador.getGenerador().gen("DUP", "");
		Generador.getGenerador().gen("PUSH 1", "");
		Generador.getGenerador().gen("ADD", "");
		Generador.getGenerador().gen("STORE 4", "");
		Generador.getGenerador().gen("LOAD 3", "");
		Generador.getGenerador().gen("ADD", "");
		Generador.getGenerador().gen("STOREHL", "");
		Generador.getGenerador().gen("STOREFP", "");
		Generador.getGenerador().gen("RET 1", "");
		
		// Para cada clase perteneciente a la tabla de símbolos, genero su sección de código y datos correspondiente.
				for (Clase clase : this.clases.values()) {
					 clase.generarCodigo();
				}
		
		// Resta volcar el código generado a un archivo de salida.
		Generador.getGenerador().generarSalida(nombreArchivo);
	}
	
	
}
