package etapa4;

import java.util.LinkedList;
import java.util.List;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.TablaDeSimbolos;
import etapa3.Tipo;
import etapa3.TipoVoid;
import etapa5.Generador;

public class AccesoMetodo extends Acceso{
	
	protected List<Expresion> argumentos;
	
	private Metodo metodoConvocado;
	

	public AccesoMetodo(Token t) {
		super(t);
		this.argumentos = new LinkedList<Expresion>();
	}
	
	
	public void addArgumentos(Expresion sent) {
		this.argumentos.add(sent);		
	}
	
	public List<Expresion> getArgumentosActuales(){
		 
		return argumentos;
	}


	@Override
	public Tipo chequear(Clase clase,Metodo metodo) throws ErrorSemantico {
		
		 metodoConvocado = clase.getMetodos().get(t.getLexema());
		
		if(metodoConvocado==null){
			throw new ErrorSemantico(t.getNroLinea()+" : no existe un metodo con el nombre \""+t.getLexema()+"\""+" "
					+ "definido en la clase \""+clase.getNombre()+"\""
					+"\n\n[Error:"+t.getLexema()+"|"+
					t.getNroLinea()+"]");
		}
		
		if(metodo.isStatic()&& metodoConvocado.isDynamic())
			throw new ErrorSemantico(t.getNroLinea()+" : no se puede llamar a un metodo dinamico dentro de un "
					+ "metodo estatico"
					+"\n\n[Error:"+metodoConvocado.getNombre()+"|"+
					t.getNroLinea()+"]");
		
		
		chequearArgumentos(metodoConvocado,clase,metodo);
		
		if(this.encadenado!=null){
		
			Clase claseRetorno = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(metodoConvocado.getTipoRetorno().getNombre());
			
			if(claseRetorno!=null)
					return this.encadenado.chequear(claseRetorno,metodo);//Le paso la clase que retorna el metodo y el metodo donde se convoca el encadenado
			else
					throw new ErrorSemantico(t.getNroLinea()+" : el metodo \""+t.getLexema()+"\""+" "
							+ "deberia retornar una clase que contenga el metodo \""+encadenado.getNombre()+"\""
							+"\n\n[Error:"+t.getLexema()+"|"+
							t.getNroLinea()+"]");
			
		
		}else
			 return metodoConvocado.getTipoRetorno();
	
	}		

	
	
	
	protected void chequearArgumentos(Metodo metodoConvocado,Clase clase,Metodo metodo) throws ErrorSemantico{
		
		if(metodoConvocado.getParametros().size() != this.argumentos.size())
			throw new ErrorSemantico(t.getNroLinea()+" : no coincide el numero de parametros actuales con el metodo/constructor \""+t.getLexema()+"\""
					+"\n\n[Error:"+t.getLexema()+"|"+
					t.getNroLinea()+"]");
		
		
		for(int i = 0 ; i<metodoConvocado.getParametros().size() ; i++){
		
			Tipo tipoArgFormal = metodoConvocado.getParametro(i).getTipo();
			
			Tipo tipoArgActual = this.argumentos.get(i).chequear(clase, metodo);
			
			if(!tipoArgActual.conforma(tipoArgFormal))
				
				throw new ErrorSemantico(t.getNroLinea()+" : el parametro actual numero \""+(i+1)+"\" del metodo/constructor "
						+ "\""+t.getLexema()+"\" no conforma con su parametro formal"
						+"\n\n[Error:"+t.getLexema()+"|"+
						t.getNroLinea()+"]");
			
		}
			
		
	}


	@Override
	public void generarCodigo() {
		
		
		if(metodoConvocado.isDynamic()){
		
		
				
					Generador.getGenerador().gen("LOAD 3", "# Cargo referencia al CIR de this (RA actual) en el tope de la pila");
					
					
					// Si el tipo de retorno NO es void, entonces considero tener un espacio para el valor de retorno.
					if ( !(metodoConvocado.getTipoRetorno() instanceof TipoVoid) ) {
						
						Generador.getGenerador().gen("RMEM 1", "# Reservo espacio para el valor de retorno del método");
						Generador.getGenerador().gen("SWAP", "# Intercambio el espacio reservado por el de this en el tope de la pila");
					}
					
					// Llevo a cabo la generación de código para los parámetros efectivos.
					for (int i = 0; i < metodoConvocado.getParametros().size(); i++) {
						// Al generar un parámetro actual correspondiente, obtendré en el tope de la pila su valor.
						this.argumentos.get(i).generarCodigo();
						// Necesito disponer de la referencia a la instancia (this) en el tope de la pila: aplico swap.
						Generador.getGenerador().gen("SWAP", "# Intercambio el valor del parámetro actual por el de this en el tope de la pila");
					}
					
					
					Generador.getGenerador().gen("DUP", "# Duplico la referencia al nuevo CIR para trabajar sobre su doble");
					Generador.getGenerador().gen("LOADREF 0", "# Apilo la referencia a la VT del this en el tope de la pila");
					
					Generador.getGenerador().gen("LOADREF " + metodoConvocado.getOffset(), "# Apilo la referencia al método a ser invocado en el tope de la pila");
					Generador.getGenerador().gen("CALL", "# Aplico la llamada al método para proceder a la ejecución de su código");			
				
					
					
		}else {
			
			
			if ( !(metodoConvocado.getTipoRetorno() instanceof TipoVoid) ) 
				Generador.getGenerador().gen("RMEM 1", "# Reservo espacio para el valor de retorno del método");
			
			
			
			
			
			for (int i = 0; i < metodoConvocado.getParametros().size(); i++) 
				
				this.argumentos.get(i).generarCodigo();
				
			
			
			
			
			Generador.getGenerador().gen("PUSH " + metodoConvocado.getEtiqueta(),
											 "# Apilo etiqueta del método en el tope de la pila");
			Generador.getGenerador().gen("CALL", "# Aplico la llamada al método para proceder a la ejecución de su código");
		
		}
					
					
					
					
					
					
					
				
			if(this.encadenado!=null){
				if(esLadoIzquierdo)
					encadenado.setLadoIzquierdo(true);
				encadenado.generarCodigo();
			}
	}
	
}
