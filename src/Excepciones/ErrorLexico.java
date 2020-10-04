package Excepciones;

public class ErrorLexico extends Exception {
	
	
	
	private static final long serialVersionUID = 1L;

	public ErrorLexico(String err){
		super("Error Lexico en la linea "+err);
	}
}
