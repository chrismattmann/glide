package glide.profile;

import Prism.core.Component;
import Prism.core.Event;
import Prism.core.PrismConstants;

import java.util.Vector;

import glide.structs.QueryObject;
import glide.util.glideUtils;

public class ProfileClient extends Prism.core.Component{
	protected String profileServerID=null;
	protected Vector returnedProfiles=null;
	
	public ProfileClient(String name,String profileSvrID){
		super(name);
		profileServerID = profileSvrID;
		returnedProfiles = new Vector();
	}
	
	public void findProfiles(QueryObject qo){
		Event r = new Event("ProfileQuery_Request");
		r.eventType = PrismConstants.REQUEST;
		r.addParameter("query",qo);
		r.addParameter("orig_id",name);
		r.addParameter("profileServer_id",profileServerID);
		send(r);
		
	}
	
	public Vector getProfiles(){return returnedProfiles;}
	
	public void handle(Event e){
		///System.err.println(name+" received event "+e.name);
		if(e.eventType == PrismConstants.REPLY && e.name.equals("ProfileQuery_Reply")){
			String clientID = (String)e.getParameter("orig_id");
			if(clientID == null || (clientID != null && !clientID.equals(name))){
				//System.err.println("Returning, client ID = "+clientID);
				return;  //this message wasn't intended for us
			}
			
			Vector rProfiles = (Vector)e.getParameter("profiles");
			
			if(rProfiles != null){returnedProfiles = glideUtils.addAll(returnedProfiles,rProfiles);}
		}
		
	}
	
}