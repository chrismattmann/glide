package glide.structs;


import java.util.Vector;
import java.io.Serializable;

/**
 * <p>A <i>QueryObject</i> is a data component that contains two important things:
 * 
 * <ul>
 *   <li>A <i>keyword query</i> specifying an assignment of values to specific profile elements describing what type of resource the user is interested in.</li>
 *   <li>A {@link Vector} of {@link Result}s which were obtained during a query sequence which satisfied the user's query.</li>
 * </ul>
 * 
 * <p>The keyword query is in the format of : <code>[Keyword 1 = Value 1]+ [LOGICAL OPERATOR]*.... [Keyword n = Value n]*</code>
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */


public class QueryObject implements IDeletable,Serializable{
	
	protected Vector results=null;
	//protected List results=null;
	protected String kwdQuery=null;
	protected String queryOrig=null;
	
	/**
	 * <p>Default c'tor for query object initializes 0 size'd result list and blank keyword query.
	 * 
	 */
	public QueryObject(){
		results = new Vector();
		kwdQuery="";
	}
	
	/**
	 * <p>Constructor for a query object initializes a 0 size'd result list and a keyword query specified by the given param.
	 * 
	 * @param query The keyword query for resources.
	 */
	public QueryObject(String query){
		kwdQuery = query;
		results = new Vector();
	}
	
	/**
	 * <p>C'tor for query object specifying both the keyword query and the result list.
	 * 
	 * @param query The keyword query for resources.
	 * @param r The {@link Vector} of {@link Result}s to be populated.
	 */
	public QueryObject(String query, Vector r){
		kwdQuery = query;
		results = r;
	}
	
	/**
	 * <p>C'tor for the query object specifying keyword query, result list and the originator of this query object (the orignator's URN).
	 * 
	 * @param query The keyword query for resources.
	 * @param r The {@link Vector} of {@link Result}s to be populated.
	 * @param orig The URN of the component which created this keyword query.
	 */
	public QueryObject(String query,Vector r,String orig){
		queryOrig = orig;
		kwdQuery = query;
		results = r;
	}
	
	/**
	 * <p>Accessor method returns a {@link Vector} of {@link Result}s for this Query Object.</p>
	 * 
	 * @return {@link Vector} of {@link Result}s.
	 */
	public Vector getResults(){return results;}
	
	/**
	 * <p>Accessor method returns the keyword query for this query object.
	 * 
	 * @return The keyword query for this query object.
	 */
	public String getQuery(){return kwdQuery;}
	
	/**
	 * <p>Accesor method returns the URN of the query originator.
	 * 
	 * @return A String URN of the component which created this query object.
	 */
	public String getQueryOrig(){return queryOrig;}
	
	/**
	 * <p>Setter method sets the query originator string for this query object.
	 * 
	 * @param qo The new query originator.
	 * @return void.
	 */
	public void setQueryOrig(String qo){queryOrig = qo;}
	
	/**
	 * <p>Setter method sets the new result list for this query object.
	 * 
	 * @param r The new {@link Vector} of {@link Result}s
	 * @return void.
	 */
	
	public void setResults(Vector r){results = r;}
	
	/**
	 * <p>Setter method sets the keyword query string for this query object.
	 * 
	 * @param q The new keyword query.
	 * @return void.
	 */
	public void setQuery(String q){kwdQuery = q;}
	
	public void deconstruct(){
		queryOrig = null;
		kwdQuery = null;
		
		for(int i=0; i < results.size(); i++){
			Result r = (Result)results.elementAt(i);
			r = null;
		}
		
		results = null;
	}
	
	/**
	 * <p>XML serialization of this query object into a String.
	 * 
	 * @return A {@link String} representation of the XML query object.
	 */
	public String toXMLString(){
		String xmlStr="";
		
		xmlStr="<?xml version=\"1.0\"?>\n";
		xmlStr+="<queryObject>\n";
		xmlStr+="\t<queryOrig>"+queryOrig+"</queryOrig>\n";
		xmlStr+="\t<kwdQueryString>"+kwdQuery+"</kwdQueryString>\n";
		xmlStr+="\t\t<Results>\n";
		
		for(int i=0; i < results.size(); i++){
			Result r = (Result)results.elementAt(i);
			xmlStr+=r.toXMLString();
		}
		
		xmlStr+="\t\t</Results>\n";
		xmlStr+="</queryObject>\n";
		
		return xmlStr;
	}

}