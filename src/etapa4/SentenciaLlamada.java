package etapa4;

import Excepciones.ErrorSemantico;
import etapa3.Clase;
import etapa3.Metodo;

public class SentenciaLlamada extends Sentencia{

	private Acceso acceso;
	
	public Acceso getAcceo(){
		return acceso;
	}

	public SentenciaLlamada(Acceso acceso) {
		this.acceso = acceso;
	}

	@Override
	public void controlSentencia(Clase clase, Metodo metodo) throws ErrorSemantico {
		
		acceso.chequear(clase,metodo);
	
	}
	
	
	
}
