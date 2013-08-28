package glide.product;

import Prism.core.Component;
import Prism.core.Event;

import glide.structs.QueryObject;
import glide.product.handlers.IQueryHandler;


/**
 * <p>A Product Server receives a query, translates it into <i>some format</i> an underlying resource
 * can understand, then delivers the result back to the user. Product Servers have 0 or more {@link IQueryHandler}s 
 * which provide translation methods for taking a query and returning data.
 * </p>
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */

public interface IProductServer{
	
	/**
	 * 
	 * <p>Method takes a QueryObject data component, extracts the keyword query, and then iterates through
	 * each of its query handlers, giving them each a chance to populate the results portion of the QueryObject.
	 * </p>
	 * 
	 * @param q The QueryObject specifying the keyword query string and additional metadata.
	 * @return A {@link QueryObject} with potentially populated results.
	 */
	public QueryObject query(QueryObject q);

}