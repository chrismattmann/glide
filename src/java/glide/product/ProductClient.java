package glide.product;

import Prism.core.*;
import Prism.util.*;

import java.util.Vector;
import glide.structs.QueryObject;

/**
 * <p>A ProductClient is a client of the functionality provided by the {@link ProductServer}. In essence, it sends query request events
 * that are answered by product servers. The query requests specify a:
 * 
 * <ul>
 *   <li>{@link QueryObject} with the query, and results list to populate</li>
 *   <li>The originator (URN pointing to the ProductClient component)</li>
 *   <li>Desired Product Server recipient.</li>
 * </ul>
 * 
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */


public class ProductClient extends Prism.core.Component{
	
  protected Vector results=null;
  protected String productServerID=null;

 
 /**
  * <p>Default constructor for a product client takes in a URN-based identifier for this product client and a product server id to communicate with.</p>
  * 
  * @param name The URN-based name of this ProductClient.
  * @param psID The URN-based name of the ProductServer to communicate with.
  * 
  */ 
  public ProductClient(String name,String psID){
  	super(name);
  	results = new Vector();
  	productServerID=psID;
  }
  
  /**
   * <p>Returns a {@link Vector} populated with {@link QueryObject}s received from any ProductServers responding to a particulary query.</p>
   * 
   * @return List of QueryObjects.
   */
  public Vector getResults(){return results;}
  
  
  /**
   * <p>Sends query request event to available product server with id specified by the {@link Event} metadata.</p>
   * 
   * @param q The QueryObject containing the query.
   * @return void.
   */
  public void query(QueryObject q){
  	
  	//make it so that this ProductClient is the originator of this query
  	q.setQueryOrig(name);
  	
  	Event e = new Event("ProductQuery_Request");
  	e.eventType = PrismConstants.REQUEST;
  	e.addParameter("query",q);
  	e.addParameter("orig_id",name);
  	e.addParameter("productServer_id",productServerID);
  	send(e);
  }
  
  /**
   * <p>Handle events received in the ProductClient.</p>
   * 
   * @param e The received Event object.
   * @return void.
   */
  public void handle(Event e){
  	if(e.eventType == PrismConstants.REPLY && e.name.equals("ProductQuery_Reply")){
  		if(e.getParameter("orig_id") != null){
  			String clientID = (String)e.getParameter("orig_id");
  			if(!clientID.equals(name)){return;} //this message was not intended for us
  		}
  		
  		//get returnedQuery
  		QueryObject qo = (QueryObject)e.getParameter("returnedQuery");
  		if(qo != null && qo.getQueryOrig().equals(name)){
            //make sure that this query was not null and was intended for us
  	  		results.addElement((QueryObject)e.getParameter("returnedQuery"));
  		}

  	}
  }
	
	
}