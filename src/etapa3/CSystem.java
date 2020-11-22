package etapa3;

import Excepciones.ErrorSemantico;
import etapa1.Token;

public class CSystem {

	
	public CSystem() {

	}

	public void init() throws ErrorSemantico {
		
		Clase clase = new Clase(new Token("idClase", "System", 0)); 
				
		clase.setHereda(new Token("idClase","Object",0));
		     
		
		 Metodo read = new Metodo(new Token("idMetVar", "read", 0), "static", new TipoInt(0));
		 Metodo printB = new Metodo(new Token("idMetVar", "printB", 0), "static", new TipoVoid(0));
		 printB.insertarParametros(new Parametro(new Token("idMetVar","b",0), new TipoBoolean(0), 0));	
		 Metodo printC = new Metodo(new Token("idMetVar", "printC", 0), "static", new TipoVoid(0));	
		 printC.insertarParametros(new Parametro(new Token("idMetVar","c",0), new TipoChar(0), 0));	
		 Metodo printI = new Metodo(new Token("idMetVar", "printI", 0), "static", new TipoVoid(0));	
		 printI.insertarParametros(new Parametro(new Token("idMetVar","i",0), new TipoInt(0), 0));	
		 Metodo printS = new Metodo(new Token("idMetVar", "printS", 0), "static", new TipoVoid(0));	
		 printS.insertarParametros(new Parametro(new Token("idMetVar","s",0), new TipoString(0), 0));
		 Metodo println = new Metodo(new Token("idMetVar", "println", 0), "static", new TipoVoid(0));
		 Metodo printBln = new Metodo(new Token("idMetVar", "printBln", 0), "static", new TipoVoid(0));	
		 printBln.insertarParametros(new Parametro(new Token("idMetVar","b",0), new TipoBoolean(0), 0));	
		 Metodo printCln = new Metodo(new Token("idMetVar", "printCln", 0), "static", new TipoVoid(0));	
		 printCln.insertarParametros(new Parametro(new Token("idMetVar","c",0), new TipoChar(0), 0));	
		 Metodo printIln = new Metodo(new Token("idMetVar", "printIln", 0), "static", new TipoVoid(0));	
		 printIln.insertarParametros(new Parametro(new Token("idMetVar","i",0), new TipoInt(0), 0));	
		 Metodo printSln = new Metodo(new Token("idMetVar", "printSln", 0), "static", new TipoVoid(0));	
		 printSln.insertarParametros(new Parametro(new Token("idMetVar","s",0), new TipoString(0), 0));	
		
		 clase.insetarMetodo(read);
		 clase.insetarMetodo(printB);
		 clase.insetarMetodo(printC);
		 clase.insetarMetodo(printI);
		 clase.insetarMetodo(printS);
		 clase.insetarMetodo(println);
		 clase.insetarMetodo(printBln);
		 clase.insetarMetodo(printCln);
		 clase.insetarMetodo(printIln);
		 clase.insetarMetodo(printSln);
		
		 TablaDeSimbolos.getTablaDeSimbolos().insertarClase(clase);
	}
	
}
