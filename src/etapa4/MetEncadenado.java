package etapa4;


import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.TablaDeSimbolos;
import etapa3.Tipo;
import etapa3.TipoVoid;
import etapa5.Generador;

public class MetEncadenado extends Encadenado{

	private AccesoMetodo actMet;
	private Metodo metodoConvocado;
	
	public MetEncadenado(Token token,AccesoMetodo actMet) {
		super(token);
		this.actMet = actMet;
	}

	
	public void setAccesoMetodo(AccesoMetodo actMet){
		this.actMet = actMet;
	}


	@Override
	public Tipo chequear(Clase clase,Metodo metodo) throws ErrorSemantico {
		
		int linea = this.actMet.t.getNroLinea();
		String nombreMetodo = this.actMet.t.getLexema();
		metodoConvocado = clase.getMetodos().get(nombreMetodo);
		
		
		
		if(metodoConvocado==null){
			
			throw new ErrorSemantico(linea+" : no existe un metodo con el nombre \""+nombreMetodo+"\""+" definido en la clase \""+clase.getNombre()+"\""
					+"\n\n[Error:"+nombreMetodo+"|"+
					linea+"]");
		}
		
		actMet.chequearArgumentos(metodoConvocado, clase, metodo);
		
		if(this.siguiente!=null){
			
			Clase claseRetorno = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(metodoConvocado.getTipoRetorno().getNombre());
			
			if(claseRetorno!=null)
					return this.siguiente.chequear(claseRetorno,metodo);
			else
				throw new ErrorSemantico(linea+" : el metodo \""+nombreMetodo+"\""+" deberia retornar una clase que contenga el metodo \""+siguiente.getNombre()+"\""
						+"\n\n[Error:"+nombreMetodo+"|"+
						linea+"]");
			
			
		}else
			 return metodoConvocado.getTipoRetorno();
	}


	@Override
	public void generarCodigo() {
		
		
		
		
		
		if(metodoConvocado.isDynamic()){
			
				
				if (!(metodoConvocado.getTipoRetorno() instanceof TipoVoid)) {
					Generador.getGenerador().gen("RMEM 1", "# Reservo espacio para el valor de retorno del método");
					Generador.getGenerador().gen("SWAP", "# Intercambio el espacio reservado por el de this en el tope de la pila");
				}
				
				// Genero los correspondientes parámetros actuales para esta invocación.
				for (int i=0; i < metodoConvocado.getParametros().size(); i++) {
					
					// Al generar un parámetro actual correspondiente, obtendré en el tope de la pila su valor.
					this.actMet.argumentos.get(i).generarCodigo();;
					// Necesito disponer de la referencia a la instancia (this) en el tope de la pila: aplico swap.
					Generador.getGenerador().gen("SWAP", "# Intercambio el valor del parámetro actual por el de this en el tope de la pila");
				}
				
				Generador.getGenerador().gen("DUP", "# Duplico la referencia al nuevo CIR para trabajar sobre su doble");
				Generador.getGenerador().gen("LOADREF 0", "# Apilo la referencia a la VT del this en el tope de la pila");
				
				Generador.getGenerador().gen("LOADREF " + metodoConvocado.getOffset(), "# Apilo la referencia al método a ser invocado en el tope de la pila");
				Generador.getGenerador().gen("CALL", "# Aplico la llamada al método para proceder a la ejecución de su código");
			
		}else{
			
				// Si no es dinámico, entonces procedo a descartar la referencia a 'this' en el tope de la pila
				Generador.getGenerador().gen("POP", "# Desapilo la referencia al CIR del this del tope"); 
				
				if (!(metodoConvocado.getTipoRetorno() instanceof TipoVoid)) 
					Generador.getGenerador().gen("RMEM 1", "# Reservo espacio para el valor de retorno del método");
				
				// Genero los correspondientes parámetros actuales para esta invocación.
				for (int i=0; i < metodoConvocado.getParametros().size(); i++) {
					
					// Al generar un parámetro actual correspondiente, obtendré en el tope de la pila su valor.
					this.actMet.argumentos.get(i).generarCodigo();;
					// Necesito disponer de la referencia a la instancia (this) en el tope de la pila: aplico swap.
					Generador.getGenerador().gen("SWAP", "# Intercambio el valor del parámetro actual por el de this en el tope de la pila");
				}
				
				Generador.getGenerador().gen("PUSH " + metodoConvocado.getEtiqueta(), "# Apilo etiqueta del método en el tope de la pila");
				Generador.getGenerador().gen("CALL", "# Aplico la llamada al método para proceder a la ejecución de su código");
				
				
		}
		
		
		
		if(this.siguiente!=null){
			if(ladoIzquierdo)
				siguiente.setLadoIzquierdo(true);
			siguiente.generarCodigo();
		}
	
	}
}
