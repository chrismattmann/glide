package glide.queryparser;

import glide.structs.ProfileElement;

public class ProfileElementExpression extends ProfileElement implements IExpression{
	protected String compareName=null;
	
	
	public ProfileElementExpression(String n,String compName){
		super();
		name = n;
		compareName = compName;
	}
	
	public String getExpressionType(){return "PE";}
	public void setProfileElementValue(String pe,String v){
		if(pe.equals(getName())){
			setValue(v);
		}
	}
	
	public boolean evaluate(){return name == compareName;}
	
	public String toEvalXMLString(){
		return toXMLString();		
	}
	
	
}