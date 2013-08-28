package mobilemedia;



import glide.product.ProductServer;
import glide.profile.ProfileServer;
import glide.query.QueryServer;

import java.util.Vector;

import Prism.core.Component;
import Prism.core.Connector;
import Prism.core.PrismConstants;
import Prism.core.FIFOScheduler;
import Prism.core.RRobinDispatcher;
import Prism.core.Scaffold;
import Prism.core.BasicC2Topology;
import Prism.core.Architecture;
import Prism.core.Port;
import Prism.handler.C2BasicHandler;
import Prism.extensions.port.ExtensiblePort;
import Prism.extensions.port.distribution.SocketDistribution;


public class startMobileMediaServer{
	
	
	
	public static void main(String [] args){
		String usage = "java mobilemedia.startMobileMediaServer --hostName <String> --portNum <String> --archName <String>\n";
		String hostName=null,portNum=null;
		String archName=null;
		
		for(int i=0; i < args.length; i++){
			if(args[i].equals("--hostName")){
				hostName = args[++i];
			}
			else if(args[i].equals("--portNum")){
				portNum= args[++i];
			}
			else if(args[i].equals("--archName")){
				archName = args[++i];
			}
		}
		
		
		if(archName == null || hostName == null || portNum == null){
			System.err.println(usage);
			System.exit(1);
		}
		
		FIFOScheduler sched = new FIFOScheduler(100);
		Scaffold s = new Scaffold();
		RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
		s.dispatcher=disp;
		s.scheduler=sched;

		BasicC2Topology c2Topology = new BasicC2Topology();
		Architecture arch = new Architecture(archName, c2Topology);
		arch.scaffold=s;
		
		Vector profServerList = new Vector();
		profServerList.addElement("urn:glide:prism:MediaProfileServer");
		QueryServer theQueryServer = new QueryServer("urn:glide:prism:MediaQueryServer",profServerList);
		theQueryServer.scaffold = s;
		
		ProfileServer theProfileServer = null;
		try{
			theProfileServer = new ProfileServer("urn:glide:prism:MediaProfileServer","mobilemedia.profile.handlers.MP3FileStoreProfileHandler;");
		}
		catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		theProfileServer.scaffold = s;
		
		ProductServer theProductServer = null;
		try{
			theProductServer = new ProductServer("urn:glide:prism:MediaProductServer","mobilemedia.product.handlers.MP3FileStoreProductHandler");
		}
		catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}		
		
		theProductServer.scaffold = s;

		
		//make the connectors now
		C2BasicHandler  bbc=new Prism.handler.C2BasicHandler();
		Connector serverConn = new Connector("urn:glide:prism:ServerConn", bbc);
		serverConn.scaffold =s;	
		
		C2BasicHandler  bbc2=new Prism.handler.C2BasicHandler();
		Connector QueryConn = new Connector("urn:glide:prism:QueryConn", bbc2);
		QueryConn.scaffold =s;	
		

		//let's add the ports
		Port mediaProductServerReplyPort=new Port(PrismConstants.REPLY,theProductServer);
		theProductServer.addPort(mediaProductServerReplyPort);
		mediaProductServerReplyPort.scaffold = s;
		
		Port mediaProfileServerReplyPort=new Port(PrismConstants.REPLY,theProfileServer);
		theProfileServer.addPort(mediaProfileServerReplyPort);
		mediaProfileServerReplyPort.scaffold = s;
		
		Port serverConnProductRequestPort=new Port(PrismConstants.REQUEST,serverConn);
		serverConn.addPort(serverConnProductRequestPort);
		serverConnProductRequestPort.scaffold = s;
		
		Port serverConnProfileRequestPort=new Port(PrismConstants.REQUEST,serverConn);
		serverConn.addPort(serverConnProfileRequestPort);
		serverConnProfileRequestPort.scaffold = s;
		
		Port serverConnReplyPort=new Port(PrismConstants.REPLY,serverConn);
		serverConn.addPort(serverConnReplyPort);
		serverConnReplyPort.scaffold = s;
		
		Port queryServerRequestPort=new Port(PrismConstants.REQUEST,theQueryServer);
		theQueryServer.addPort(queryServerRequestPort);
		queryServerRequestPort.scaffold = s;
			
		Port queryServerReplyPort=new Port(PrismConstants.REPLY,theQueryServer);
		theQueryServer.addPort(queryServerReplyPort);
		queryServerReplyPort.scaffold = s;
		
		Port QueryConnRequestPort=new Port(PrismConstants.REQUEST,QueryConn);
		QueryConn.addPort(QueryConnRequestPort);
		QueryConnRequestPort.scaffold = s;
		
		
		arch.weld(mediaProductServerReplyPort,serverConnProductRequestPort);
		arch.weld(mediaProfileServerReplyPort,serverConnProfileRequestPort);
		arch.weld(serverConnReplyPort,queryServerRequestPort);
		arch.weld(queryServerReplyPort,QueryConnRequestPort);

        ExtensiblePort QueryConnSocketReplyPort = new ExtensiblePort(PrismConstants.REPLY, QueryConn);
		SocketDistribution sd=new SocketDistribution(QueryConnSocketReplyPort, Integer.parseInt(portNum));
		QueryConnSocketReplyPort.addDistributionModule(sd);
		QueryConnSocketReplyPort.scaffold = s;
		QueryConn.addPort(QueryConnSocketReplyPort);
		
		arch.add(QueryConnSocketReplyPort);
		arch.add(theQueryServer);
		arch.add(theProfileServer);
		arch.add(theProductServer);
		arch.add(QueryConn);
		arch.add(serverConn);
		
	

                
                
		disp.start();
		arch.start();
		
		
	}
	
	
}