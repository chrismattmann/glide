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


public class startMobileMediaClient{
	
	public static void main(String [] args){	
		String usage = "java mobilemedia.startMobileMediaClient --hostName <String> --portNum <String> --archName <String>\n";
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
		
		Component mediaGUIComponent = null;

		mediaGUIComponent = new AWTMediaQueryGUI("urn:glide:prism:Client_mediaGUIComponent",System.getProperty("mp3.download.loc"));			
		mediaGUIComponent.scaffold = s;
		
		//make the connectors now
		
		C2BasicHandler  bbc2=new Prism.handler.C2BasicHandler();
		Connector QueryConn = new Connector("urn:glide:prism:QueryConn", bbc2);
		QueryConn.scaffold =s;	
		
		
		arch.add(mediaGUIComponent);
		arch.add(QueryConn);
		
		//let's add the ports		
		Port QueryConnReplyPort=new Port(PrismConstants.REPLY,QueryConn);
		QueryConn.addPort(QueryConnReplyPort);
		QueryConn.scaffold = s;
		
		Port mediaGUIRequestPort=new Port(PrismConstants.REQUEST,mediaGUIComponent);
		mediaGUIComponent.addPort(mediaGUIRequestPort);
		mediaGUIRequestPort.scaffold = s;
		
        ExtensiblePort QueryConnRequestPort = new ExtensiblePort (PrismConstants.REQUEST, QueryConn);
		SocketDistribution sd=new SocketDistribution(QueryConnRequestPort);    
		QueryConnRequestPort.addDistributionModule(sd);
		QueryConnRequestPort.scaffold = s;
		QueryConn.addPort(QueryConnRequestPort);
		
		arch.add(QueryConnRequestPort);
		arch.add(QueryConnReplyPort);
		arch.add(mediaGUIRequestPort);
		
		
		arch.weld(QueryConnReplyPort,mediaGUIRequestPort);
		
		disp.start();
		arch.start();	

		QueryConnRequestPort.connect(hostName, Integer.parseInt(portNum)); //connect the port to the specified host
		
	
	}
	
	
}