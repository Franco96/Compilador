package etapa3;

import etapa1.Token;

public class TipoClase extends Tipo{
	
	
	
public TipoClase(Token t){
		
		super(t.getNroLinea());
		nombre = t.getLexema();
		
	}

public boolean conforma(Tipo c){
	if (c instanceof TipoNull || nombre.equals("Object"))
		return true;

	if (c instanceof TipoClase){
		Clase clase = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(c.getNombre());
		Clase thisClass = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(this.nombre);
		
		
		return thisClass.esAncestro(clase.getNombre());
	}
	return false;
}

}
