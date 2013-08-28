package glide.structs;

import java.io.Serializable;
import glide.util.mimeTypes;

/**
 * <p>A Result is a data component holding data returned from a particular resource. The data is
 * stored in {@link String} format, but can be encoded to any particular {@link mimeTypes} This encoding is not part of 
 * this class, this class merely stores the data (in String format), and then stores what the Mime Type of the
 * Result is. It is up to the user to perform the necessary encoding.
 * </p>
 * 
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */


public class Result implements Serializable{
	
	protected String data=null;
	protected String mimeType=null;
	protected String creator=null;
	protected byte[] byteData=null;
	
	/**
	 * <p>Default c'tor
	 */
	public Result(){
		
	}
	
	/**
	 * <p>C'tor initializes result with appropriate params, including byte array data.
	 * 
	 * @param mType The mime type for this result.
	 * @param cr The creator of this result.
	 * @param dta The data.
	 * @param bData The byte data.
	 * 
	 */
	public Result(String mType, String cr, String dta, byte [] bData){
		data = dta;
		creator = cr;
		mimeType = mType;
		byteData = bData;
	}
	
	/**
	 * <p>C'tor initializes result with appropriate params.
	 * 
	 * @param mType The mime type for this result. Please use the {@link mimeTypes} constants class to set your mime type to ensure that everyone's mime type is from a controlled list.
	 * @param cr The creator of this result.
	 * @param dta The data.
	 * 
	 * 
	 */
	public Result(String mType,String cr,String dta){
		this(mType,cr,dta,null);
	}
	

	/**
	 * <p>Accessor method to get the byte data</p>
	 * 
	 * @return The byte data for this result.
	 */	
	public byte[] getByteData(){return byteData;}
	
	/**
	 * <p>Accessor method to get the mime type</p>
	 * 
	 * @return The {@link mimeTypes}  for this result.
	 */
	public String getMimeType(){return mimeType;}
	
	/**
	 * <p>Accessor method to get the data for this result.
	 * 
	 * @return The data.
	 */
	public String getData(){return data;}
	
	/**
	 * <p>Accessor method to get the creator of this result.
	 * 
	 * @return The creator.
	 */
	public String getCreator(){return creator;}
	
	/**
	 * <p>Setter method to set the byte data for this result.
	 * 
	 * @param bData The new byte data for this result.
	 * @return void.
	 */
	public void setByteData(byte[] bData){byteData = bData;}
	
	/**
	 * <p>Setter method to set the mime type for this result.
	 * 
	 * @param mType The new {@link mimeTypes} for this result.
	 * @return void.
	 */
	public void setMimeType(String mType){mimeType = mType;}
	
	/**
	 * <p>Setter method to set the data for this result.
	 * 
	 * @param d The new data for this result.
	 * @return void.
	 */
	public void setData(String d){data = d;}
	
	/**
	 * <p>Setter method to set the creator for this result.
	 * 
	 * @param c The new creator for this result.
	 * @return void.
	 */
	public void setCreator(String c){creator = c;}
	
	/**
	 * 
	 * <p>Serialization method serializes Result object in XML format to a string and returns it.
	 * 
	 * @return XML-formatted, serialized String.
	 */
	public String toXMLString(){
		String xmlStr="";
		
		xmlStr+="\t\t\t<Result>\n";
		xmlStr+="\t\t\t\t<mimeType>"+mimeType+"</mimeType>\n";
		xmlStr+="\t\t\t\t<Creator>"+creator+"</Creator>\n";
		xmlStr+="\t\t\t\t<Data>"+data+"</Data>\n";
		xmlStr+="\t\t\t\t<ByteData>"+byteData.toString()+"</ByteData>\n";
		xmlStr+="\t\t\t</Result>\n";
		
		return xmlStr;
	}
	
	
	
}