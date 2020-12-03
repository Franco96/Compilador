package etapa4;

import Excepciones.ErrorSemantico;
import etapa3.Clase;
import etapa3.Metodo;
import etapa5.GCI;



public class BloqueSystem extends Bloque{
	
	private String implementacion;
	
	public BloqueSystem(String implementacion){
		
		this.implementacion=implementacion;
	}
	@Override
	public void controlSentencias(Clase clase,Metodo metodo) throws ErrorSemantico {
		
		String[]  simp = implementacion.split("\n");
		
		
		for (String s : simp)
			GCI.gen().gen(s,"");
		
		
	}
}	
	
