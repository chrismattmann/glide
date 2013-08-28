package glide.product;

import Prism.core.Component;
import Prism.core.Event;
import Prism.core.PrismConstants;

import glide.structs.QueryObject;
import glide.structs.Profile;
import glide.structs.Result;
import glide.product.handlers.IQueryHandler;


import java.util.Vector;
import java.util.StringTokenizer;

/**
 * <p>A ProductServer returns <i>data</i> or provides a <i>computational service</i> (which may also return <i>data</i>). A <u>Product</u> is a unit
 * of the aforementioned data. ProductServers have zero or more {@link IQueryHandler}s which provide the same query interface that the product server does.
 * ProductServers effectively formulate the aggregate of a set of <i>related</i> QueryHandlers for a particular data resource or computation-providing system.
 * </p>
 * 
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */



public class ProductServer extends Prism.core.Component implements IProductServer{
	
	protected Vector handlers=new Vector();
	
	/**
	 * <p>Default Constructor for Product Server takes a unique URN to identify the product server in the architecture.</p>
	 * 
	 * @param name A unique URN identifying the product server.
	 */
	public ProductServer(String name)throws ClassNotFoundException,InstantiationException,IllegalAccessException{
		super(name);
		loadQueryHandlers(getQueryHandlerList(System.getProperty("glide.productHandlers")));
	}
	
	/**
	 * <p>Constructor takes both a unique name URN for the product server, along with a semi-colon (;) separated String list of
	 * {@link IQueryHandler} class names to attach to this product server. The query handlers are loaded using java's dynamic class
	 * loading facility.
	 * </p>
	 * 
	 * @param name A unique URN identifying the product server.
	 * @param theQueryHandlers 
	 */
	public ProductServer(String name,String theQueryHandlers)throws ClassNotFoundException,InstantiationException,IllegalAccessException{
		super(name);
		loadQueryHandlers(getQueryHandlerList(theQueryHandlers));		
	}

	
	/**
	 * <p>Function takes query object, hands it off to each the {@link IQueryHandler}s for this ProductServer, let's them
	 * populate the result list with anything that they can, and then returns the query object to the caller with (possibly)
	 * populated results.
	 * </p>
	 * 
	 * @param q The Query Object specifying the initial keyword query.
	 * @return The same {@link QueryObject} passed in, this time with (possibly) populated results.
	 */
	public QueryObject query(QueryObject q){

		/*for(Iterator i = handlers.iterator(); i.hasNext(); ){
			IQueryHandler qh = (IQueryHandler)i.next();
			q = qh.query(q);
		}*/
		
		for(int i=0; i < handlers.size(); i++){
			IQueryHandler qh = (IQueryHandler)handlers.elementAt(i);
			q = qh.query(q);
		}
		
		return q;
	}
	
	/**
	 * <p>Handle events for this Product Server.</p>
	 * <p>Events include:
	 * 
	 *    <ul>
	 *       <li>ProductQuery_Request, specifying:
	 *           <ul>
	 *              <li>productServer_id - the id of this product server to communicate with</li>
	 *              <li>queryServer_id - the id of the originating query server which (possibly) sent this request</li>
	 *              <li>query - the QueryObject to issue to this product server.</li>
	 *              <li>resourceProfile - an accompanying resource profile of the product(s) being requested of this product server.</li>
	 *              <li>orig_id - the unique URN identifier of the originator of this request.</li>
	 *           </ul>
	 *      </li>
	 *   </ul>
	 * 
	 * @param e The {@link Event} object the ProductServer received.
	 * @return void.
	 * 
	 * 
	 */
	public void handle(Event e){
		if(e.eventType == PrismConstants.REQUEST && e.name.equals("ProductQuery_Request")){
			
			String theProductServerID = (String)e.getParameter("productServer_id");
			String theQueryServerID = (String)e.getParameter("queryServer_id");
			
			if(theProductServerID != null){
				//make sure that this was intended for us, otherwise, don't even bother
				if(!theProductServerID.equals(name)){
					return;
				}
			}else return;
			
			
			QueryObject theQuery = (QueryObject)e.getParameter("query");
			Profile theProfile = (Profile)e.getParameter("resourceProfile");
			
			String returnClientID = (String)e.getParameter("orig_id");
			if(returnClientID == null){return;}  //if we don't know who to send the reply to, then don't bother
			
			if(theQuery != null){
				theQuery = query(theQuery);	
                
				//System.err.println(name+" sending ProductQuery_Reply to "+returnClientID);
				//System.err.println(name+" sending QueryObjet with query = "+theQuery.getQuery());
				Event n = new Event("ProductQuery_Reply");
				n.eventType = PrismConstants.REPLY;
				n.addParameter("returnedQuery",theQuery);
				n.addParameter("orig_id",returnClientID);
				n.addParameter("productServer_id",name);
				if(theProfile != null){n.addParameter("resourceProfile",theProfile);}
				if(theQueryServerID != null){n.addParameter("queryServer_id",theQueryServerID);}
				send(n);
			}
		}
		
		
	}
	
	private Vector getQueryHandlerList(String theList){
		if(theList == null){return null;}
		
		StringTokenizer st = new StringTokenizer(theList,";");
		Vector qList = new Vector();
		
		while(st.hasMoreTokens()){
			String qhClass = st.nextToken();
			qList.addElement(qhClass);
		}
		
		return qList;
	}
	
	
	private void loadQueryHandlers(Vector hList) throws ClassNotFoundException,InstantiationException,IllegalAccessException{
		
		if(hList==null){return;}
		
		for(int i2=0; i2 < hList.size(); i2++){
			
			IQueryHandler q1 = null;
			String cName = (String)hList.elementAt(i2);
			
			try{
				q1 = (IQueryHandler)Class.forName(cName).newInstance();				
				handlers.addElement(q1);
				
			}catch(java.lang.ClassNotFoundException c){
				System.out.println("PRISM Product Server: Unable to Find Handler Class!");
				throw c;
			}
			catch(ClassCastException cce){
				System.out.println("PRISM Product Server:  Caught Class Cast Exception!");
				cce.printStackTrace();
			}
			catch(java.lang.InstantiationException i){
				System.out.println("PRISM Product Server: Unable to Instantiate Class!");
				throw i;
			}
			catch(java.lang.IllegalAccessException iae){
				System.out.println("PRISM Product Server: Illegal Access Exception!");
				throw iae;
			}		
		
		}	
	}
	
	
	
	
	
	
}