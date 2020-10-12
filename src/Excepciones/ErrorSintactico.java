package Excepciones;

public class ErrorSintactico extends Exception{
	
	
	
	public ErrorSintactico(String err){
		super("Error Sintactico en linea "+err);
	}

}
