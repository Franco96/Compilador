package etapa4;



public class SentenciaBloque extends Sentencia{
	
	private Bloque b;

	public SentenciaBloque(Bloque b) {
		this.b=b;
	}

	/*
	public boolean check(Metodo metodoActual) throws SemanticException {
		return b.check(metodoActual);
	}
*/
	
	
	public int getLine() {
		return b.getLine();
	}



}