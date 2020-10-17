package etapa2;

import java.io.IOException;

import Excepciones.ErrorLexico;
import Excepciones.ErrorSintactico;
import etapa1.*;


public class Principal {

	public static void main(String[] args) {
		
		GestorDeArchivo gestorDeFuente;
		try {
			
			
			gestorDeFuente = new GestorDeArchivo("C:/Users/franc/OneDrive/Escritorio/01.LllamadasSinEnc");
		
			AnalizadorLexico anlex = new AnalizadorLexico(gestorDeFuente);
		
			AnalizadorSintactico anSic = new AnalizadorSintactico(anlex);
			
			System.out.println("sistacticamente correcto");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorLexico e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorSintactico e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());

	}
		
	}
}


