package etapa4;

import Excepciones.ErrorSemantico;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.TipoBoolean;
import etapa5.Generador;

public class SentenciaIf extends Sentencia{

private Expresion condicion;
private Sentencia cuerpoIf;
private Sentencia cuerpoElse;


public SentenciaIf(Expresion condicion, Sentencia cuerpoIf, Sentencia cuerpoElse) {
	this.condicion = condicion;
	this.cuerpoIf = cuerpoIf;
	this.cuerpoElse = cuerpoElse;
}


@Override
public void controlSentencia(Clase clase, Metodo metodo) throws ErrorSemantico {
	
	if(!(condicion.chequear(clase, metodo) instanceof TipoBoolean))
		throw new ErrorSemantico(condicion.getLinea()+" : la condicion del if \""+condicion.getNombre()+"\" debe ser de tipo booleana "
				+"\n\n[Error:"+condicion.getNombre()+"|"+
				condicion.getLinea()+"]");
	
	cuerpoIf.controlSentencia(clase, metodo);
	
	if(cuerpoElse!=null)
		cuerpoElse.controlSentencia(clase, metodo);
}
	
public boolean tieneRetorno(){
	
	boolean retornoCuerpoIf = false;
	boolean retornoCuerpoElse = false;
	
	if(cuerpoIf instanceof SentenciaReturn)
		retornoCuerpoIf = true;
	if(cuerpoIf instanceof SentenciaBloque)
		retornoCuerpoIf = ((SentenciaBloque) cuerpoIf).tieneRetorno();
	if(cuerpoIf instanceof SentenciaIf)
		retornoCuerpoIf = ((SentenciaIf) cuerpoIf).tieneRetorno();
	
	
	if(cuerpoElse instanceof SentenciaReturn)
		retornoCuerpoElse = true;
	if(cuerpoElse instanceof SentenciaBloque)
		retornoCuerpoElse = ((SentenciaBloque) cuerpoElse).tieneRetorno();
	if(cuerpoElse instanceof SentenciaIf)
		retornoCuerpoElse = ((SentenciaIf) cuerpoElse).tieneRetorno();
	
	
	return retornoCuerpoIf && retornoCuerpoElse;
}


@Override
public void generarCodigo() {
	
	
	int etiquetaIf = Generador.getGenerador().nuevaEtiquetaDeIf();
	
	
	// GENERO LA CONDICION.SU VALOR QUEDARA EN EL TOPE DE LA PILA.
		this.condicion.generarCodigo();
		
		
	// DEPENDIENDO DE SI TENGO ELSE SE GENERADA EL CODIGO CORRESPONDIENTE.
	if (this.cuerpoElse!=null) {
		
		// Si el tope de la pila es falso (0), debo saltar a la etiqueta del ELSE.
		Generador.getGenerador().gen("BF else_" + etiquetaIf, "# Si es falsa la condición, salto al else");
		
		// Si el salto previo no se produce, entonces se ejecuta la sentencia asociada al if.
		this.cuerpoIf.generarCodigo();
		
		// Al término del código de la sentencia asociada al if, debo saltar el espacio de código else
		Generador.getGenerador().gen("JUMP if_" + etiquetaIf, "# Salto al fin del segmento de sentencias de if");
		
		Generador.getGenerador().gen("else_" + etiquetaIf + ": NOP", "# Etiqueta asociada al else de if");
		this.cuerpoElse.generarCodigo();
	}
	else {
		// Si el tope de la pila es falso (0), debo saltar al fin del segmento de código del IF.
		Generador.getGenerador().gen("BF if_" + etiquetaIf, "# Si es falsa la condición, salto el if");
		// Si el salto previo no se produce, entonces se ejecuta la sentencia asociada al if.
		this.cuerpoIf.generarCodigo();
	}
	// Establezco la etiqueta de salto al final de la sentencia if.
	Generador.getGenerador().gen("if_" + etiquetaIf + ": NOP", "# Etiqueta asociada a la finalización de if");
	
	
	
}

	
}
