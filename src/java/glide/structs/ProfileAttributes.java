package glide.structs;

import java.io.Serializable;

/**
 * <p>ProfileAttributes is a data component which stores house-keeping information about the profile
 * that it belongs to. <i>House-keeping</i> information includes an <code>ID</code> for the profile (preferably a URN), 
 * and the <code>Creator</code> of the Profile.</p>
 * 
 * 
 * 
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */

public class ProfileAttributes implements IDeletable,Serializable{
	
	protected String profileID=null;
	protected String profileCreator=null;
	
	/**
	 * <p>Default c'tor create Profile Attributes with default values.</p>
	 * 
	 * 
	 */
	public ProfileAttributes(){
		profileID="NULL_ID";
		profileCreator="The glide Funder";
	}
	
	/**
	 * <p>C'tor creates profile attributes with the specified parameters.
	 * 
	 * @param pID The Profile ID
	 * @param pC The Profile's Creator
	 * 
	 * 
	 */
	public ProfileAttributes(String pID,String pC){
		profileID=pID;
		profileCreator = pC;
	}
	
	public void deconstruct(){
	  profileID = null;
	  profileCreator = null;
	}
	
	
	/**
	 * <p>Accessor method to get profile id.</p>
	 * 
	 * @return The profile ID.
	 */
	public String getProfileID(){return profileID;}
	
	/**
	 * <p>Accessor method to get the profile creator.</p>
	 * 
	 * @return The profile creator.
	 */
	public String getProfileCreator(){return profileCreator;}
	
	/**
	 * <p>Setter method sets the profile id to the specified parameter value.</p>
	 * 
	 * @param pID The new Profile ID.
	 * @return void.
	 */
	public void setProfileID(String pID){profileID = pID;}
	
	/**
	 * <p>Setter method sets the new profile creator to the specified value.</p>
	 * 
	 * @param pC The new Profile Creator.
	 * @return void.
	 */
	public void setProfileCreator(String pC){profileCreator = pC;}
	
	
	/**
	 * <p>XML serialization of profile attributes into a String.</p>
	 * 
	 * @return {@link String} representation of XML Profile Attributes serialization.
	 */
	public String toXMLString(){
		String xmlStr="";
		
		xmlStr+="<profileAttributes>\n";
		xmlStr+="\t<profileID>"+profileID+"</profileID>\n";
		xmlStr+="\t<profileCreator>"+profileCreator+"</profileCreator>\n";
		xmlStr+="</profileAttributes>\n";
		
		return xmlStr;
	}
	
	
}