package etapa3;


public class TipoBoolean extends Tipo{
	
	
	public TipoBoolean(int linea){
		
		super(linea);
		nombre = "boolean";
		
	}
	
	 public boolean conforma(Tipo c) {
         return c instanceof TipoBoolean;
 }

}
