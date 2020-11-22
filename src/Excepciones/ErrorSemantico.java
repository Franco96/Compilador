package Excepciones;

public class ErrorSemantico extends Exception{
	
	private static final long serialVersionUID = 1L;

	public ErrorSemantico(String err){
		super("Error Semantico en la linea "+err);
	}

}
