package glide.product.test;

import Prism.core.*;
import Prism.util.*;

import glide.structs.QueryObject;
import glide.product.ProductClient;
import glide.product.ProductServer;

/**
 * <p>Product Client Test class creates a GLIDE architecture with 1 ProductClient and one ProductServer and tries to 
 * have them communicate.
 * </p>
 * 
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */
public class ProductClientTest{

    public final static String usage="java glide.product.test.ProductClientTest --query <queryString>\n";
	  
	  
	public static void main(String [] args){
		FIFOScheduler sched = new FIFOScheduler(100);
		Scaffold s = new Scaffold();
		RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
		s.dispatcher=disp;
		s.scheduler=sched;

		BasicC2Topology c2Topology = new BasicC2Topology();
		Architecture arch = new Architecture("ProductClientDemo", c2Topology);
		arch.scaffold=s;
		
		ProductClient pc = new ProductClient("urn:glide:prism:PCTest","urn:glide:prism:MyProductServer");
		ProductServer psImpl = null;
		
		try{
			psImpl = new ProductServer("urn:glide:prism:MyProductServer");
		}catch(Exception e){ e.printStackTrace(); return;}

		Prism.handler.C2BasicHandler  bbc=new Prism.handler.C2BasicHandler();
		Connector pcConn = new Connector("urn:glide:prism:PCTestConn", bbc);
		pcConn.scaffold =s;	
		
		pc.scaffold = s;
		psImpl.scaffold = s;
		
		arch.add(psImpl);
		arch.add(pc);
		arch.add(pcConn);
		
		Port pcRequestPort = new Port(PrismConstants.REQUEST,pc);
		pc.addPort(pcRequestPort);
		
		Port psReplyPort = new Port(PrismConstants.REPLY,psImpl);
		psImpl.addPort(psReplyPort);
		
		Port pcConnReplyPort = new Port(PrismConstants.REPLY,pcConn);
		pcConn.addPort(pcConnReplyPort);
		
		Port pcConnRequestPort = new Port(PrismConstants.REQUEST,pcConn);
		pcConn.addPort(pcConnRequestPort);
		
		arch.weld(psReplyPort,pcConnRequestPort);
		arch.weld(pcRequestPort,pcConnReplyPort);
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
	    pc.query(q);
	  	
	    //now we have to wait to get a result
	    while(pc.getResults().size() == 0){
	    	//sleep
	    	try{
	    		Thread.sleep(5000);
	    	}catch(InterruptedException ignore){}
	    }
	    
	    //okay, we have the result now
	    QueryObject q1 = (QueryObject)pc.getResults().elementAt(0);
	    
	    System.err.println(q1.toXMLString());
	    System.exit(1);
	    
	  }
	
}