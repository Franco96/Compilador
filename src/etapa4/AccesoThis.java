package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Tipo;
import etapa3.TipoClase;

public class AccesoThis extends Acceso{

	public AccesoThis(Token t) {
		super(t);
	}

	@Override
	public Tipo chequear(Clase clase, Metodo metodo) throws ErrorSemantico {
		
		if(metodo.isStatic())
			throw new ErrorSemantico(t.getNroLinea()+" : no se puede acceder a this en un metodo estatico"
					+"\n\n[Error:"+t.getLexema()+"|"+
					t.getNroLinea()+"]");
		
		
		
		if(this.encadenado!=null)
			
			return encadenado.chequear(clase,metodo);
		else
			return new TipoClase(new Token("idClase",clase.getNombre() , t.getNroLinea()));
		
		
		
		
	}

	
}
