package glide.queryparser;

/**
 * <p>EqualsExpression represents a boolean equals operation between a lhs and rhs expression.
 * 
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */

public class EqualsExpression implements IExpression{
	
	protected IExpression lhsElem=null;
	protected IExpression rhsElem=null;
	
	
	/**
	 * <p>Default c'tor takes a left hand expression and a right.
	 * 
	 * @param elem The lhs side of the Expression
	 * @param elem2 The rhs side of the Expression
	 * 
	 */
	public EqualsExpression(IExpression elem,IExpression elem2){
		lhsElem = elem;
		rhsElem = elem2;
	}
	

	public void setProfileElementValue(String pe,String v){lhsElem.setProfileElementValue(pe,v); rhsElem.setProfileElementValue(pe,v);}
	public String getExpressionType(){return "=";}
	public boolean evaluate(){
        ProfileElementExpression pe = (ProfileElementExpression)lhsElem;
        Literal literal = (Literal)rhsElem;
        
        if(pe.getValue().equals(literal.getValue())){return true;} else return false;
	}
	
	public String toEvalXMLString(){
		String xmlStr="";
		
		xmlStr+="<eq>\n";
		xmlStr+=lhsElem.toEvalXMLString()+"\n";
		xmlStr+=rhsElem.toEvalXMLString()+"\n";
		xmlStr+="</eq>\n";
		
		return xmlStr;
	}
	
	
	
	
}