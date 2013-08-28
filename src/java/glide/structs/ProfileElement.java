package glide.structs;

import java.io.Serializable;

/**
 * <p>A ProfileElement is a granule of metadata describing a domain-specific facet of a resource. A domain-specific facet of 
 * a car resource might be <i>Car.Model</i> with possible values <code>Ford, Chevy, etc.</code>. 
 * 
 * </p>
 * 
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */



public class ProfileElement implements IDeletable,Serializable{
	
	protected String context=null;
	protected String name=null;
	protected String creator=null;
	protected String title=null;
	protected String value=null;
	
	/**
	 * <p>Constructor takes the specified attributes of the profile element and constructs a new profile element with those attributes.</p>
	 * 
	 * @param ctxt The context of this profile element (e.g. CS589, Space Science, Physics, etc.)
	 * @param n The name of this profile element (e.g. Car.Model, Book.Domain, Car Name, etc.)
	 * @param cr The creator of this profile element.
	 * @param t The title of this profile element.
	 * @param v The value of this profile element.
	 */
	public ProfileElement(String ctxt,String n,String cr,String t,String v){
		context = ctxt;
		name = n;
		creator = cr;
		title = t;
		value = v;
	}
	
	/**
	 * 
	 * <p>Constructor for profile element initializes all attributes to the blank 
	 * string.
	 * 
	 */
	public ProfileElement(){
		context="";
		name="";
		creator="";
		title="";
		value="";
	}
	
	public void deconstruct(){
		context=null;
		name=null;
		creator=null;
		title=null;
		value=null;
	}
	
	/**
	 * <p>Gets the context of this profile element.</p>
	 * 
	 * @return Context of this profile element.
	 */
	public String getContext(){return context;}
	
	/**
	 * <p>Gets the name of this profile element.</p>
	 * 
	 * @return Name of this profile element.
	 */
	public String getName(){return name;}
	
	/**
	 * <p>Gets the creator of this profile element.</p>
	 * 
	 * @return Creator of this profile element.
	 */
	public String getCreator(){return creator;}
	
	/**
	 * <p>Gets the title of this profile element.</p>
	 * 
	 * @return Title of this profile element.
	 */
	public String getTitle(){return title;}
	
	/**
	 * <p>Gets the value of this profile element.</p>
	 * 
	 * @return Value of this profile element.
	 */
	public String getValue(){return value;}
	
	/**
	 * <p>Setter method sets the context of this profile element to the new specified value.</p>
	 * 
	 * @param c The new context for this profile element.
	 * @return void.
	 */
	public void setContext(String c){context = c;}
	
	/**
	 * <p>Setter method sets the name of this profile element to the new specified value.</p>
	 * 
	 * @param n The new name for this profile element.
	 * @return void.
	 */
	public void setName(String n){name = n;}
	
	/**
	 * <p>Setter method sets the creator of this profile element to the new specified value.</p>
	 * 
	 * @param c The new creator for this profile element.
	 * @return void.
	 */
	public void setCreator(String c){creator = c;}
	
	/**
	 * <p>Setter method sets the title of this profile element to the new specified value.</p>
	 * 
	 * @param t The new title for this profile element.
	 * @return void.
	 */
	public void setTitle(String t){title = t;}
	
	/**
	 * <p>Setter method sets the value of this profile element to the new specified value.</p>
	 * 
	 * @param v The new value for this profile element.
	 * @return void.
	 */
	public void setValue(String v){value = v;}
	
	/**
	 * <p>XML serialization method creates a new string and serializes the new profile in XML to the string.</p>
	 * 
	 * @return {@link String} representation of XML profile.
	 */
	public String toXMLString(){
		String xmlStr="";
		
		xmlStr+="<profileElement>\n";
		xmlStr+="\t<name>"+name+"</name>\n";
		xmlStr+="\t<creator>"+creator+"</creator>\n";
		xmlStr+="\t<title>"+title+"</title>\n";
		xmlStr+="\t<context>"+context+"</context>\n";
		xmlStr+="\t<value>"+value+"</value>\n";
		
		xmlStr+="</profileElement>\n";
		
		return xmlStr;
	}
	
	
	
}