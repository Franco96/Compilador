package etapa3;



public class TipoInt extends Tipo{
	
	
	
public TipoInt(int linea){
		
		super(linea);
		nombre = "int";

	}

public boolean conforma(Tipo c) {
    return c instanceof TipoInt;
}

}
