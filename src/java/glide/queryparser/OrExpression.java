package glide.queryparser;

/**
 * <p>A Literal represents a value in the keyword=value query string.</p>
 * 
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */


public class OrExpression implements IExpression{
	protected IExpression lhs=null;
	protected IExpression rhs=null;
	
	/**
	 * <p>Default c'tor takes a left hand expression and a right.
	 * 
	 * @param l The lhs side of the Expression
	 * @param r The rhs side of the Expression
	 * 
	 */
	public OrExpression(IExpression l,IExpression r){
		lhs = l;
		rhs = r;
	}
	
	public boolean evaluate(){return lhs.evaluate() || rhs.evaluate();}
	public String getExpressionType(){return "OR";}
	public void setProfileElementValue(String pe,String v){lhs.setProfileElementValue(pe,v); rhs.setProfileElementValue(pe,v);}
	
	public String toEvalXMLString(){
		String xmlStr="";
		
		xmlStr+="<or>\n";
		xmlStr+=lhs.toEvalXMLString()+"\n";
		xmlStr+=rhs.toEvalXMLString()+"\n";
		xmlStr+="</or>\n";
		
		return xmlStr;		
		
	}
	
	
}