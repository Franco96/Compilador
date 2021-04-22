package etapa2;

import java.io.File;
import java.io.IOException;
import Excepciones.ErrorLexico;
import Excepciones.ErrorSemantico;
import Excepciones.ErrorSintactico;
import etapa1.*;
import etapa3.*;



public class Principal {
	
	

	public static void main(String[] args) {
		
		
		if(true){//args.length > 0) {
		
		
					GestorDeArchivo gestorDeFuente;
		
					try {
						
							//Vacia la tabla de simbolos por si de ejecucion en ejecucion del main no se vacia
							TablaDeSimbolos.getTablaDeSimbolos().limpiar(); 
			
							gestorDeFuente = new GestorDeArchivo("C:/Users/franc/OneDrive/Escritorio/Compiladores/Entregas/Lautaro-Carranza-Etapa5/"
														+ "TEST/SentenciaLlamada/AccesoStatico/Test_0.java");
			
							AnalizadorLexico anlex = new AnalizadorLexico(gestorDeFuente);
		
							AnalizadorSintactico anSic = new AnalizadorSintactico(anlex);
												
							anSic.inicial();
							
							TablaDeSimbolos.getTablaDeSimbolos().controlDeclaracion();
							TablaDeSimbolos.getTablaDeSimbolos().controlSentencia();
							TablaDeSimbolos.getTablaDeSimbolos().generarCodigo("miSalida"); //El nombre despues lo cambio por parametro main
							

							
							
							
							System.out.println("Compilacion Exitosa\n\n[SinErrores]");
							
							
							gestorDeFuente.cerrarArchivo();
							
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ErrorLexico e) {
					    System.out.println(e.getMessage());
					} catch (ErrorSintactico e) {
						System.out.println(e.getMessage());
					} catch (ErrorSemantico e){
						System.out.println(e.getMessage());
					}
		
		
		}else
			 System.out.println("Error: Faltan  Parametros de entrada");
		
	}
	

	
	
}


