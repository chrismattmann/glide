package glide.query;

import Prism.core.Component;
import Prism.core.Event;
import Prism.core.PrismConstants;

import java.util.Vector;
//import java.util.List;
//import java.util.Iterator;
import java.util.StringTokenizer;

import glide.structs.QueryObject;
import glide.structs.Profile;


public class QueryServer extends Prism.core.Component implements IQueryServer{
	
	protected Vector profileServerIDs=null;
	protected Vector visitedList=null;
	//protected List productCache=null;
	
	public QueryServer(String name){
		super(name);
		profileServerIDs = getProfileServers(System.getProperty("glide.queryServer."+name+".profileServers"));
		visitedList = new Vector();
		//productCache = new Vector();
	}
	
	
	public QueryServer(String name,Vector profServers){
		super(name);
		profileServerIDs = profServers;
		visitedList = new Vector();
		//productCache = new Vector();
	}
	
	public void query(QueryObject qo){
		//basic algorithm
		
		//for each profile server in the initial list
		//create a querier
		//send a message to the profile server component you pulled off the list
		//get profiles back
		//examine the resource class attribute
		//  if class = glide.profileServer, then add that to the visited list, create a sub-querier and exit
		//  else if class = glide.profileServer then send a product query to it, and get the query object back
		//  else if class = glide.data, then issue the appropriate command to retrieve it (not sure what this is yet..)
		
		for(int i=0; i < profileServerIDs.size(); i++){
			String profSvrID = (String)profileServerIDs.elementAt(i);
			visitedList.addElement(profSvrID);
			findProfiles(qo,profSvrID);
		}
		
	}

	private Vector getProfileServers(String theList){
		if(theList == null){return null;}
		
		StringTokenizer st = new StringTokenizer(theList,";");
		Vector profileList = new Vector();
		
		while(st.hasMoreTokens()){
			String profileID = st.nextToken();
			profileList.addElement(profileID);
		}
		
		return profileList;
	}
	
	private void findProfiles(QueryObject qo,String profServerID){
		Event n = new Event("ProfileQuery_Request");
		n.eventType = PrismConstants.REQUEST;
		n.addParameter("query",qo);
		n.addParameter("orig_id",qo.getQueryOrig());
		n.addParameter("queryServer_id",name);
		n.addParameter("profileServer_id",profServerID);
		send(n);
	}
	
	public void handle(Event e){
		System.err.println(name+" received event "+e.name);
		
		if(e.eventType == PrismConstants.REQUEST && e.name.equals("Query_Request")){
			String serverID = (String)e.getParameter("queryServer_id");
			if(serverID == null || (serverID != null && !serverID.equals(name))){
				return;  //this message wasn't intended for us
			}			
			
			QueryObject qoRequest = (QueryObject)e.getParameter("query");
			query(qoRequest);
		}
		else if(e.eventType == PrismConstants.REPLY && e.name.equals("ProfileQuery_Reply")){
			String serverID = (String)e.getParameter("queryServer_id");
			if(serverID == null || (serverID != null && !serverID.equals(name))){
				return;  //this message wasn't intended for us
			}
			
			Vector rProfiles = (Vector)e.getParameter("profiles");
			
			if(rProfiles != null){
				//examine the resource class of each returned profile, and run the appropriate condition
				//for(Iterator i = rProfiles.iterator(); i.hasNext();)
				//{
				
				for(int i=0; i < rProfiles.size(); i++){
					Profile theProfile = (Profile)rProfiles.elementAt(i);
					//Profile theProfile = (Profile)i.next();
					String resClass = (String)theProfile.getResourceAttributes().getResourceClass();
					QueryObject q = (QueryObject)e.getParameter("query");
					
					if(resClass.equals("glide.profileServer")){
						String profSvrID = (String)theProfile.getResourceAttributes().getResourceLocations().elementAt(0);
						visitedList.addElement(profSvrID);
						findProfiles(q,profSvrID);					
					}
					else if(resClass.equals("glide.productServer")){
					  	//make it so that this ProductClient is the originator of this query
					  	//q.setQueryOrig(name);
					  	
						//create a new Query Object
						QueryObject qsQuery = new QueryObject();
						
					  	//System.err.println("Got profile "+theProfile.toXMLString());
					  	qsQuery.setQuery(theProfile.getResourceAttributes().getResourceID()); //set the query to the ID of this resource
					  	qsQuery.setQueryOrig(q.getQueryOrig());
					  	//System.err.println("Set query to "+qsQuery.toXMLString());
					  	
					  	//clean up reference
					  	q = null;
					  	
					  	
					  	System.err.println("Profile "+theProfile.toXMLString());
					  	Event e1 = new Event("ProductQuery_Request");
					  	e1.eventType = PrismConstants.REQUEST;
					  	e1.addParameter("query",qsQuery);
					  	e1.addParameter("orig_id",qsQuery.getQueryOrig());
					  	e1.addParameter("productServer_id",(String)theProfile.getResourceAttributes().getResourceLocations().elementAt(0));
					  	e1.addParameter("queryServer_id",name);
					  	e1.addParameter("resourceProfile",theProfile);
					  	send(e1);							
					}
					else if(resClass.equals("glide.data")){
						//not sure what to do with this one yet
					}
				}
				
		    }
		}else if(e.eventType == PrismConstants.REPLY && e.name.equals("ProductQuery_Reply")){
			System.err.println(name+" received event "+e.name);

			//get the query object, and add it to the list of results (e.g. cache it)
			//QueryObject qo = (QueryObject)e.getParameter("returnedQuery");
			//System.err.println(name+" received query Object with query "+qo.getQuery());
			//productCache.add(qo);
			
			//send it along 
			send(e);
			
		}
		
	}
	
	
}