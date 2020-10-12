package etapa2;

import java.io.IOException;
import java.util.Arrays;
import Excepciones.ErrorLexico;
import Excepciones.ErrorSintactico;
import etapa1.*;


public class AnalizadorSintactico {

	private AnalizadorLexico aLex;	
	private Token tokenActual;

	
	public AnalizadorSintactico(AnalizadorLexico aLex) throws IOException, ErrorLexico, ErrorSintactico{
		this.aLex = aLex;
		tokenActual = aLex.proximoToken(); 
		inicial();
	}
	
	

	private void match(String token) throws ErrorSintactico, IOException, ErrorLexico{
		if (token.equals(tokenActual.getToken())) 
			
				tokenActual = aLex.proximoToken();
		else 
			throw new ErrorSintactico(tokenActual.getNroLinea()+": se esperaba "+token+" se encontro "+tokenActual.getLexema()+" "+tokenActual.getToken());
		
	}
	
	private boolean esIgual(String token){
		return Arrays.asList(token).contains(tokenActual.getToken());
	}
	
	
	
	private void inicial() throws ErrorSintactico, IOException, ErrorLexico{
			clase();
			listaClases();
			match("EOF");
		
	}
	
	private void listaClases() throws ErrorSintactico, IOException, ErrorLexico{
		
		 if(Arrays.asList("T_Class").contains(tokenActual.getToken())){
			 clase();
			 listaClases();
		 }else{
			 //epsilon no se hace nada
		 }
		
	}
	
	private void clase() throws ErrorSintactico, IOException, ErrorLexico{
		match("T_Class");
		match("idClase");
		herencia();
		match("T_llavesIni");
		listaMiembros();
		match("T_llavesFin");
		
	}
	
	private void herencia() throws ErrorSintactico, IOException, ErrorLexico{
		 
		if(Arrays.asList("T_Extends").contains(tokenActual.getToken())){
			
			match("T_Extends");
			match("idClase");
		}
		else{
			//no hago nada epsilon
		}
		
		
	}
	
	private void listaMiembros() throws ErrorSintactico, IOException, ErrorLexico{
		 if(Arrays.asList("T_Public","T_Private","idClase","T_Static","T_Dynamic").contains(tokenActual.getToken())){
			
			 	miembro();
			 	listaMiembros();
		 }else{
			 //epsilon no se hace nada
		 }
		
	}
	
	
	private void miembro() throws ErrorSintactico, IOException, ErrorLexico{
		
		
			switch(tokenActual.getToken()){
		
					case "T_Public":
					case "T_Private":
										atributo();
										break;
		
					case "idClase":
										constructor();
										break;	
					case "T_Static":
					case "T_Dynamic":
										metodo();
										break;
					
					default: 
										throw new ErrorSintactico("Inicio Miembro");
			
			
			}
	}
	
	
	private void atributo()throws ErrorSintactico, IOException, ErrorLexico{
		
		visibilidad();
		tipo();
		listaDeAtribs();
		match("T_PyC");
		
	}

	
	private void visibilidad()throws ErrorSintactico, ErrorLexico, IOException{
	
		if(esIgual("T_Public"))
			match("T_Public");
		else
			match("T_Private");		
		
	}
	
	private void tipo()throws ErrorSintactico, ErrorLexico, IOException{
			
				switch(tokenActual.getToken()){
		
				
					case "T_Boolean":
							match("T_Boolean");
							break;
					
					case "T_Char":
						match("T_Char");
						break;
					
					case "T_Int":
						match("T_Int");
						break;
					
					case "T_String":
						match("T_String");
						break;
						
					case "idClase":
						match("idClase");
						break;

					default: 
							throw new ErrorSintactico("No es un tipo definido en la declaracion del atributo");


				}
		
		
	}
	
	
	private void listaDeAtribs()throws ErrorSintactico, ErrorLexico, IOException{
		
		match("idMetVar");
		listaDeAtribsAux();
		
	}
	
	private void listaDeAtribsAux()throws ErrorSintactico, ErrorLexico, IOException{
		
			if(esIgual("T_coma")){
				match("T_coma");
				listaDeAtribs();
			}else{
				 //epsilon no se hace nada
			}
				
	}
	
	private void constructor()throws ErrorSintactico, IOException, ErrorLexico{
			
			match("idClase");
			argsFormales();
			bloque();
	
	}
	
	
	private void argsFormales() throws ErrorSintactico, IOException, ErrorLexico{
		
		match("T_parenIni");
		listaArgsFormalesOVacio();
		match("T_parenFin");
			
	}
	
	private void listaArgsFormalesOVacio() throws ErrorSintactico, IOException, ErrorLexico{
		
		
		 if(Arrays.asList("T_Boolean","T_Char","idClase","T_Int","T_String").contains(tokenActual.getToken()))
				
			 	listaArgsFormales();
		 else{
			 //epsilon no se hace nada
		 }
		
		
	}
	
	
	private void listaArgsFormales() throws ErrorSintactico, IOException, ErrorLexico{
			
			argFormal();
			listaArgsFormalesAux();
	}
	
	
	private void listaArgsFormalesAux() throws ErrorSintactico, IOException, ErrorLexico{
		
			if(esIgual("T_Coma")){
				match("T_Coma");
				listaArgsFormales();
			}else{
				 //epsilon no se hace nada
			}
				
	}
	
	private void argFormal() throws ErrorSintactico, IOException, ErrorLexico{
			
			tipo();
			match("idMetVar");
	}
	
	
	private void bloque() throws ErrorSintactico, IOException, ErrorLexico{
		
		match("T_llavesIni");
		
		listaSentencias();
		match("T_llavesFin");
		
	}
	
	private void listaSentencias() throws ErrorSintactico, IOException, ErrorLexico{
		
		if(Arrays.asList("T_Boolean","T_Char","idClase","T_Int","T_String"
						,"T_PyC","T_This","idMetVar","T_Static","T_New","T_parenIni"
						,"T_If","T_While","T_Return","T_llavesIni").contains(tokenActual.getToken())){
		
						sentencia();
						listaSentencias();
		
		}else{
				//epsilon no se hace nada
		}
		

	}
	
	
	
	
	
	
	
}
