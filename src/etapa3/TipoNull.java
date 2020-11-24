package etapa3;

public class TipoNull extends Tipo{

	public TipoNull(int linea) {
		super(linea);
		nombre = "null";
		
		
	}

	//Tipo null conforma con cualquier tipo de clase
	public boolean conforma(Tipo c){
		return true;
	}
	
}
