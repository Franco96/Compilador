package etapa1;

import java.io.IOException;
import java.util.Map;
import Excepciones.ErrorLexico;

public class AnalizadorLexico {

	private String lexema;
	private int caracterActual;
	private GestorDeArchivo gestorDeFuente;
	private Map<String,String> palabrasClaves;
	private Map<String,String> puntuacion_operadores;
	
	
	public AnalizadorLexico(GestorDeArchivo gestor) throws IOException{
		
			gestorDeFuente = gestor;
			palabrasClaves = new PalabrasClaves().getPalabrasClaves();
			puntuacion_operadores = new Puntuacion_operadores().getPuntuacionOp();
			actualizarCaracterActual();
	}
	
	public Token proximoToken() throws IOException, ErrorLexico{
		
		lexema = "";
		
		return estadoInicial();
	}
	
	private void actualizarLexema(){
		lexema = lexema + (char)caracterActual;
	}
	private void actualizarCaracterActual() throws IOException{
		caracterActual = gestorDeFuente.proximoCaracter();
	}
	
	
	private Token estadoInicial() throws IOException, ErrorLexico{
		
			if(Character.isWhitespace(caracterActual)){
					actualizarCaracterActual();
					return estadoInicial();
		
			}else if(Character.isUpperCase(caracterActual)){
					actualizarLexema();
					actualizarCaracterActual();
					return idClase();
			
			}else if(Character.isLowerCase(caracterActual)){
					actualizarLexema();
					actualizarCaracterActual();
					return idMetVar();
		
			}else if(caracterActual == 47){ // si el caracter es Barra '/'
					actualizarCaracterActual();
					return estadoComentario();
				
			}else if(Character.isDigit(caracterActual)){
					actualizarLexema();
					actualizarCaracterActual();
					return litEntero();
		
			}else if(caracterActual == 39){ // comilla simple
					actualizarCaracterActual();
					return litCaracter();
		
			}else if(caracterActual == 34){ // comilla doble
					actualizarCaracterActual();
					return litString();
				
			}else if(caracterActual == 124){ // barra vertical '|'
					actualizarLexema();
					actualizarCaracterActual();
					return opBarraVertical();
				
			}else if(caracterActual == 38){ //ampersand '&'
					actualizarLexema();
					actualizarCaracterActual();
					return opAmpersand();
			
			}else if(puntuacion_operadores.get(""+(char)caracterActual)!=null){
					actualizarLexema();
					actualizarCaracterActual();
					return puntuacionYop();
		
			}else if(gestorDeFuente.esEOF(caracterActual)){
				
					return new Token("EOF",lexema,gestorDeFuente.getNroLinea());
			}else{
					actualizarLexema();
					throw new ErrorLexico(gestorDeFuente.getNroLinea()+" : "+lexema+" no es un token valido en el lenguaje\n\n"
			
							+"[Error:"+lexema+"|"+gestorDeFuente.getNroLinea()+"]");
			}
		
	}
	
	private Token idClase() throws IOException{
		
		if(Character.isLetter(caracterActual)||Character.isDigit(caracterActual) || caracterActual == 95){
			actualizarLexema();
			actualizarCaracterActual();
			return idClase();
		}else{
			 String nombreToken = palabrasClaves.get(lexema);
			 
			 Token ret = (nombreToken==null)? new Token("idClase", lexema, gestorDeFuente.getNroLinea()): 
				                        new Token(nombreToken, lexema, gestorDeFuente.getNroLinea());
			
			 return ret;
		}
			
	}
	
	private Token idMetVar() throws IOException{
		
		if(Character.isLetter(caracterActual)||Character.isDigit(caracterActual) || caracterActual == 95){
			actualizarLexema();
			actualizarCaracterActual();
			return idMetVar();
		}else{
			 String nombreToken = palabrasClaves.get(lexema);
			 
			 Token ret = (nombreToken==null)? new Token("idMetVar", lexema, gestorDeFuente.getNroLinea()): 
                 						new Token(nombreToken, lexema, gestorDeFuente.getNroLinea());
			 
			 return ret;
		}
			
	}

	private Token estadoComentario() throws IOException, ErrorLexico{
		
		Token ret;
		
		switch(caracterActual){
			
				case 42:	// caracter '*'
						actualizarCaracterActual();
						ret = comentarioMultiLinea();
						break;
				case 47: // caracter '/'
						actualizarCaracterActual();
						ret = comentarioSimple();
						break;
						
				default:
						ret=new Token("op/","/",gestorDeFuente.getNroLinea());
		}
		
		return ret;
		
	}
	
	private Token comentarioMultiLinea() throws IOException, ErrorLexico{
		
		Token ret;
		
		switch(caracterActual){
			
				case 42:	// caracter '*'
						actualizarCaracterActual();
						ret = comentarioMultiLineaCierre();
						break;
				case -1:
						throw new ErrorLexico(gestorDeFuente.getNroLinea()+" : "+lexema+" comentario multilinea sin cerrar\n\n"
									+"[Error:"+lexema+"|"+gestorDeFuente.getNroLinea()+"]");
						
				default:
						actualizarCaracterActual();
						ret = comentarioMultiLinea();						
		}
		
		return ret;
				
	}
	
	private Token comentarioMultiLineaCierre() throws IOException, ErrorLexico{
		
		Token ret;
		
		switch(caracterActual){
			
				case 47:	// caracter '/'
						actualizarCaracterActual();
						ret = estadoInicial();
						break;
				case -1:
						throw new ErrorLexico(gestorDeFuente.getNroLinea()+" : "+lexema+" comentario multilinea sin cerrar\n\n"
								+"[Error:"+lexema+"|"+gestorDeFuente.getNroLinea()+"]");
			
				default:
						actualizarCaracterActual();
						ret = comentarioMultiLinea();					
		}
		
		return ret;
	}

	private Token comentarioSimple() throws IOException, ErrorLexico{
		
			if(caracterActual == 10){
				actualizarCaracterActual();
				return estadoInicial();
			}else if(caracterActual == -1){
					
					return estadoInicial();
					
			}else{
				actualizarCaracterActual();
				return comentarioSimple();
			}
			
	}
	
	private Token litEntero() throws IOException{
			
			if(Character.isDigit(caracterActual)){
				actualizarLexema();
				actualizarCaracterActual();
				return litEntero();
			}else
				return new Token("LitEntero",lexema,gestorDeFuente.getNroLinea());
	}
	
	private Token litCaracter() throws ErrorLexico, IOException{
	
		Token ret;
		
			switch(caracterActual){
			
					case 39: //Comilla simple
						throw new ErrorLexico(gestorDeFuente.getNroLinea()+" : '' no es un caracter valido\n\n"
									+"[Error:"+lexema+"|"+gestorDeFuente.getNroLinea()+"]");
					
					case 92: //barra invertida '\'
							actualizarLexema(); 
							actualizarCaracterActual();
							actualizarLexema();        //Como se permite cualquier caracter actualizo nuevamente
							actualizarCaracterActual();
							ret = litCaracterFin();	
							break;
					default:
							actualizarLexema();
							actualizarCaracterActual();
							ret = litCaracterFin();	
			}
			
			return ret;
	}
	
	private Token  litCaracterFin() throws ErrorLexico, IOException{
		
		if(caracterActual == 39){ //comilla simple 
			actualizarCaracterActual();
			return new Token("LitCaracter", lexema, gestorDeFuente.getNroLinea());
		}else{
			
			throw new ErrorLexico(gestorDeFuente.getNroLinea()+" : '"+lexema+" caracter sin cerrar\n\n"
					+"[Error:"+lexema+"|"+gestorDeFuente.getNroLinea()+"]");
		}
		
	}
	
	private Token litString() throws IOException, ErrorLexico{
		
		Token tok;
		
				switch(caracterActual){
				
						case 34 : //comilla doble
									actualizarCaracterActual();
									tok = new Token("LitString", lexema, gestorDeFuente.getNroLinea());
									break;		
						case 13 : //salto de linea
									throw new ErrorLexico(gestorDeFuente.getNroLinea()+" : \""+lexema+" , String sin cerrar\n\n[Error:"+lexema+"|"+
									gestorDeFuente.getNroLinea()+"]");
						case -1 :
									throw new ErrorLexico(gestorDeFuente.getNroLinea()+" : \""+lexema+" , String sin cerrar\n\n[Error:"+lexema+"|"+
									gestorDeFuente.getNroLinea()+"]");
						default :
									actualizarLexema(); 
									actualizarCaracterActual();
									tok = litString();
				}
				
		return tok;	
	}
	
	private Token opBarraVertical() throws IOException, ErrorLexico{
		
		if(caracterActual == 124){ //barra vertical '|'
			actualizarLexema();
			actualizarCaracterActual();
			return new Token("opOR", lexema, gestorDeFuente.getNroLinea());
		}else{
			
			throw new ErrorLexico(gestorDeFuente.getNroLinea()+" : "+lexema+" no es un caracter valido\n\n[Error:"+lexema+"|"+
																								gestorDeFuente.getNroLinea()+"]");
		}
		
		
	}
	
	private Token opAmpersand() throws IOException, ErrorLexico{
		
		if(caracterActual == 38){ //ampersand '&'
			actualizarLexema();
			actualizarCaracterActual();
			return new Token("opAND", lexema, gestorDeFuente.getNroLinea());
		}else{
			
			throw new ErrorLexico(gestorDeFuente.getNroLinea()+" : "+lexema+" no es un caracter valido\n\n[Error:"+lexema+"|"+
																								gestorDeFuente.getNroLinea()+"]");
		}
		
		
	}
		
	private Token puntuacionYop()throws IOException{
		
			if(caracterActual == 61){ //igual '='
			
						String nombreToken = puntuacion_operadores.get(lexema+(char)caracterActual);
			
						if(nombreToken!=null){
												actualizarLexema();
												actualizarCaracterActual();
												return new Token(puntuacion_operadores.get(lexema),lexema,gestorDeFuente.getNroLinea());
					
						}else
								return new Token(puntuacion_operadores.get(lexema),lexema,gestorDeFuente.getNroLinea());
					
		   }else
				return new Token(puntuacion_operadores.get(lexema),lexema,gestorDeFuente.getNroLinea());
			
	}
		
}

 

