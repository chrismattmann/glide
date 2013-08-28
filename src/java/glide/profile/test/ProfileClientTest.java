package glide.profile.test;

import Prism.core.*;
import Prism.util.*;

import glide.structs.QueryObject;
import glide.structs.Profile;
import glide.profile.ProfileClient;
import glide.profile.ProfileServer;


public class ProfileClientTest{

    public final static String usage="java glide.profile.test.ProductClientTest --query <queryString>\n";
	  
	  
	public static void main(String [] args){
		FIFOScheduler sched = new FIFOScheduler(100);
		Scaffold s = new Scaffold();
		RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
		s.dispatcher=disp;
		s.scheduler=sched;

		BasicC2Topology c2Topology = new BasicC2Topology();
		Architecture arch = new Architecture("ProductClientDemo", c2Topology);
		arch.scaffold=s;
		
		ProfileClient profClient = new ProfileClient("urn:glide:prism:PfCTest","urn:glide:prism:MyProfileServer");
		ProfileServer pfsImpl = null;
		
		try{
			pfsImpl = new ProfileServer("urn:glide:prism:MyProfileServer");
		}catch(Exception e){ e.printStackTrace(); return;}

		Prism.handler.C2BasicHandler  bbc=new Prism.handler.C2BasicHandler();
		Connector pcConn = new Connector("urn:glide:prism:PCTestConn", bbc);
		pcConn.scaffold =s;	
		
		profClient.scaffold = s;
		pfsImpl.scaffold = s;
		
		arch.add(pfsImpl);
		arch.add(profClient);
		arch.add(pcConn);
		
		Port profClientRequestPort = new Port(PrismConstants.REQUEST,profClient);
		profClient.addPort(profClientRequestPort);
		
		Port pfsReplyPort = new Port(PrismConstants.REPLY,pfsImpl);
		pfsImpl.addPort(pfsReplyPort);
		
		Port pcConnReplyPort = new Port(PrismConstants.REPLY,pcConn);
		pcConn.addPort(pcConnReplyPort);
		
		Port pcConnRequestPort = new Port(PrismConstants.REQUEST,pcConn);
		pcConn.addPort(pcConnRequestPort);
		
		arch.weld(pfsReplyPort,pcConnRequestPort);
		arch.weld(profClientRequestPort,pcConnReplyPort);
		disp.start();
		arch.start();
		
		//read command line arguments
		String kwdQuery=null;
		
		for(int i=0; i < args.length; i++){
			if(args[i].equals("--query")){
				kwdQuery=args[++i];
			}
		}
		
		if(kwdQuery==null){
			System.err.println(usage);
			System.exit(1);
		}
		
		QueryObject q = null;
	    q = new QueryObject(kwdQuery);
	    q.setQueryOrig(profClient.name);
	    
	    profClient.findProfiles(q);
	  	
	    //now we have to wait to get a result
	    while(profClient.getProfiles().size() == 0){
	    	//sleep
	    	try{
	    		Thread.sleep(5000);
	    	}catch(InterruptedException ignore){}
	    }
	    
	    //okay, we have the result now
	    Profile p1 = (Profile)profClient.getProfiles().elementAt(0);
	    
	    System.err.println(p1.toXMLString());
	    System.exit(1);
	    
	  }
	
}