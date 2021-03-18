package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.TablaDeSimbolos;
import etapa3.Tipo;
import etapa5.Generador;


public class AccesoConstructor extends AccesoMetodo {

	
	public AccesoConstructor(Token t) {
		super(t);	
	}
	
	
	@Override
	public Tipo chequear(Clase clase,Metodo metodo) throws ErrorSemantico {
		
		Clase claseConstructor = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(t.getLexema());
		
		if(claseConstructor == null)
			throw new ErrorSemantico(t.getNroLinea()+" : acceso a constructor invalido porque la clase \""+t.getLexema()+"\" no existe"
					+"\n\n[Error:"+t.getLexema()+"|"+
					t.getNroLinea()+"]");
		
		
		chequearArgumentos(claseConstructor.getConstructor(), clase , metodo);
		
		if(this.encadenado!=null)		
			 return this.encadenado.chequear(claseConstructor,metodo);
		else
			 return claseConstructor.getConstructor().getTipoRetorno();
		
	}
	
	
	@Override
	public void generarCodigo() {
		
		
		Clase claseConstructor = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(t.getLexema());
		
		int cantAtributos = claseConstructor.getAtributos().size();
		
		int cantParametros = claseConstructor.getConstructor().getParametros().size();
		
		
		// Reservo espacio en memoria para la referencia a una nueva instancia de la clase correspondiente.
		Generador.getGenerador().gen("RMEM 1", "# Reservo espacio para la nueva instancia en el RA");
		
		// Apilo en el tope la cantidad de espacios requerida para el CIR asociado a la nueva instancia.
		Generador.getGenerador().gen("PUSH " + (cantAtributos + 1), "# Apilo cantidad de espacio requerido por CIR para atributos y VT");
		
		// Apilo la etiqueta del método MALLOC, antes de proceder a la reserva de espacio en memoria.
		Generador.getGenerador().gen("PUSH simple_malloc", "# Apilo la etiqueta de malloc para proceder a la reserva de espacio");
		
		Generador.getGenerador().gen("CALL", "# Aplico llamada a malloc. Reservo " + (cantAtributos + 1) + "espacios para nuevo CIR");
		
		Generador.getGenerador().gen("DUP", "# Duplico el tope: queda la referencia a la nueva instancia (CIR) en el tope");
		
		//Guardo etiqueta de VT
		Generador.getGenerador().gen("PUSH VT_" + claseConstructor.getNombre(), "# Apilo en el tope la etiqueta de la VT de la clase");
		
		Generador.getGenerador().gen("STOREREF 0", "# Almaceno la etiqueta de la VT como primera componente del CIR");
		
		// Duplico la referencia a la instancia creada para trabajar sobre su doble y no perderla.
		Generador.getGenerador().gen("DUP", "# Duplico la referencia al nuevo CIR para trabajar sobre su doble");
		
		// Llevo a cabo la generación de código para los parámetros efectivos.
		for (int i =0; i < cantParametros; i++) {
					// Al generar un parámetro actual correspondiente, obtendré en el tope de la pila su valor.
					this.argumentos.get(i).generarCodigo();;
					// Necesito disponer de la referencia a la instancia (this) en el tope de la pila: aplico swap.
					Generador.getGenerador().gen("SWAP", "# Intercambio el valor del parámetro actual por la referencia al CIR en el tope de la pila");
		}
		
		
		// Salto al código del constructor, que puede incializar ciertos atributos de la nueva instancia.
		Generador.getGenerador().gen("PUSH constructor_" + claseConstructor.getNombre(), "# Apilo etiqueta del constructor en el tope de la pila");
		Generador.getGenerador().gen("CALL", "# Aplico la llamada al constructor para proceder a la ejecución de su código");
		
		
		
	
		
		if(this.encadenado!=null){
			if(this.esLadoIzquierdo)
				encadenado.setLadoIzquierdo(true);
			encadenado.generarCodigo();
		}
	}
	

}
