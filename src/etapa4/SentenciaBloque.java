package etapa4;

import Excepciones.ErrorSemantico;
import etapa3.Clase;
import etapa3.Metodo;



public class SentenciaBloque extends Sentencia{
	
	private Bloque b;
	
	public SentenciaBloque(Bloque b) {
		this.b=b;
	}


	public boolean tieneRetorno(){
		return b.tieneRetorno();
	}

	@Override
	public void controlSentencia(Clase clase, Metodo metodo)
			throws ErrorSemantico {
		b.controlSentencias(clase, metodo);
		
	}

	

}