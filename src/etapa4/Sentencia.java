package etapa4;

import etapa3.Clase;
import etapa3.Metodo;
import Excepciones.ErrorSemantico;



public abstract class Sentencia{
	
	
	public abstract void controlSentencia(Clase clase,Metodo metodo) throws ErrorSemantico;

}