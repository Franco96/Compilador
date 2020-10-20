package etapa2;

import java.io.IOException;

import Excepciones.ErrorLexico;
import Excepciones.ErrorSintactico;
import etapa1.*;


public class Principal {

	public static void main(String[] args) {
		
		GestorDeArchivo gestorDeFuente;
		try {
			
			
			gestorDeFuente = new GestorDeArchivo("C:/Users/franc/OneDrive/Escritorio/archivos.java");
		
			AnalizadorLexico anlex = new AnalizadorLexico(gestorDeFuente);
		
			AnalizadorSintactico anSic = new AnalizadorSintactico(anlex);
			
			System.out.println("sistacticamente correcto");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ErrorLexico e) {
			System.out.println(e.getMessage());
			
		} catch (ErrorSintactico e) {
			
			System.out.println(e.getMessage());

	}
		
	}
}


