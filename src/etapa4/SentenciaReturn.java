package etapa4;

import Excepciones.ErrorSemantico;
import etapa3.Clase;
import etapa3.Metodo;

public class SentenciaReturn extends Sentencia{

	private Expresion exp;

	public SentenciaReturn(Expresion exp) {
	
		this.exp = exp;
	}

	@Override
	public void controlSentencia(Clase clase, Metodo metodo)
			throws ErrorSemantico {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
