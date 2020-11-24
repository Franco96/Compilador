package etapa3;



public class TipoString extends Tipo{
	
	
public TipoString(int linea){
		
		super(linea);
		nombre = "String";
		
		
	}


	public boolean conforma(Tipo c) {
		return c instanceof TipoString;
	}

}
