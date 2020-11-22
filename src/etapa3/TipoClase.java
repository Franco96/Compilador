package etapa3;

import etapa1.Token;

public class TipoClase extends Tipo{
	
	
	
public TipoClase(Token t){
		
		super(t.getNroLinea());
		nombre = t.getLexema();
		
	}

}
