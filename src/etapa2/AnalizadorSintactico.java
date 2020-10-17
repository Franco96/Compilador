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
	
	

	private void match(String token,String esperaba) throws ErrorSintactico, IOException, ErrorLexico{
		if (token.equals(tokenActual.getToken())) 
			
				tokenActual = aLex.proximoToken();
		else 
			throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba "+esperaba+" se encontro \""+tokenActual.getLexema()+"\""
											+"\n\n[Error:"+tokenActual.getLexema()+"|"+
																		tokenActual.getNroLinea()+"]");
		
	}
	
	private boolean esIgual(String token){
		return token.equals(tokenActual.getToken());
	}
	
	
	
	private void inicial() throws ErrorSintactico, IOException, ErrorLexico{
			clase();
			listaClases();
			match("EOF","fin de archivo");
		
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
		match("T_Class","palabra clave class");
		match("idClase","identificador de clase");
		herencia();
		match("T_llavesIni","{");
		listaMiembros();
		match("T_llavesFin","}");
		
	}
	
	private void herencia() throws ErrorSintactico, IOException, ErrorLexico{
		 
		if(Arrays.asList("T_Extends").contains(tokenActual.getToken())){
			
			match("T_Extends","palabra clave extends");
			match("idClase","identificador de clase");
		}
		else{
			//no hago nada epsilon
		}
		
		
	}
	
	private void listaMiembros() throws ErrorSintactico, IOException, ErrorLexico{
		 if(Arrays.asList("T_Public","T_Private","idClase","T_Static","T_Dynamic").contains(tokenActual.getToken())){
			
			 	miembro();
			 	listaMiembros();
		 }else if(esIgual("T_llavesFin")){
			 //epsilon no se hace nada
		 }else{
			 throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba un tipo de miembro (atributo/constructor/metodo)"
					 + "se encontro \""+tokenActual.getLexema()+"\""
						+"\n\n[Error:"+tokenActual.getLexema()+"|"+
													tokenActual.getNroLinea()+"]");
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
		match("T_PyC",";");
		
	}
	
	private void constructor()throws ErrorSintactico, IOException, ErrorLexico{
		
		match("idClase","identificador de clase");
		argsFormales();
		bloque();

	}
	
	private void metodo()throws ErrorSintactico, IOException, ErrorLexico{
		
		
		match(tokenActual.getToken(),"static o dynamic");  //matcheo con static o dynamic
		tipoMetodo();
		match("idMetVar","identificador de metodo o variable");
		argsFormales();
		bloque();
			
	}
	
	private void tipoMetodo()throws ErrorSintactico, IOException, ErrorLexico{
	
		 if(Arrays.asList("T_Boolean","T_Char","idClase","T_Int","T_String").contains(tokenActual.getToken())){
			 tipo();
		 }else if(esIgual("T_Void")){
			  match("T_Void","palabra clave void");
		 }else{
			 throw new ErrorSintactico("tipo no definido en la declaracion del metodo");
		 }
	}
	
	
	private void visibilidad()throws ErrorSintactico, ErrorLexico, IOException{
	
		if(esIgual("T_Public"))
			match("T_Public","palabra clave public");
		else
			match("T_Private","palabra clave private");		
		
	}
	
	private void tipo()throws ErrorSintactico, ErrorLexico, IOException{
			
				switch(tokenActual.getToken()){
		
				
					case "T_Boolean":
							match("T_Boolean","palabra clave boolean");
							break;
					
					case "T_Char":
						match("T_Char","palabra clave char");
						break;
					
					case "T_Int":
						match("T_Int","palabra clave int");
						break;
					
					case "T_String":
						match("T_String","palabra clave String");
						break;
						
					case "idClase":
						match("idClase","identificador de clase");
						break;

					default: 
							throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba un tipo primitivo o identificador de clase se encontro \""+tokenActual.getLexema()+"\""
								+"\n\n[Error:"+tokenActual.getLexema()+"|"+
									tokenActual.getNroLinea()+"]");


				}
		
		
	}
	
	
	private void listaDeAtribs()throws ErrorSintactico, ErrorLexico, IOException{
		
		match("idMetVar","identificador de metodo o variable");
		listaDeAtribsAux();
		
	}
	
	private void listaDeAtribsAux()throws ErrorSintactico, ErrorLexico, IOException{
		
			if(esIgual("T_coma")){
				match("T_coma",",");
				listaDeAtribs();
			}else{
				 //epsilon no se hace nada
			}
				
	}
	
	
	
	
	private void argsFormales() throws ErrorSintactico, IOException, ErrorLexico{
		
		match("T_parenIni","(");
		listaArgsFormalesOVacio();
		match("T_parenFin",")");
			
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
		
			if(esIgual("T_coma")){
				match("T_coma",",");
				listaArgsFormales();
			}else{
				 //epsilon no se hace nada
			}
				
	}
	
	private void argFormal() throws ErrorSintactico, IOException, ErrorLexico{
			
			tipo();
			match("idMetVar","identificador de metodo o variable");
	}
	
	
	private void bloque() throws ErrorSintactico, IOException, ErrorLexico{
		
		match("T_llavesIni","(");
		
		listaSentencias();
		match("T_llavesFin",")");
		
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
	
	private void sentencia() throws ErrorSintactico, IOException, ErrorLexico{
		
				switch(tokenActual.getToken()){
		
					case "T_PyC":
										match("T_PyC",";");
										break;
					case "T_This":
					case "idMetVar":
					case "T_Static":
					case "T_New":
					case "T_parenIni":
										
										acceso();
										asignacionOLlamada();
										match("T_PyC",";");
										break;
					
					case "T_Boolean":
					case "T_Char":
					case "idClase":
					case "T_Int":
					case "T_String":
										tipo();
										listaDeVars();
										match("T_PyC",";");
										break;
					
					case "T_If":
										match("T_If","palabra clave if");
										match("T_parenIni","(");
										expresion();
										match("T_parenFin",")");
										sentencia();
										conOsinElse();
										break;
					
					case "T_While": 
										match("T_While","palabra clave while");
										match("T_parenIni","(");
										expresion();
										match("T_parenFin",")");
										sentencia();
										break;
										
					case "T_llavesIni":
										bloque();
										break;
										
					case "T_Return": 
						 				match("T_Return","palabra clave return");
						 				expresionOVacio();
						 				match("T_PyC",";");
						 				break;
					
					
					
					
				}
		
	}
	
	private void conOsinElse() throws ErrorSintactico, IOException, ErrorLexico{
		
		if(esIgual("T_Else")){
			match("T_Else","palabra clave else");
			sentencia();
		}
		
	}
	
	private void listaDeVars() throws ErrorSintactico, IOException, ErrorLexico{
		
			match("idMetVar","identificador de metodo o variable");
			listaDeVarsAux();
			
	}
	
	private void listaDeVarsAux() throws ErrorSintactico, IOException, ErrorLexico{
		
		if(esIgual("T_coma")){
			match("T_coma",",");
			listaDeVars();
		}else{
			//epsilon no se hace nada
		}
	}
	
	
	
	
	private void asignacionOLlamada() throws ErrorSintactico, IOException, ErrorLexico{
		 
			if(Arrays.asList("op=","op+=","op-=").contains(tokenActual.getToken())){
				
					match(tokenActual.getToken(),"operador de asignacion (=,-=,+=)");
					expresion();
			}else{
				//epsilon no se hace nada (aca es el caso de una llamada)
			}
		
		
	}
	
	
	
	
	
	
	
	private void acceso() throws ErrorSintactico, IOException, ErrorLexico{
				
				switch(tokenActual.getToken()){
				
					case "T_This":
									match("T_This","palabra clave this");
									break;
					
					case "idMetVar":			
									
									match("idMetVar","identificador de metodo o variable");
									accesoMetOVar();
									break;
					
					case "T_Static":
									
									match("T_Static","static");
									match("idClase","identificador de clase");
									match("T_punto","punto (.)");
									match("idMetVar","identificador de metodo o variable");
									match("T_parenIni","(");
									listaExpsOVacio();
									match("T_parenFin",")");
									break;
					
					case "T_New":	
									
									match("T_New","palabra clave new");
									match("idClase","identificador de clase");
									match("T_parenIni","(");
									listaExpsOVacio();
									match("T_parenFin",")");
									break;
					
					case "T_parenIni":
									
									match("T_parenIni","(");
									expresion();
									match("T_parenFin",")");
									break;
					
				}
				
				
				encadenado();
				
	}
	
	private void encadenado() throws ErrorSintactico, IOException, ErrorLexico{
		
			if(esIgual("T_punto")){
				
				match("T_punto","punto (.)");
				match("idMetVar","identificador de metodo o variable");
				accesoMetOVar();
				encadenado();
			}else{
				//epsilon
			}
	}
	
	
	private void accesoMetOVar() throws ErrorSintactico, IOException, ErrorLexico{
		
		
		if(esIgual( "T_parenIni")){
			
				match("T_parenIni","(");
				listaExpsOVacio();
				match("T_parenFin",")");
		}
	
	}
	
	
	private void listaExpsOVacio() throws ErrorSintactico, IOException, ErrorLexico{
		
		
		if(Arrays.asList("op+","op-","op!","LitNull","LitBoolean","LitEntero","LitCaracter","LitString"
				,"T_This","idMetVar","T_Static","T_New","T_parenIni").contains(tokenActual.getToken())){

			listaExprs();
		
		}else{
			//epsilon no hago nada
		}
	}
	
	private void listaExprs() throws ErrorSintactico, IOException, ErrorLexico{
		
		expresion();
		listaExprsAux();
		
	}
	
	private void listaExprsAux() throws ErrorSintactico, IOException, ErrorLexico{
		
		if(esIgual("T_coma")){
			match("T_coma","coma (,)");
			listaExprs();
		}else{
			//epsilon no se hace nada
		}
		
		
	}
	
	
	
	
	//----------------Metodos para manejar la parte de expresion-------------------------
	
	
	
	private void expresionOVacio() throws ErrorSintactico, IOException, ErrorLexico{
		
		if(Arrays.asList("op+","op-","op!","LitNull","LitBoolean","LitEntero","LitCaracter","LitString"
						,"T_This","idMetVar","T_Static","T_New","T_parenIni").contains(tokenActual.getToken())){
		
				expresion();
		
		}else{
					
				//epsilon no se hace nada
		}
	
	}
	
	
	
	private void expresion() throws ErrorSintactico, IOException, ErrorLexico{
		
				expresionUnaria();
				expresionAux();                    
				
	}
	
	private void expresionAux() throws ErrorSintactico, IOException, ErrorLexico{
	
		if(Arrays.asList("op+","op-","op==","op!=","op<","op>","op<=","op>=","op*","op/","op%","opOR","opAND").contains(tokenActual.getToken())){
					
			match(tokenActual.getToken(),"operador binario"); 
			expresionUnaria();
			expresionAux();
		}else{
			
			//epsilon no se hace nada
		}
		
	}
	
	
	
	
	private void expresionUnaria() throws ErrorSintactico, IOException, ErrorLexico{
		
			
			if(Arrays.asList("op+","op-","op!").contains(tokenActual.getToken())){
					
						match(tokenActual.getToken(),"operador unario");
						operando();
						
			}else if(Arrays.asList("LitNull","LitBoolean","LitEntero","LitCaracter","LitString"
					,"T_This","idMetVar","T_Static","T_New","T_parenIni").contains(tokenActual.getToken())){
					
						operando();
			
			}else{
					throw new ErrorSintactico(tokenActual.getNroLinea()+"-"+tokenActual.getToken()+"-"+tokenActual.getLexema()+"-unaria");
			}
		
	}
	
	
	private void operando() throws ErrorSintactico, IOException, ErrorLexico{
		
		if(Arrays.asList("LitNull","LitBoolean","LitEntero","LitCaracter","LitString").contains(tokenActual.getToken())){
				
						match(tokenActual.getToken(),"litaral");
						
		}else if(Arrays.asList("T_This","idMetVar","T_Static","T_New","T_parenIni").contains(tokenActual.getToken())){
						
						acceso();
		}else{
					
						throw new ErrorSintactico("operando");
		}
		
	}
	
	//------------------------------------------------------------------------------------
}
