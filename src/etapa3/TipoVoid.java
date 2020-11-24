package etapa3;



public class TipoVoid extends Tipo{

	
	public TipoVoid(int linea){
		super(linea);
		nombre = "void";
	
		
	}
		
	
	 public boolean conforma(Tipo c) {
         return c instanceof TipoVoid;
 }
}
