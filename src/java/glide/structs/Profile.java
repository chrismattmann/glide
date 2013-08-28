package glide.structs;


import java.util.Vector;
import java.io.Serializable;
import glide.product.ProductServer; // for javadocs
import glide.profile.ProfileServer; // for javadocs


/**
 * <p>A Profile is a data component capturing information about a <i>resource</i> available in GLIDE.
 * A <i>resource</i> is one of the following:<br>
 * 
 * <ul>
 *   <li>A {@link ProductServer} identified by the class <code>glide.productServer</code></li>
 *   <li>A {@link ProfileServer} identified by the class <code>glide.profileServer</code></li>
 *   <li>A granule of <i>data</i> (e.g. an image, an mp3 file, etc.), identifed by the class <code>glide.data</code></li>
 * </ul>
 * 
 * <p>Profiles store information about these type of resources. They have three important sections:<br>
 * 
 * <ol>
 *   <li>The {@link ProfileAttributes}, basically housekeeping information about this profile data component.</li>
 *   <li>The {@link ResourceAttributes}, metadata regarding the resource that this profile describes. This metadata is loosely based on the <a href="http://dublincore.org">Dublin Core Metadata Set of Elements to Describe Any Electronic resource</a></li>
 *   <li>A {@link Vector} of {@link ProfileElement}s. <i>ProfileElements</i> are metadata elements which identify domain specific attributes of the resource that this profile describes. An example attribute for a book would be <i>Author</i>.
 *   Not every resource defines an "Author" attribute, but book resources typically do. In this case, a profile of a book might have a profile element called <i>Book.Author</i>. Hopefully you get the picture. :-)</li>
 * </ol>
 * 
 * 
 * 
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */


public class Profile implements IDeletable,Serializable{
	
	protected ResourceAttributes resAttr=null;
	protected ProfileAttributes profAttr=null;
	protected Vector profileElements=null;
	
	/**
	 * <p>Default c'tor creates new resource attributes, profile attributes and a blank vector to store profile elements.</p>
	 * 
	 */
	public Profile(){
	   resAttr = new ResourceAttributes();
	   profAttr = new ProfileAttributes();
	   profileElements = new Vector();
		
	}
	
	/**
	 * <p>C'tor where you specify the resource attributes, profile attributes and Vector of profile elements.</p>
	 * 
	 * @param rAttr The {@link ResourceAttributes} describing the resource that this profile points to.
	 * @param pAttr The {@link ProfileAttributes} containing housekeeping information about this profile.
	 * @param profElements A {@link Vector} of {@link ProfileElement}s that describe the resource that this profile is pointing to.
	 * 
	 * 
	 */
	
	public Profile(ResourceAttributes rAttr,ProfileAttributes pAttr,Vector profElements){
		resAttr = rAttr;
		profAttr = pAttr;
		profileElements = profElements;
		
	}
	
	public void deconstruct(){
		resAttr.deconstruct();
		profAttr.deconstruct();
		
		resAttr=null;
		profAttr=null;
		
		for(int i=0; i < profileElements.size(); i++){
			ProfileElement profElem = (ProfileElement)profileElements.elementAt(i);
			profElem.deconstruct();
			profElem=null;
		}
		
		profileElements = null;
	}
	
	/**
	 * <p>Accessor method returning the resource attributes for this profile.</p>
	 * 
	 * @return The {@link ResourceAttributes} for this profile.
	 */
	public ResourceAttributes getResourceAttributes(){return resAttr;}
	
	/**
	 * <p>Accessor method returning the profile attributes for this profile.</p>
	 * 
	 * @return The {@link ProfileAttributes} for this profile.
	 */
	public ProfileAttributes getProfileAttributes(){return profAttr;}
	
	/**
	 * <p>Accessor method returning profile elements for this profile.</p>
	 * 
	 * @return The {@link Vector} of {@link ProfileElement}s for this profile.
	 */
	public Vector getProfileElements(){return profileElements;}
	
	
	/**
	 * <p>Setter method sets the resource attributes for this profile.</p>
	 * 
	 * @param rAttr The new ResourceAttributes for this profile.
	 * @return void.
	 */
	public void setResourceAttributes(ResourceAttributes rAttr){resAttr = rAttr;}
	
	/**
	 * <p>Setter method sets the profile attributes for this profile.</p>
	 * 
	 * @param pAttr The new ProfileAttributes for this profile.
	 * @return void.
	 */
	
	public void setProfileAttributes(ProfileAttributes pAttr){profAttr = pAttr;}
	
	/**
	 * <p>Setter method sets the profile elements for this profile.</p>
	 * 
	 * @param pElem The new Vector of ProfileElements for this profile.
	 * @return void.
	 */
	public void setProfileElements(Vector pElem){profileElements = pElem;}
	
	
	/**
	 * <p>Serialization method returns a String representation in XML of this profile data component.</p>
	 * 
	 * @return String XML representation of this profile data component.
	 */
	public String toXMLString(){
		String xmlStr="";
		
		xmlStr+="<?xml version=\"1.0\" ?>\n";
		xmlStr+="<profile>\n";
		xmlStr+=profAttr.toXMLString()+"\n";
		xmlStr+=resAttr.toXMLString()+"\n";
		
		//for(Iterator i = profileElements.iterator(); i.hasNext(); ){
		for(int i = 0; i < profileElements.size(); i++){
		    //ProfileElement pe = (ProfileElement)i.next();
			ProfileElement pe = (ProfileElement)profileElements.elementAt(i);
			xmlStr+=pe.toXMLString()+"\n";
		}
	    
		xmlStr+="</profile>\n";
		
		return xmlStr;
	}
	
		
}