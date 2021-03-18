package etapa4;

import Excepciones.ErrorSemantico;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.TipoBoolean;

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
		// TODO Auto-generated method stub
		
	}
	
	
	
}
