package etapa3;


import java.util.HashMap;
import java.util.Map;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa4.Bloque;
import etapa5.Generador;






public class Metodo {
	private String nombreClase;
	private String nombre;
	private String forma;
	private Tipo tipoRetorno;
	private Map<String, Parametro> params;
	private int linea;
	private Bloque bloque;
	private boolean isChequeado;
	private int offset;
	private boolean isGenerado;
	private int offsetDeRetorno;
	
	
	public Metodo(Token t, String forma, Tipo tipoRetorno,String nombreClase) {
		
		nombre = t.getLexema();
		linea = t.getNroLinea();
		params = new HashMap<String, Parametro>();
		
		this.forma = forma;
		this.tipoRetorno = tipoRetorno;
		this.bloque = new Bloque();
		isChequeado = false;
		this.nombreClase = nombreClase;
		
		offset = -1; //indica que no tiene offset asignado.
		isGenerado = false;
	}
	

	public String getNombre() {
		return nombre;
	}
	
	public int getLinea(){
		return linea;
	}
	
	public String getForma(){
		return forma;
	}

	public Tipo getTipoRetorno(){
		return tipoRetorno;
	}
	
	public Map<String, Parametro> getParametros() {
		return params;
	}
	
	
	
	public boolean isDynamic() {
		return forma.equals("dynamic");
	}

	public boolean isStatic() {
		return forma.equals("static");
	}

	public void setBloque(Bloque bloque){
		this.bloque = bloque;
	}
	
	public Bloque getBloque(){
		
		return bloque;
	}
	
	public boolean isChequeado() {
		return isChequeado;
	}

	public void setChequeado(boolean isChequeado) {
		this.isChequeado = isChequeado;
	}

	
	
	public int getOffset() {
		return offset;
	}


	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public boolean isOffsetAsigned(){
		return offset != -1;
	}

	
	

	public boolean isGenerado() {
		return isGenerado;
	}


	public void setGenerado(boolean isGenerado) {
		this.isGenerado = isGenerado;
	}
	
	

	public int getOffsetDeRetorno() {
		return offsetDeRetorno;
	}


	public void setOffsetDeRetorno(int offsetDeRetorno) {
		this.offsetDeRetorno = offsetDeRetorno;
	}


	public Parametro getParametro(int indice){
		
		Parametro toReturn = null;
		
				for(Parametro p : params.values()){
					
					if(p.getIndice()==indice)
						toReturn = p;
				}
			
		
		return toReturn;
	}

	public void insertarParametros(Parametro p) throws ErrorSemantico{
		
		if(!params.containsKey(p.getNombre()))
			params.put(p.getNombre(), p);
		else
			throw new ErrorSemantico(p.getLinea()+" : ya existe un parametro con el mismo nombre \""+p.getNombre()+"\""
					+"\n\n[Error:"+p.getNombre()+"|"+
					p.getLinea()+"]");
	}
	
	
	public String getEtiqueta() {
		return nombre +"_en_"+ nombreClase;
	}
	
	
		
	
	
	public void controlDeclaracion() throws ErrorSemantico{
		

		// Si retorna un tipo clase, entonces dicha clase debe estar declarada.
		
		if (tipoRetorno instanceof TipoClase) {
			if (TablaDeSimbolos.getTablaDeSimbolos().getClases().get(tipoRetorno.getNombre())==null)
				throw new ErrorSemantico(tipoRetorno.getLinea()+" : la clase \""+tipoRetorno.getNombre()+"\" no fue declarada"
						+"\n\n[Error:"+tipoRetorno.getNombre()+"|"+
						tipoRetorno.getLinea()+"]");
		}
		
		// Verifica que para los argumentos de tipo clase, la misma este
				// declarada.
		for (Parametro param : params.values()) {
					
					if (param.getTipo() instanceof TipoClase) {
						
						if (TablaDeSimbolos.getTablaDeSimbolos().getClases().get(param.getTipo().getNombre())==null)
							
							throw new ErrorSemantico(param.getTipo().getLinea()+" : la clase \""+param.getTipo().getNombre()+"\" no fue declarada"
									+"\n\n[Error:"+param.getTipo().getNombre()+"|"+
									param.getTipo().getLinea()+"]");
					}
				}
		
		
	}
	
	
	
	public void puedeSobreEscribirse(Metodo m) throws ErrorSemantico{
		
		 
		
		if(!forma.equals(m.getForma()))
				throw new ErrorSemantico(m.getLinea()+" : el metodo \""+this.nombre+"\" no se puede redefinir porque su forma es distinta"
					+"\n\n[Error:"+this.nombre+"|"+
					m.getLinea()+"]");
		
		if(!tipoRetorno.getNombre().equals(m.getTipoRetorno().getNombre()))
			throw new ErrorSemantico(m.getLinea()+" : el metodo \""+this.nombre+"\" no se puede redefinir porque su tipo de retorno es distinto"
					+"\n\n[Error:"+this.nombre+"|"+
					m.getLinea()+"]");
		
		if(params.size()!=m.getParametros().size())
			
			throw new ErrorSemantico(m.getLinea()+" : el metodo \""+this.nombre+"\" no se puede redefinir porque su cantidad de parametros es distinta"
					+"\n\n[Error:"+this.nombre+"|"+
					m.getLinea()+"]");	
		
		
		for(int i=0;i<params.size();i++){
			
			if(!this.getParametro(i).getTipo().getNombre().equals(m.getParametro(i).getTipo().getNombre()))
				
				throw new ErrorSemantico(m.getLinea()+" :El metodo \""+nombre+"\" no se puede redefinir, el parametro numero "+(i+1)+" no matchea. Se esperaba un Parametro de tipo \""
						+this.getParametro(i).getTipo().getNombre()+"\" y encontro un parametro de tipo \""+m.getParametro(i).getTipo().getNombre()+"\""
						+"\n\n[Error:"+this.nombre+"|"+
						m.getLinea()+"]");
		}
		
	}
	
	public void controlSentencia(Clase clase) throws ErrorSemantico{
		
		bloque.controlSentencias(clase,this);
			
		
	}
	
	
	
	
	public void generarCodigo(){
		
		// ASOCIO LA NUEVA UNIDAD A GENERAR
		Generador.getGenerador().setMetodoActualAGenerar(this);
		

		//COMPUTO EL OFFSET PARA UBICACION DE RETORNO.
		calcularOffsetDeRetorno();
		
		Generador.getGenerador().gen("LOADFP", "# Guardo el ED: dirección base del RA de la unidad llamadora.");
		Generador.getGenerador().gen("LOADSP", "# Apilo la dirección base del RA de la unidad llamada.");
		Generador.getGenerador().gen("STOREFP", "# Actualizo el FP (frame pointer) con el tope de la pila.");
		
		
		// Procedo a generar el código del bloque del método en cuestión, reiniciando el conteo de vars locales generadas por método.		
		Generador.getGenerador().nuevoConteoVarLocalesGeneradas();
		
		bloque.generarCodigo();
		
		
		
		// Genero el código para la terminación de la ejecución de la instancia activa asociada al RA antes creado.
		Generador.getGenerador().gen("STOREFP", "# Recupero la dirección base del RA llamador (uso el ED)");
		
		
		// Procedo a liberar el espacio ocupado por los parámetros (de las variables locales se encarga el bloque).
		if (this.isStatic()) 
			// Si el método en cuestión es estático, debo considerar entonces que no tengo 'this' al momento de liberar espacio.
			Generador.getGenerador().gen("RET " + this.params.size(), "# Retorno: libero " +  this.params.size() + " espacios");	
		else 
			// Si el método en cuestión es dinámico, debo considerar el 'this' al momento de liberar espacio.
			Generador.getGenerador().gen("RET " + ( this.params.size() + 1), "# Retorno: libero " + ( this.params.size() + 1) + " espacios");
				
		
		
		
		
		
		
		
		// Marco al método como generado.
		 
		this.setGenerado(true);
	}
	
	
	/**
	 * calcularOffsetDeRetorno: permite computar el valor de offset requerido sobre el RA de la unidad para acceder al retorno.
	 */
	public void calcularOffsetDeRetorno () {
		
		if (!(this.tipoRetorno instanceof TipoVoid)) {
		
			if (this.isDynamic()) {
				offsetDeRetorno = 3 + params.size() + 1;
			}
			else {
				offsetDeRetorno = 2 + params.size() + 1;
			}	
		}
		// Si el tipo de retorno es void, no debo considerar espacio adicional para el retorno, entonces asigno 0.
		else offsetDeRetorno = 0;
			
	}
	
	
}
