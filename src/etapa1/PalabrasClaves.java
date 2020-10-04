package etapa1;

import java.util.HashMap;
import java.util.Map;

public class PalabrasClaves {

	private Map<String,String> palabrasClaves;
	

	public PalabrasClaves(){
		palabrasClaves = new HashMap<String, String>();
		
		palabrasClaves.put("class","T_Class");
		palabrasClaves.put("extends","T_Extends");
		palabrasClaves.put("static","T_Static");
		palabrasClaves.put("dynamic","T_Dynamic");
		
		palabrasClaves.put("void","T_Void");
		palabrasClaves.put("boolean","T_Boolean");
		palabrasClaves.put("char","T_Char");
		palabrasClaves.put("int","T_Int");
		palabrasClaves.put("String","T_String");
		
		palabrasClaves.put("public","T_Public");
		palabrasClaves.put("private","T_Private");
		
		palabrasClaves.put("if","T_If");
		palabrasClaves.put("else","T_Else");
		palabrasClaves.put("while","T_While");
		palabrasClaves.put("return","T_Return");
		
		palabrasClaves.put("this","T_This");
		palabrasClaves.put("new","T_New");
		palabrasClaves.put("null","LitNull");
		palabrasClaves.put("true","LitBoolean");
		palabrasClaves.put("false","LitBoolean");
			
	}
	
	
	public Map<String, String> getPalabrasClaves() {
		return palabrasClaves;
	}

	
	
	
	
	
	
}
