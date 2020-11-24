package etapa4;

import Excepciones.ErrorSemantico;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.TipoBoolean;

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
	
	

	
}
