package glide.structs;


import java.util.Vector;
import java.io.Serializable;

/**
 * <p>ResourceAttributes are metadata about the resource that a profile describes. The resource attributes
 * are based loosely on the <a href="http://dublincore.org">Dublin Core set of data elements to describe <i>any</i> Electronic Resource.
 * </a></p>
 * 
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */


public class ResourceAttributes implements IDeletable,Serializable{
	
	protected String resourceID=null;
	protected String resourceClass=null;
	protected Vector resourceLocations=null;
	protected Vector authors=null;
	protected Vector creators=null;
	protected Vector owners=null;
	
	/**
	 * <p>Default c'tor for resource attributes creates 0 sized lists for owners, authors, etc. and default values for attributes.
	 * 
	 * 
	 */
	public ResourceAttributes(){
		resourceID = "urn:NULL";
		resourceClass="data.null";
		resourceLocations = new Vector();
		authors = new Vector();
		creators = new Vector();
		owners = new Vector();
	}
	
	/**
	 * <p>C'tor for resource attributes to set attributes to specified values.
	 * 
	 * @param resID A String ID for this resource.
	 * @param resClass A String describing the type of resource that this profile describes. Possible values include <code>glide.productServer, glide.profileServer</code> and <code>glide.data</code>
	 * @param resLoc A {@link Vector} of locations for the resource that this profile describes.
	 * @param a A {@link Vector} of authors for the resource that this profile describes.
	 * @param c A {@link Vector} of creators for the resource that this profile describes.
	 * @param o A {@link Vector} of owners for the resource that this profile describes.
	 */
	public ResourceAttributes(String resID,String resClass,Vector resLoc,Vector a,Vector c, Vector o){
		resourceID = resID;
		resourceClass=resClass;
		resourceLocations = resLoc;
		authors = a;
		creators = c;
		owners = o;
	}
	
	public void deconstruct(){
	  resourceID=null;
	  resourceClass=null;
	  
	  for(int i=0; i < resourceLocations.size(); i++){
	  	String rLoc = (String)resourceLocations.elementAt(i);
	  	rLoc = null;
	  }
	  
	  resourceLocations = null;

	  for(int i=0; i < authors.size(); i++){
	  	String theAuthor = (String)authors.elementAt(i);
	  	theAuthor = null;
	  }
	  
      authors = null;

	  for(int i=0; i < creators.size(); i++){
	  	String theCreator = (String)creators.elementAt(i);
	  	theCreator = null;
	  }
	  
      creators = null;

	  for(int i=0; i < authors.size(); i++){
	  	String theOwner = (String)owners.elementAt(i);
	  	theOwner = null;
	  }
	  
      owners = null;

	}
	
	/**
	 * <p>Gets the ID for this resource.</p>
	 * 
	 * @return The ID for this resource.
	 */
	public String getResourceID(){return resourceID;}
	
	/**
	 * <p>Gets the type of this resource.</p>
	 * 
	 * @return The type for this resource.
	 */
	public String getResourceClass(){return resourceClass;}
	
	/**
	 * <p>Gets the locations of this resource.</p>
	 * 
	 * @return A {@link Vector} of {@link String} locations for this resource.
	 */
	public Vector getResourceLocations(){return resourceLocations;}
	
	/**
	 * <p>Gets the authors of this resource.</p>
	 * 
	 * @return A {@link Vector} of {@link String} authors for this resource.
	 */
	
	public Vector getAuthors(){return authors;}
	
	/**
	 * <p>Gets the creators of this resource.</p>
	 * 
	 * @return A {@link Vector} of {@link String} creators for this resource.
	 */
	public Vector getCreators(){return creators;}
	
	/**
	 * <p>Gets the owners of this resource.</p>
	 * 
	 * @return A {@link Vector} of {@link String} owners for this resource.
	 */
	public Vector getOwners(){return owners;}
	

	/**
	 * <p>Sets the resource ID</p>
	 * 
	 * @param resID The new resource ID.
	 * @return void.
	 */
	public void setResourceID(String resID){resourceID = resID;}	
	
	/**
	 * <p>Sets the resource class</p>
	 * 
	 * @param resClass The new resource class.
	 * @return void.
	 */
	
	public void setResourceClass(String resClass){resourceClass = resClass;}
	
	/**
	 * <p>Sets the resource locations</p>
	 * 
	 * @param resLoc The new resource locations.
	 * @return void.
	 */
	public void setResourceLocations(Vector resLoc){resourceLocations = resLoc;}
	
	/**
	 * <p>Sets the resource authors</p>
	 * 
	 * @param a The new resource authors.
	 * @return void.
	 */
	public void setAuthors(Vector a){authors = a;}
	
	/**
	 * <p>Sets the resource creators</p>
	 * 
	 * @param c The new resource creators.
	 * @return void.
	 */
	
	public void setCreators(Vector c){creators = c;}
	
	/**
	 * <p>Sets the resource owners</p>
	 * 
	 * @param o The new resource owners.
	 * @return void.
	 */
	
	public void setOwners(Vector o){owners = o;}
	
	/**
	 * <p>Serialization method returns a string XML representation of the resource attributes.
	 * 
	 * @return String XML representation of the resource attributes.
	 */
	public String toXMLString(){
		String xmlStr="";
		
		xmlStr+="<resourceAttributes>\n";
		xmlStr+="\t<resourceClass>"+resourceClass+"</resourceClass>\n";
		xmlStr+="\t<resourceID>"+resourceID+"</resourceID>\n";
		

		for(int i=0; i < resourceLocations.size(); i++){
			String resLoc = (String)resourceLocations.elementAt(i);
			xmlStr+="\t<resourceLocation>"+resLoc+"</resourceLocation>\n";
		}


		for(int i=0; i < authors.size(); i++){
			String theAuthor = (String)authors.elementAt(i);
			xmlStr+="\t<author>"+theAuthor+"</author>\n";
		}
		

		for(int i=0; i < creators.size(); i++){
			String theCreator = (String)creators.elementAt(i);
			xmlStr+="\t<creator>"+theCreator+"</creator>\n";
		}		

		

		for(int i=0; i < owners.size(); i++){
			String theOwner = (String)owners.elementAt(i);
			xmlStr+="\t<owner>"+theOwner+"</owner>\n";
		}	
		
		xmlStr+="</resourceAttributes>\n";
		
		return xmlStr;
		
		
	}
	
	
	
	
}