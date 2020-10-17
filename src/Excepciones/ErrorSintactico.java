package Excepciones;

public class ErrorSintactico extends Exception{
	
	

	private static final long serialVersionUID = 1L;

	public ErrorSintactico(String err){
		super("Error Sintactico en la linea "+err);
	}

}
