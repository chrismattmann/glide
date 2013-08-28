package glide.queryparser;

/**
 * <p>AndExpression represents a boolean and between two IExpression objects.</p>
 * 
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */

public class AndExpression implements IExpression{
	
	protected IExpression lhs=null;
	protected IExpression rhs=null;
	
	/**
	 * <p>Default c'tor takes a left hand expression and a right.
	 * 
	 * @param l The lhs side of the Expression
	 * @param r The rhs side of the Expression
	 * 
	 */
	public AndExpression(IExpression l,IExpression r){
		lhs = l;
		rhs = r;
	}
	
	public void setProfileElementValue(String pe,String v){lhs.setProfileElementValue(pe,v); rhs.setProfileElementValue(pe,v);}
	

	public String getExpressionType(){return "AND";}
	

	public boolean evaluate(){return lhs.evaluate() && rhs.evaluate();}
	

	public String toEvalXMLString(){
		String xmlStr="";
		
		xmlStr+="<and>\n";
		xmlStr+=lhs.toEvalXMLString()+"\n";
		xmlStr+=rhs.toEvalXMLString()+"\n";
		xmlStr+="</and>\n";
		
		return xmlStr;
	}
	
	
	
	
}