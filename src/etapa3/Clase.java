package etapa3;

import java.util.HashMap;
import java.util.Map;

import Excepciones.ErrorSemantico;
import etapa1.Token;



public class Clase {
	
	private String nombre;
	private Token hereda;
	
	private Map<String, Atributo> atributos;
	private Map<String, Metodo> metodos;
	
	private int linea;
	
	private Metodo constructor;
	
	private boolean chequeada;
	
	private boolean consolidada;
	
	
	public Clase(Token t) {
		
		nombre = t.getLexema();
		linea = t.getNroLinea();
		atributos = new HashMap<String, Atributo>();
		metodos = new HashMap<String, Metodo>();
		constructor = null;
		chequeada = false;
		}


	public String getNombre() {
		return nombre;
	}


	public Token getHereda() {
		return hereda;
	}

	public void setHereda(Token hereda) {
		this.hereda = hereda;
	}

	public Map<String, Atributo> getAtributos() {
		return atributos;
	}


	public Map<String, Metodo> getMetodos() {
		return metodos;
	}

	public int getLinea() {
		return linea;
	}



	public Metodo getConstructor() {
		return constructor;
	}

	
	
	public boolean isChequeada() {
		return chequeada;
	}


	public void setChequeada(boolean chequeada) {
		this.chequeada = chequeada;
	}


	public void insertarAtributo(Atributo atrib) throws ErrorSemantico{
		if(!atributos.containsKey(atrib.getNombre()))
			atributos.put(atrib.getNombre(), atrib);
		else 
			throw new ErrorSemantico(atrib.getLinea()+" : ya existe un atributo con el mismo nombre \""+atrib.getNombre()+"\""
					+"\n\n[Error:"+atrib.getNombre()+"|"+
					atrib.getLinea()+"]");
	}
	
	public void insertarConstructor(Token t,Tipo tipo) throws ErrorSemantico{
		if(constructor!=null)
			throw new ErrorSemantico(t.getNroLinea()+" : solamente puede existir un unico constructor \""+t.getLexema()+"\""
					+"\n\n[Error:"+t.getLexema()+"|"+
					t.getNroLinea()+"]");
		
		
		if(!t.getLexema().equals(nombre))
			throw new ErrorSemantico(t.getNroLinea()+" : constructor mal definido , debe tener nombre de la clase \""+nombre+"\" "
					+"se encontro \""+t.getLexema()+"\""
					+"\n\n[Error:"+t.getLexema()+"|"+
					t.getNroLinea()+"]");
	
		
		constructor = new Metodo(t, "dynamic", tipo);
	}
	
	
	public void insetarMetodo(Metodo m) throws ErrorSemantico{
		
		if(!metodos.containsKey(m.getNombre())){
			metodos.put(m.getNombre() , m);
			
		}
		else 
			throw new ErrorSemantico(m.getLinea()+" : ya existe un metodo con el nombre \""+m.getNombre()+"\""
				+"\n\n[Error:"+m.getNombre()+"|"+
				m.getLinea()+"]");

	}
	
	
	
	public void controlDeclaracion() throws ErrorSemantico{
		
		// clases predefinidas Object y System no se controlan
		if (nombre.equals("Object") || nombre.equals("System"))
		return;
		
	
		for(Clase c : TablaDeSimbolos.getTablaDeSimbolos().getClases().values())
			   c.setChequeada(false);
		
		//Controlo herencia circular y ademas que las clases que extiende esten declaradas
		hayHerenciaCircular(nombre);
		
		//Chequeo las declaraciones de atributos

		for (Atributo atrib : atributos.values()) {
				
				if (atrib.getTipo() instanceof TipoClase){
						
					if(TablaDeSimbolos.getTablaDeSimbolos().getClases().get(atrib.getTipo().getNombre())==null){
						
							throw new ErrorSemantico(atrib.getTipo().getLinea()+" : la clase \""+atrib.getTipo().getNombre()+"\" no fue declarada"
									+"\n\n[Error:"+atrib.getTipo().getNombre()+"|"+
									atrib.getTipo().getLinea()+"]");
						}
				}
		}
		
		//Chequeo para los parametros del constructor
				
				if(constructor==null){
					constructor = new Metodo(new Token("idClase",nombre , 0),"dynamic" , new TipoClase(new Token("idClase", this.nombre, 0)));
				}
				else	
				constructor.controlDeclaracion();
		// Realizo el chequeo de declaracion de los metodos
				for (Metodo m : metodos.values())
					m.controlDeclaracion();

		actualizarTablasHerencias();
		
	}
	
	public boolean tieneMain(){
		
		for(Metodo m : metodos.values()){
			
			if( (m.getNombre().equals("main"))
					&& (m.getParametros().size()==0) 
					&& (m.getForma().equals("static")) 
					&& (m.getTipoRetorno() instanceof TipoVoid) ){
					
						return true;	
						
			}
			
		}
		
		return false;
	}
	

	
	private void hayHerenciaCircular(String nombre) throws ErrorSemantico{
		
		
		
         this.chequeada = true;
		
		if(hereda.getLexema().equals(nombre))
			throw new ErrorSemantico(linea+" : la clase \""+nombre+"\" sufre herencia circular"
					+"\n\n[Error:"+nombre+"|"+
					linea+"]");
		
		 Clase ancestro = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(hereda.getLexema());
		 
		 if(ancestro == null)
			 throw new ErrorSemantico(hereda.getNroLinea()+" : la clase \""+hereda.getLexema()+"\" no fue declarada"
						+"\n\n[Error:"+hereda.getLexema()+"|"+
						hereda.getNroLinea()+"]");
		 
		 if(ancestro.isChequeada() || ancestro.getNombre().equals("Object"))
			 return;
		 
		 
		 
		 ancestro.hayHerenciaCircular(nombre);
		
	}
	
	
	
	
	private void actualizarTablasHerencias() throws ErrorSemantico{
		
		if(!nombre.equals("Object") && !consolidada){
			
				Clase clasePadre=TablaDeSimbolos.getTablaDeSimbolos().getClases().get(hereda.getLexema());
				clasePadre.actualizarTablasHerencias();
			
				for(Metodo heredado: clasePadre.getMetodos().values()){
					
							Metodo redefinido= metodos.get(heredado.getNombre()); 
				
							if(redefinido!=null)
									heredado.puedeSobreEscribirse(redefinido);
							else
									metodos.put(heredado.getNombre(), heredado);
				}
				
				
				for(String claveHeredado :clasePadre.getAtributos().keySet()){
				
						Atributo heredado = clasePadre.getAtributos().get(claveHeredado);
						
							
						if(claveHeredado.contains("super.")){
							//No hace nada porque se esta soobreescribiendo un atributo en la clase padre
						
						}else if(atributos.get(claveHeredado)==null){
							atributos.put(claveHeredado, heredado);
						}else
							atributos.put("super."+claveHeredado, heredado);
							
				}
					

		}
		
		
		consolidada=true;
	}
	
	
	public boolean esAncestro(String c){
		
		if (this.nombre.equals("Object"))
			return false;
		
		if (this.nombre.equals(c))
			return true;
		
		Clase claseAncestro = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(hereda.getLexema());
		
		return claseAncestro.esAncestro(c);
		
	}
	
	public void controlSentencia() throws ErrorSemantico{
		
		// clases predefinidas Object y System no se controlan
				if (nombre.equals("Object") || nombre.equals("System"))
				return;
		
		constructor.controlSentencia(this);
		
		
		for(Metodo metodo : metodos.values()){
			
				if(!metodo.isChequeado()){
					metodo.controlSentencia(this);
					metodo.setChequeado(true);
				}
		}
		}
		
		
	}
	
	
	

