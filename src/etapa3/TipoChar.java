package etapa3;



public class TipoChar extends Tipo{
	
public TipoChar(int linea){
		
		super(linea);
		nombre = "char";
			
	}

public boolean conforma(Tipo c) {
    return c instanceof TipoChar;
}

}
