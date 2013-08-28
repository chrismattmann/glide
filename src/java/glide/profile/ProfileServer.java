package glide.profile;

import Prism.core.Component;
import Prism.core.Event;
import Prism.core.PrismConstants;

import glide.structs.QueryObject;
import glide.structs.Profile;
import glide.profile.handlers.IProfileHandler;
import glide.util.glideUtils;

import java.util.Vector;
import java.util.StringTokenizer;

/**
 * <p>Placeholder
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */


public class ProfileServer extends Prism.core.Component implements IProfileServer{
	
	protected Vector profileHandlers = null;
	
	
	public ProfileServer(String name) throws ClassNotFoundException,InstantiationException,IllegalAccessException{
		super(name);
		profileHandlers = new Vector();
		loadProfileHandlers(getProfileHandlerList(System.getProperty("glide.profileHandlers")));
	}

	
	public ProfileServer(String name,String profHldrs) throws ClassNotFoundException,InstantiationException,IllegalAccessException{
		super(name);
		profileHandlers = new Vector();
		loadProfileHandlers(getProfileHandlerList(profHldrs));
	}
	
	
	public Vector findProfiles(QueryObject q){
	  Vector returnProfiles = new Vector();
	  
		//for(Iterator i = profileHandlers.iterator(); i.hasNext(); ){
		//	IProfileHandler iph = (IProfileHandler)i.next();
		for(int i=0; i < profileHandlers.size(); i++){
	      IProfileHandler iph = (IProfileHandler)profileHandlers.elementAt(i);
	      returnProfiles = glideUtils.addAll(returnProfiles,iph.findProfiles(q));
		}
		
		return returnProfiles;
	}
	
	public void handle(Event e){
		//System.err.println(name+" received event "+e.name);
		if(e.eventType == PrismConstants.REQUEST && e.name.equals("ProfileQuery_Request")){
			
			//ensure that this message is for us
			String profileServerID = (String)e.getParameter("profileServer_id");
			String queryServerID = (String)e.getParameter("queryServer_id");
			String theOrigID = (String)e.getParameter("orig_id");
			
			/*System.err.println("checking server ID parameter, it's = "+serverID);
			System.err.println("checking client ID parameter, it's = "+clientID);
			*/
			
			
			if(profileServerID == null || (profileServerID != null && !profileServerID.equals(name))){
				return;  //this message was not intended for us
			}
			
			
			QueryObject theQueryObject = (QueryObject)e.getParameter("query");
			Vector foundProfiles = findProfiles(theQueryObject);
			
			//System.err.println(name+" responding to profile query");
			Event n = new Event("ProfileQuery_Reply");
			n.eventType = PrismConstants.REPLY;
			if(queryServerID != null){n.addParameter("queryServer_id",queryServerID);}
			n.addParameter("orig_id",theQueryObject.getQueryOrig());
			n.addParameter("query",theQueryObject);
			n.addParameter("profiles",foundProfiles);
			send(n);
		}
		
		
	}

	private Vector getProfileHandlerList(String theList){
		if(theList == null){return null;}
		
		StringTokenizer st = new StringTokenizer(theList,";");
		Vector qList = new Vector();
		
		while(st.hasMoreTokens()){
			String phClass = st.nextToken();
			qList.addElement(phClass);
		}
		
		return qList;
	}
	
	private void loadProfileHandlers(Vector hList) throws ClassNotFoundException,InstantiationException,IllegalAccessException{
		
		if(hList==null){return;}
		
		for(int i2=0; i2 < hList.size(); i2++){
			
			IProfileHandler q1 = null;
			String cName = (String)hList.elementAt(i2);
			
			try{
				q1 = (IProfileHandler)Class.forName(cName).newInstance();				
				profileHandlers.addElement(q1);
				
			}catch(java.lang.ClassNotFoundException c){
				System.out.println("GLIDE Profile Server: Unable to Find Handler Class!");
				throw c;
			}
			catch(ClassCastException cce){
				System.out.println("GLIDE Profile Server:  Caught Class Cast Exception!");
				cce.printStackTrace();
			}
			catch(java.lang.InstantiationException i){
				System.out.println("GLIDE Profile Server: Unable to Instantiate Class!");
				throw i;
			}
			catch(java.lang.IllegalAccessException iae){
				System.out.println("GLIDE Profile Server: Illegal Access Exception!");
				throw iae;
			}		
		
		}	
	}
	
}