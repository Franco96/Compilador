package etapa4;

import Excepciones.ErrorSemantico;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.TipoBoolean;
import etapa5.Generador;

public class SentenciaWhile extends Sentencia{

	private Expresion condicion;
	private Sentencia cuerpoWhile;
	
	
	public SentenciaWhile(Expresion condicion, Sentencia cuerpoWhile) {

		this.condicion = condicion;
		this.cuerpoWhile = cuerpoWhile;
	}


	@Override
	public void controlSentencia(Clase clase, Metodo metodo)
			throws ErrorSemantico {
		
		if(!(condicion.chequear(clase, metodo) instanceof TipoBoolean))
			throw new ErrorSemantico(condicion.getLinea()+" : la condicion del while \""+condicion.getNombre()+"\" debe ser de tipo booleana "
					+"\n\n[Error:"+condicion.getNombre()+"|"+
					condicion.getLinea()+"]");
		
		cuerpoWhile.controlSentencia(clase, metodo);
		
	}


	@Override
	public void generarCodigo() {
		
		int etiquetaWhile = Generador.getGenerador().nuevaEtiquetaDeWhile();
		// Establezco la etiqueta inicial del ciclo while en cuestión.
		Generador.getGenerador().gen("while_" + etiquetaWhile + ": NOP", "# Etiqueta asociada al inicio de while");
		// Genero la expresión. Su valor correspondiente a la evaluación estará en el tope de la pila.
		this.condicion.generarCodigo();
		// Si el tope de la pila es falso (0), debo saltar al final del while.
		Generador.getGenerador().gen("BF fin_while_" + etiquetaWhile, "# Si es falsa la condición, no continúo iterando");
		// Si el salto previo no se produce, entonces se ejecuta la sentencia asociada al if.
		this.cuerpoWhile.generarCodigo();
		// Al término del código de la sentencia asociada al if, debo saltar el espacio de código else
		Generador.getGenerador().gen("JUMP while_" + etiquetaWhile, "# Salto al fin del segmento de sentencias de if");
		Generador.getGenerador().gen("fin_while_" + etiquetaWhile + ": NOP", "# Etiqueta asociada al else de if");
		
	}
	
	
	
}
