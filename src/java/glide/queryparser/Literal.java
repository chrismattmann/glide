package glide.queryparser;

/**
 * <p>A Literal represents a value in the keyword=value query string.</p>
 * 
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */


public class Literal implements IExpression{
	protected String literalValue=null;
	
	/**
	 * <p>Default c'tor
	 * 
	 * @param t The literal value
	 */
	public Literal(String t){
		literalValue=t;
	}
	
	public String getExpressionType(){return "LIT";}
	public String getValue(){return literalValue;}
	public void setProfileElementValue(String pe,String v){}	
	public boolean evaluate(){return true;}
	
	public String toEvalXMLString(){
		String xmlStr="";
		
		xmlStr+="<literal>"+literalValue+"</literal>\n";
		
		return xmlStr;
	}
	
}