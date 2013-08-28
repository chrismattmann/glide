package glide.queryparser;



public interface IExpression{
	
	/**
	 * <p>Returns whether the AND'ing of both lhs expression and rhs expression is true.
	 * 
	 * @return The boolean value of the AND'ing of the lhs and rhs expressions
	 */
	public boolean evaluate();
	
	/**
	 * <p>Returns string serialization in XML of the expression.
	 * 
	 * @return String XML serialization of the expression.
	 */	
	public String toEvalXMLString();
	
	
	/**
	 * <p>Assign a value to a profile Element used in the evaluation method.
	 * 
	 * @param pe The name of the profile element
	 * @param v The value of the profile element
	 * 
	 * @return void.
	 */
	
	public void setProfileElementValue(String pe,String v);
	
	/**
	 * <p>Gets the type of this expression.
	 * 
	 * @return A {@link String} version of the expression type.
	 */
	public String getExpressionType();

	
}