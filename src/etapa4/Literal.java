package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Tipo;
import etapa3.TipoBoolean;
import etapa3.TipoChar;
import etapa3.TipoInt;
import etapa3.TipoNull;
import etapa3.TipoString;

public class Literal extends Operando{

	
	
	public Literal(Token t){
		this.t = t;
	}

	@Override
	public Tipo chequear(Clase clase, Metodo metodo) throws ErrorSemantico {
		
			switch (t.getToken()) {
			
			
			case "LitString":
								return new TipoString(t.getNroLinea());
			case "LitEntero":						
								return new TipoInt(t.getNroLinea());
			case "LitCaracter":
								return new TipoChar(t.getNroLinea());
			case "LitBoolean":
								return new TipoBoolean(t.getNroLinea());
			case "LitNull":
								return new TipoNull(t.getNroLinea());
			default:
					return null;
						
			}
	
	}
	
	
	
	
}
