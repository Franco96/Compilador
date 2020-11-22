package etapa3;

public abstract class Tipo {

	protected String nombre;
	protected int linea;
	
	
	public Tipo(int linea){
		
		this.linea = linea;
	}
	
	public String getNombre() {
		return nombre;
	}

	public int getLinea() {
		return linea;
	}
	
	
	

}
