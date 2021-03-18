package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Tipo;
import etapa3.TipoBoolean;
import etapa3.TipoChar;
import etapa3.TipoInt;
import etapa3.TipoNull;
import etapa3.TipoString;
import etapa5.Generador;

public class Literal extends Operando{

	
	
	public Literal(Token t){
		this.t = t;
	}

	@Override
	public Tipo chequear(Clase clase, Metodo metodo) throws ErrorSemantico {
		
			switch (t.getToken()) {
			
			
			case "LitString":
								return new TipoString(t.getNroLinea());
			case "LitEntero":						
								return new TipoInt(t.getNroLinea());
			case "LitCaracter":
								return new TipoChar(t.getNroLinea());
			case "LitBoolean":
								return new TipoBoolean(t.getNroLinea());
			case "LitNull":
								return new TipoNull(t.getNroLinea());
			default:
					return null;
						
			}
	
	}

	@Override
	public void generarCodigo() {
		
				switch (t.getToken()) {
		
		
				case "LitString":
					
									int indexEtiq = Generador.getGenerador().nuevaEtiquetaDeString();
					
									Generador.getGenerador().gen(".DATA", "# Continuación de la sección de datos para un nuevo String");
									Generador.getGenerador().gen("string_" + indexEtiq + ": DW \"" + t.getLexema() + "\",0","# Defino una etiqueta para un nuevo literal String");	
									Generador.getGenerador().gen(".CODE", "# Continuación de la sección de código para un nuevo String");
									Generador.getGenerador().gen("PUSH string_" + indexEtiq, "# Apilo la etiqueta ligada al nuevo literal String");		
						
									break;
				case "LitEntero":	
					
									Generador.getGenerador().gen("PUSH " + t.getLexema(),"# Apilo el valor del literal entero '" + t.getLexema() + "'");
									break;
				
				case "LitCaracter":
									Generador.getGenerador().gen("PUSH " + t.getLexema(),"# Apilo el valor del literal char '" + t.getLexema() + "'");
									break;
				case "LitBoolean":
									if (t.getLexema().equals("true")) 
										Generador.getGenerador().gen("PUSH 1","# Apilo el literal booleano 'true'");			
					
									else 
										Generador.getGenerador().gen("PUSH 0","# Apilo el literal booleano 'false'");
				
									break;
							
				case "LitNull":
								Generador.getGenerador().gen("PUSH 0","# Apilo un literal nulo");
								
								break;
		}
		
	}
	
	
	
	
}
