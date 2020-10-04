package etapa1;

public class Token {
	
	private String token;
	private String lexema;
	private int nroLinea;
	
	
	public Token(String token, String lexema, int nroLinea) {
		this.token = token;
		this.lexema = lexema;
		this.nroLinea = nroLinea;
	}


	public String getToken() {
		return token;
	}





	public String getLexema() {
		return lexema;
	}




	public int getNroLinea() {
		return nroLinea;
	}


	

	
}
