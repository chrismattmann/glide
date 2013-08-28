package mobilemedia;

import Prism.core.Event;
import Prism.core.Component;
import Prism.core.PrismConstants;

import java.awt.event.*;
import java.awt.*;

import java.util.StringTokenizer;
import java.util.Vector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.w3c.tools.codec.Base64Encoder;
import org.w3c.tools.codec.Base64Decoder;
import org.w3c.tools.codec.Base64FormatException;


import glide.structs.QueryObject;
import glide.structs.Profile;
import glide.structs.Result;


public class AWTMediaQueryGUI extends Prism.core.Component{
	
	protected String mediaBaseDirectory=null;
	protected AWTMediaGUI frame=null;
	int fileCount=0;
	
	public AWTMediaQueryGUI(String name,String md){
		super(name);
		mediaBaseDirectory = md;
		//MediaGUI.setDefaultLookAndFeelDecorated(true);
		frame = new AWTMediaGUI(this);
		frame.setSize(350,200);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.pack();
		frame.setVisible(true); 
		
  
	}
	
	public static void main (String [] args){
	   //super("Console GUI Test");

		Runtime rt = Runtime.getRuntime();
		System.gc();
		long initMemory = rt.freeMemory();
		System.out.println("Initial memory " + initMemory);
		long totalMemory = rt.totalMemory();
		System.out.println("Initial total memory " + totalMemory);
		
		AWTMediaGUI theFrame = null;		
		theFrame = new AWTMediaGUI(new AWTMediaQueryGUI("Console Test","/www/mediaRecStore"));
		//theFrame.setDefaultLookAndFeelDecorated(true);
		theFrame.setSize(350,200);
		//theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.pack();
		theFrame.setVisible(true);   
		
		System.gc();
		long remainingMemory = rt.freeMemory();
		System.out.println("Final memory " + remainingMemory);
		totalMemory = rt.totalMemory();
		System.out.println("Final total memory " + totalMemory);
		System.out.println("Memory used\t" + (initMemory - remainingMemory));
		
	}
	
	
	public void handle(Event e){
		System.err.println(name+" received event "+e.name);
		
	  	if(e.eventType == PrismConstants.REPLY && e.name.equals("ProductQuery_Reply")){
	  		
	  	   //make sure that this event was intended for us
	  		String origClientID = (String)e.getParameter("orig_id");
	  		if(origClientID == null || (origClientID != null && !origClientID.equals(name)))
	  			return;
	  		
	  		
	  		System.err.println(name+" unmarshalling returned query object");
	  		//get returnedQuery
	  		QueryObject qo = (QueryObject)e.getParameter("returnedQuery");
	  		System.err.println("Received QueryObject with queryString = "+qo.getQuery());
	  		if(qo != null){
	            //make sure that this query was not null and was intended for us

	  			FileOutputStream outFile=null;
	  	        
	  			String mp3FileName = null;
	  			Profile resProfile = (Profile)e.getParameter("resourceProfile");
	  			
	  			if(resProfile != null){
	  				StringTokenizer st = new StringTokenizer(resProfile.getResourceAttributes().getResourceID(),":");
	  				st.nextToken(); //genre
	  				st.nextToken(); //quality
	  				mp3FileName = mediaBaseDirectory+"/"+st.nextToken(); //filename
	  				
	  			}else{
	  				mp3FileName = mediaBaseDirectory+"/returnedMP3_"+fileCount+".mp3";
	  			    fileCount++;
	  			}
  	            Runtime r = Runtime.getRuntime(); 
  	            
  	           // System.err.println("Client received MP3:  Amount of Free Memory = "+r.freeMemory());
	  			
	  	        try{
	  	        	outFile = new FileOutputStream(new File(mp3FileName));
	  	        	fileCount++;
	  	        	if(qo.getResults().size() == 0){System.err.println("Query Object "+qo.toXMLString()+" has 0 results!"); return;}
	  	        	Result theResult = (Result)qo.getResults().elementAt(0);
	  	            //String theData = (String)theResult.getData();
	  	        	byte[] theByteData = theResult.getByteData();
	  	            //qo.deconstruct();
	  	            //qo = null;
	  	            Base64Decoder decoder = new Base64Decoder(new ByteArrayInputStream(theByteData),outFile);
	  	            decoder.process();
	  	            qo.deconstruct();
	  	            qo = null;
	  	            decoder = null;
	  	            outFile.close();

	  	            r.gc();
	  	            //System.err.println("Client processed MP3 and garbage collected:  Amount of Free Memory = "+r.freeMemory());
	  	   
	  	        }
	  	        catch(IOException theIOException){
	  	        	theIOException.printStackTrace();
	  	        	System.err.println(theIOException.getMessage());
	  	        }
	  	        catch (Base64FormatException theException) {
	  	            System.out.println(theException.getMessage());
	  	            theException.printStackTrace();
	  	         }
	  		}else{
	  			System.err.println("Query Object was NULL!");
	  		}

	  	}		
		
	}
	
	public void doQuery(String kwdQuery){
		Event r = new Event("Query_Request");
		r.eventType = PrismConstants.REQUEST;
		
		QueryObject qo = new QueryObject();
		qo.setQueryOrig(name);
		qo.setQuery(kwdQuery);
		
		r.addParameter("query",qo);
		r.addParameter("orig_id",name);
		r.addParameter("queryServer_id","urn:glide:prism:MediaQueryServer");
		send(r);
	}

	

}