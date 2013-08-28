package glide.product.handlers;


import java.util.Vector;

import glide.structs.QueryObject;
import glide.structs.Result;
import glide.util.mimeTypes;

/**
 * <p>Example Query Handler that adds a single {@link Result} to the result list of the passed in {@link QueryObject}.
 * 
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */

public class TestProductHandler implements IQueryHandler
{
	
	/**
	 * 
	 * <p>Method takes a query, ignores it, and returns one result in the query object results list.</p>
	 * 
	 * 
	 * @param q The QueryObject containing the query. This parameter is basically ignored.
	 * @return {@link QueryObject} with 1 hard-coded result.
	 */
	public QueryObject query(QueryObject q){
		q.getResults().addElement(new Result(mimeTypes.TEXT_PLAIN, "TestProductHandler", "This is a test result"));
		return q;
		
	}
	
	
	
}