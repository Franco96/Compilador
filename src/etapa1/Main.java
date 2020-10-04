package etapa1;


import java.io.FileNotFoundException;
import java.io.IOException;

import Excepciones.ErrorLexico;

public class Main {

	  
	
	public static void main(String[] args) {
		
		if(args.length > 0) {
  
				
				try {
						GestorDeArchivo gestorDeFuente = new GestorDeArchivo(args[0]);
			
						AnalizadorLexico anlex = new AnalizadorLexico(gestorDeFuente);
			
						Token tok = anlex.proximoToken();
			
						String tokenId = tok.getToken();
			
						
						while(tokenId!="EOF"){
				
								System.out.println("("+tok.getToken()+","+tok.getLexema()+","+tok.getNroLinea()+")");
								tok = anlex.proximoToken();
								tokenId = tok.getToken();
						}
				
						
					} catch (FileNotFoundException e) {
							e.printStackTrace();
					
					}catch (IOException e) {
							e.printStackTrace();
					
					} catch (ErrorLexico e) {
			
					System.out.println(e.getMessage());
		
					}
        

		}else
			System.out.println("Error: Faltan  Parametros de entrada");
		
	}
	
}
