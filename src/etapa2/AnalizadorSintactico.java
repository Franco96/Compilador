package etapa2;

import java.io.IOException;
import java.util.Arrays;

import Excepciones.*;
import etapa1.*;
import etapa3.*;
import etapa4.*;



public class AnalizadorSintactico {
	

	private AnalizadorLexico aLex;	
	private Token tokenActual;
	private TablaDeSimbolos ts;

	
	
	public AnalizadorSintactico(AnalizadorLexico aLex) throws IOException, ErrorLexico, ErrorSintactico{
		this.aLex = aLex;
		tokenActual = aLex.proximoToken(); 
		ts = TablaDeSimbolos.getTablaDeSimbolos();
		
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
	
	
	

	public void inicial() throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
			clase();
			listaClases();
			match("EOF","fin de archivo");
		
	}
	
	private void listaClases() throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
		
		 if(Arrays.asList("T_Class").contains(tokenActual.getToken())){
			 clase();
			 listaClases();
		 }else if(esIgual("EOF")){
			 //epsilon no se hace nada
		 }else{
			 throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba palabra clave class se encontro \""+tokenActual.getLexema()+"\""
						+"\n\n[Error:"+tokenActual.getLexema()+"|"+
													tokenActual.getNroLinea()+"]");
		 }
		
	}

	private void clase() throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
		
		Token aux;
		
		match("T_Class","palabra clave class");
		aux = tokenActual;
		match("idClase","identificador de clase");
		
		Clase clase = new Clase(aux);
		ts.setClaseActual(clase);
		Token hereda = herencia();
		ts.getClaseActual().setHereda(hereda);
		match("T_llavesIni","{");
		listaMiembros();
		match("T_llavesFin","}");
		
		ts.insertarClase(clase);
		
		
	}
	
	private Token herencia() throws ErrorSintactico, IOException, ErrorLexico{
		 
		if(Arrays.asList("T_Extends").contains(tokenActual.getToken())){
			
			match("T_Extends","palabra clave extends");
			Token hereda = tokenActual;
			match("idClase","identificador de clase");
			
			return hereda;
		}
		else if(esIgual("T_llavesIni")){
			
			return  new Token("idClase", "Object", 0);
			
		}else{
			throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba palabra clave extends se encontro \""+tokenActual.getLexema()+"\""
					+"\n\n[Error:"+tokenActual.getLexema()+"|"+
												tokenActual.getNroLinea()+"]");
		}
		
		
	}
	
	private void listaMiembros() throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
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
	
	
	private void miembro() throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
		
		
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
					
			
			}
	}
	
	
	private void atributo()throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
		String visibilidad = tokenActual.getLexema();    
		match(tokenActual.getToken(),"public o private");  //matcheo con public o private
		Tipo tipo = tipo();
		listaDeAtribs(tipo,visibilidad);
		match("T_PyC",";");
		
	}
	
	private void constructor()throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
		Token aux = tokenActual;
		match("idClase","identificador de clase");
		ts.getClaseActual().insertarConstructor(aux, new TipoClase(aux));
		ts.setMetodoActual(ts.getClaseActual().getConstructor());
		argsFormales();
		Bloque bloque = bloque();
		ts.getClaseActual().getConstructor().setBloque(bloque);
	}
	
	private void metodo()throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
		
		String formaMetodo = tokenActual.getLexema();
		match(tokenActual.getToken(),"static o dynamic");  //matcheo con static o dynamic
		Tipo tipo = tipoMetodo();
		Token aux = tokenActual;
		match("idMetVar","identificador de metodo o variable");
		Metodo m = new Metodo(aux, formaMetodo, tipo);
		ts.setMetodoActual(m);
		argsFormales();
		Bloque bloque = bloque();
		m.setBloque(bloque);
		ts.getClaseActual().insetarMetodo(m);	
	}
	
	private Tipo tipoMetodo()throws ErrorSintactico, IOException, ErrorLexico{
	
		 if(Arrays.asList("T_Boolean","T_Char","idClase","T_Int","T_String").contains(tokenActual.getToken())){
			return tipo();
		 }else if(esIgual("T_Void")){
			  Token aux = tokenActual;
			  match("T_Void","palabra clave void");
			 
			 return new TipoVoid(aux.getNroLinea()); 
		 }else{
			 throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba un tipo primitivo,identificador de clase o palabra reservada void se encontro \""+tokenActual.getLexema()+"\""
						+"\n\n[Error:"+tokenActual.getLexema()+"|"+
							tokenActual.getNroLinea()+"]");
		 }
	}
	
	
	
	
	private Tipo tipo()throws ErrorSintactico, ErrorLexico, IOException{
			
		Tipo tipo = null;
		    
				switch(tokenActual.getToken()){
		
				
					case "T_Boolean":
							tipo = new TipoBoolean(tokenActual.getNroLinea());
							match("T_Boolean","palabra clave boolean");
							break;
					
					case "T_Char":
							tipo = new TipoChar(tokenActual.getNroLinea());
							match("T_Char","palabra clave char");
							break;
					
					case "T_Int":
							tipo = new TipoInt(tokenActual.getNroLinea());
							match("T_Int","palabra clave int");
							break;
					
					case "T_String":
							tipo = new TipoString(tokenActual.getNroLinea());
							match("T_String","palabra clave String");
							break;
						
					case "idClase":
							tipo = new TipoClase(tokenActual);
							match("idClase","identificador de clase");
							break;

					default: 
							throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba un tipo primitivo o identificador de clase se encontro \""+tokenActual.getLexema()+"\""
								+"\n\n[Error:"+tokenActual.getLexema()+"|"+
									tokenActual.getNroLinea()+"]");
							

				}
				
				
				return tipo;
		
		
	}
	
	
	private void listaDeAtribs(Tipo tipo,String visibilidad)throws ErrorSintactico, ErrorLexico, IOException, ErrorSemantico{
		
		Token aux = tokenActual;
		match("idMetVar","identificador de metodo o variable");
		Atributo atrib = new Atributo(aux, visibilidad, tipo);
		ts.getClaseActual().insertarAtributo(atrib);
		listaDeAtribsAux(tipo,visibilidad);
		
	}
	
	private void listaDeAtribsAux(Tipo tipo,String visibilidad)throws ErrorSintactico, ErrorLexico, IOException, ErrorSemantico{
		
			if(esIgual("T_coma")){
				match("T_coma",",");
				listaDeAtribs(tipo,visibilidad);
			}else if (esIgual("T_PyC")){
				 //epsilon no se hace nada
			}else{
				throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba una Coma \",\" o "
						+ "Punto y coma \";\" para finalizar la declaracion del atributo "
						+ "se encontro \""+tokenActual.getLexema()+"\""
						+"\n\n[Error:"+tokenActual.getLexema()+"|"+
							tokenActual.getNroLinea()+"]");
			}
				
	}
	
	
	
	
	private void argsFormales() throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
		
		match("T_parenIni","(");
		listaArgsFormalesOVacio();
		match("T_parenFin",")");
			
	}
	
	
	
	private void listaArgsFormalesOVacio() throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
		
		
		 if(Arrays.asList("T_Boolean","T_Char","idClase","T_Int","T_String").contains(tokenActual.getToken()))
				
			 	listaArgsFormales(0);
		 else if(esIgual("T_parenFin")){
			 //epsilon no se hace nada
		 }else{
			 throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba un tipo de dato primitivo o identificador de clase"
			 		+ " se encontro \""+tokenActual.getLexema()+"\""
						+"\n\n[Error:"+tokenActual.getLexema()+"|"+
													tokenActual.getNroLinea()+"]");
		 }
		
		
	}
	
	
	private void listaArgsFormales(int indice) throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
			
			argFormal(indice);
			listaArgsFormalesAux(indice);
			
	
	}
	

	private void listaArgsFormalesAux(int indice) throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
		
			if(esIgual("T_coma")){
				match("T_coma",",");
				listaArgsFormales(indice+1);
			}else if(esIgual("T_parenFin")){
				 //epsilon no se hace nada
			 }else{
				 throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba una Coma \",\" o parentesis de cierre \")\""
				 		+ " se encontro \""+tokenActual.getLexema()+"\""
							+"\n\n[Error:"+tokenActual.getLexema()+"|"+
														tokenActual.getNroLinea()+"]");
			 }
				
	}
	
	
	
	
	

	private void argFormal(int indice) throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
			
			Tipo tipo = tipo();
			Token aux = tokenActual;
			match("idMetVar","identificador de metodo o variable");
			Parametro p = new Parametro(aux, tipo, indice);
			ts.getMetodoActual().insertarParametros(p);
	}
	
	
	private Bloque bloque() throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
		
		match("T_llavesIni","{");
		Bloque bloque = new Bloque();
		TablaDeSimbolos.getTablaDeSimbolos().setBloqueActual(bloque);
		listaSentencias();
		match("T_llavesFin","}");
		return bloque;
	}
	
	
	private void listaSentencias() throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
		
		
		
		if(Arrays.asList("T_Boolean","T_Char","idClase","T_Int","T_String"
						,"T_PyC","T_This","idMetVar","T_Static","T_New","T_parenIni"
						,"T_If","T_While","T_Return","T_llavesIni").contains(tokenActual.getToken())){
			
						Sentencia sentencia = null;
					    sentencia = sentencia();
					    
					    if(sentencia!=null)
					    TablaDeSimbolos.getTablaDeSimbolos().getBloqueActual().addSent(sentencia);
						listaSentencias();
		
		}else if (esIgual("T_llavesFin")){
				//epsilon no se hace nada
		}else{
			 throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba una sentencia"
				 		+ " se encontro \""+tokenActual.getLexema()+"\""
							+"\n\n[Error:"+tokenActual.getLexema()+"|"+
														tokenActual.getNroLinea()+"]");
		}
		

	}
	
	
	private Sentencia sentencia() throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
				
				Sentencia sent=null;
				
			
				switch(tokenActual.getToken()){
		
					case "T_PyC":
										
										match("T_PyC",";");
										sent = new SentenciaVacia();
										break;
					case "T_This":
					case "idMetVar":
					case "T_Static":
					case "T_New":
					case "T_parenIni":
										
										Acceso acceso = acceso();
										SentenciaAsignacion senAsig = asignacionOLlamada(acceso);
										match("T_PyC",";");
										
										sent = (senAsig == null)? new SentenciaLlamada(acceso): senAsig;
										break;
					
					case "T_Boolean":
					case "T_Char":
					case "idClase":
					case "T_Int":
					case "T_String":
										Tipo tipo = tipo();
										listaDeVars(tipo);
										match("T_PyC",";");
										break;
					
					case "T_If":
										match("T_If","palabra clave if");
										match("T_parenIni","(");
										Expresion condicionIf = expresion();
										match("T_parenFin",")");
										Sentencia cuerpoIf = sentencia();
										Sentencia cuerpoElse = conOsinElse();
										sent = new SentenciaIf(condicionIf, cuerpoIf, cuerpoElse);
										break;
					
					case "T_While": 
										match("T_While","palabra clave while");
										match("T_parenIni","(");
										Expresion condicionWhile = expresion();
										match("T_parenFin",")");
										Sentencia cuerpoWhile = sentencia();
										sent = new SentenciaWhile(condicionWhile, cuerpoWhile);
										break;
										
					case "T_llavesIni":
										Bloque bloqueAux = TablaDeSimbolos.getTablaDeSimbolos().getBloqueActual();
										
										Bloque bloque = bloque();
										
										TablaDeSimbolos.getTablaDeSimbolos().setBloqueActual(bloqueAux);
										sent = new SentenciaBloque(bloque);
										break;
										
					case "T_Return": 
						 				match("T_Return","palabra clave return");
						 				Expresion exp = expresionOVacio();
						 				match("T_PyC",";");
						 				sent = new SentenciaReturn(exp);
						 				break;
						 				
					default :
								throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba una sentencia"
											+ " se encontro \""+tokenActual.getLexema()+"\""
											+"\n\n[Error:"+tokenActual.getLexema()+"|"+
																	tokenActual.getNroLinea()+"]");
				}
				
				
			return sent;
		
	}
	
	
	private Sentencia conOsinElse() throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
		
		Sentencia sent = null;
		
		if(esIgual("T_Else")){
			match("T_Else","palabra clave else");
			sent = sentencia();
		}
		
		return sent;
	}
	
	
	private void listaDeVars(Tipo tipo) throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
			
			Token id = tokenActual;
			match("idMetVar","identificador de metodo o variable");
			Variable var = new Variable(id, tipo);
			TablaDeSimbolos.getTablaDeSimbolos().getMetodoActual().insertarVariable(var);
			listaDeVarsAux(tipo);
			
	}
	
	private void listaDeVarsAux(Tipo tipo) throws ErrorSintactico, IOException, ErrorLexico, ErrorSemantico{
		
		if(esIgual("T_coma")){
			match("T_coma",",");
			listaDeVars(tipo);
		}else if(esIgual("T_PyC")){
			//epsilon no se hace nada
		}else{
			throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba una Coma \",\" o Punto y coma \";\" para finalizar la sentencia"
			 		+ " se encontro \""+tokenActual.getLexema()+"\""
						+"\n\n[Error:"+tokenActual.getLexema()+"|"+
													tokenActual.getNroLinea()+"]");
		}
			
	}
	
	
	
	
	private SentenciaAsignacion asignacionOLlamada(Acceso acceso) throws ErrorSintactico, IOException, ErrorLexico{
		 
		SentenciaAsignacion senAsig = null;
		
		
			if(Arrays.asList("op=","op+=","op-=").contains(tokenActual.getToken())){
					
					Token tipoAsig = tokenActual;
					match(tokenActual.getToken(),"operador de asignacion (=,-=,+=)");
					Expresion exp = expresion();
					
					senAsig = new SentenciaAsignacion(tipoAsig, acceso, exp);
			}
			
			
			return senAsig;
		
	}
	
	
	
	
	
	
	
	private Acceso acceso() throws ErrorSintactico, IOException, ErrorLexico{
				
		Acceso acceso = null;
		Token aux;
		
				switch(tokenActual.getToken()){
				
					case "T_This":
									aux = tokenActual;
									match("T_This","palabra clave this");
									acceso = new AccesoThis(aux);
									break;
					
					case "idMetVar":			
									aux = tokenActual;
									match("idMetVar","identificador de metodo o variable");
									AccesoMetodo actMet =  accesoMetOVar(aux); 
									acceso = (actMet == null)? new AccesoVar(aux) : actMet;
									break;
					
					case "T_Static":
									
									match("T_Static","static");
									aux = tokenActual;
									match("idClase","identificador de clase");
									AccesoEstatico actEstatic = new AccesoEstatico(aux); 
									match("T_punto","punto (.)");
									aux = tokenActual;
									match("idMetVar","identificador de metodo o variable");
									AccesoMetodo actMetodo = new AccesoMetodo(aux);  
									match("T_parenIni","(");
									listaExpsOVacio(actMetodo);	
									match("T_parenFin",")");
									actEstatic.setAccesoMet(actMetodo);
									acceso = actEstatic;
									break;
					
					case "T_New":	
									
									match("T_New","palabra clave new");
									aux = tokenActual;
									match("idClase","identificador de clase");
									AccesoConstructor aconst = new AccesoConstructor(aux);
									match("T_parenIni","(");
									listaExpsOVacio(aconst);	
									match("T_parenFin",")");
									acceso = aconst;
									break;
					
					case "T_parenIni":
									aux = tokenActual; 
									match("T_parenIni","("); 
									Expresion expresion = expresion();
									match("T_parenFin",")");
									acceso = new AccesoParentizado(aux, expresion);
									break;
					
				}
				
				
				acceso.setEncadenado(encadenado());
				
				
				return acceso;
				
	}

	private Encadenado encadenado() throws ErrorSintactico, IOException, ErrorLexico{
		
		Encadenado enca = null;
		
			if(esIgual("T_punto")){
				
				match("T_punto","punto (.)");
				Token id = tokenActual;
				match("idMetVar","identificador de metodo o variable");
				
				AccesoMetodo actMet = accesoMetOVar(id);
				
				enca = (actMet == null)? new VarEncadenada(id) : new MetEncadenado(id,actMet);
				
				enca.setSiguiente(encadenado());
			}
			
			
			return enca;
	}
	
	
	private AccesoMetodo accesoMetOVar(Token token) throws ErrorSintactico, IOException, ErrorLexico{
		
		AccesoMetodo accMet = null;
		if(esIgual( "T_parenIni")){
				
				accMet = new AccesoMetodo(token);
				match("T_parenIni","(");
				listaExpsOVacio(accMet);
				match("T_parenFin",")");
		}
		
		return accMet;
	
	}
	
	
	private void listaExpsOVacio(AccesoMetodo accMet) throws ErrorSintactico, IOException, ErrorLexico{
		
		
		if(Arrays.asList("op+","op-","op!","LitNull","LitBoolean","LitEntero","LitCaracter","LitString"
				,"T_This","idMetVar","T_Static","T_New","T_parenIni").contains(tokenActual.getToken())){

			listaExprs(accMet);
		
		}else if (esIgual("T_parenFin")){
			//epsilon no hago nada
		}else{
			throw new ErrorSintactico(tokenActual.getNroLinea()+" : Se esperaba argumento actual"
			 		+ " se encontro \""+tokenActual.getLexema()+"\""
						+"\n\n[Error:"+tokenActual.getLexema()+"|"+
													tokenActual.getNroLinea()+"]");
		}
	}
	
	
	
	private void listaExprs(AccesoMetodo accMet) throws ErrorSintactico, IOException, ErrorLexico{
		
		Expresion exp = expresion();
		accMet.addArgumentos(exp);
		listaExprsAux(accMet);
		
	}
	
	private void listaExprsAux(AccesoMetodo accMet) throws ErrorSintactico, IOException, ErrorLexico{
		
		if(esIgual("T_coma")){
			match("T_coma","coma (,)");
			listaExprs(accMet);
		}else if (esIgual("T_parenFin")){
			//epsilon no hago nada
		}else{
			throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba una Coma \",\" o parentesis de cierre \")\""
			 		+ " se encontro \""+tokenActual.getLexema()+"\""
						+"\n\n[Error:"+tokenActual.getLexema()+"|"+
													tokenActual.getNroLinea()+"]");
		}
		
	}
	
	
	
	

	
	

	private Expresion expresionOVacio() throws ErrorSintactico, IOException, ErrorLexico{
		
		Expresion exp = null;
		
		if(Arrays.asList("op+","op-","op!","LitNull","LitBoolean","LitEntero","LitCaracter","LitString"
						,"T_This","idMetVar","T_Static","T_New","T_parenIni").contains(tokenActual.getToken())){
		
				exp = expresion();
		
		}if (esIgual("T_PyC")){
			 //epsilon no se hace nada
		}else{
			throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba retornar una expresion "
					+ "se encontro \""+tokenActual.getLexema()+"\""
					+"\n\n[Error:"+tokenActual.getLexema()+"|"+
						tokenActual.getNroLinea()+"]");
		}
		
		return exp;
		
		
	
	}
	
	
	
	private Expresion expresion() throws ErrorSintactico, IOException, ErrorLexico{
		
	
		
		ExpresionUnaria expUni = expresionUnaria();
				
		ExpresionBinaria expBinAux=  expresionAux(expUni);
		
		
				
		return (expBinAux ==null)? expUni:expBinAux;                    
				
	}
	
	
	private ExpresionBinaria expresionAux(Expresion exp) throws ErrorSintactico, IOException, ErrorLexico{
	
		Token operador;
		
		if(Arrays.asList("op+","op-","op==","op!=","op<","op>","op<=","op>=","op*","op/","op%","opOR","opAND").contains(tokenActual.getToken())){
			
			operador = tokenActual;
			match(tokenActual.getToken(),"operador binario"); 
			
			return new ExpresionBinaria(exp ,expresion(), operador);
			
		}else if(Arrays.asList("T_PyC","T_coma","T_llavesFin","T_parenFin").contains(tokenActual.getToken())) {
			
			return null;
		}else{
			throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba un operador binario "
					+ "se encontro \""+tokenActual.getLexema()+"\""
					+"\n\n[Error:"+tokenActual.getLexema()+"|"+
						tokenActual.getNroLinea()+"]");
		}
		
	}
	
	
	
	
	private ExpresionUnaria expresionUnaria() throws ErrorSintactico, IOException, ErrorLexico{
		
			ExpresionUnaria expUni;
			Token operadorUnario = null;
		
			if(Arrays.asList("op+","op-","op!").contains(tokenActual.getToken())){
						
						operadorUnario = tokenActual;
						match(tokenActual.getToken(),"operador unario");
						
			}
			
			if(Arrays.asList("LitNull","LitBoolean","LitEntero","LitCaracter","LitString"
					,"T_This","idMetVar","T_Static","T_New","T_parenIni").contains(tokenActual.getToken())){
					
						Operando operando = operando();
						expUni = new ExpresionUnaria(operando);
						
						if(operadorUnario!=null)
							expUni.setOperador(operadorUnario);
			
			}else{
				throw new ErrorSintactico(tokenActual.getNroLinea()+" : se esperaba un operando con un tipo de operador unario de ser necesario "
						+ "se encontro \""+tokenActual.getLexema()+"\""
						+"\n\n[Error:"+tokenActual.getLexema()+"|"+
							tokenActual.getNroLinea()+"]");
			}
			
			return expUni;
		
	}
	
	
	private Operando operando() throws ErrorSintactico, IOException, ErrorLexico{
		
		Token token;
		if(Arrays.asList("LitNull","LitBoolean","LitEntero","LitCaracter","LitString").contains(tokenActual.getToken())){
						
						token  = tokenActual;
						match(tokenActual.getToken(),"litaral");
						
						return new Literal(token);
						
		}else //if(Arrays.asList("T_This","idMetVar","T_Static","T_New","T_parenIni").contains(tokenActual.getToken())){
						
						
						return acceso();
						

		
	}
	
}
