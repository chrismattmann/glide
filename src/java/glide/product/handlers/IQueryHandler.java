package glide.product.handlers;

import glide.structs.QueryObject;
import glide.product.ProductServer;

/**
 * <p>Query Handlers are members of Product Servers which actually translate the keyword query (specified in the
 * provided {@link QueryObject} to something that the underlying resource can understand. Examples of this translation are:<br><br>
 * 
 * <ul>
 *   <li>Translating the query <i>Author.Name LIKE Chris</i> to the sql command <code>SELECT * FROM books WHERE books.author LIKE '%Chris%'</code></li>
 *   <li>Translating the query <i>Favorite Sport = 'Basketball' and Favorite Player = Kobe</i> to a series of HTTP Get Requests to ESPN.com, NBA.com, etc., and subsequent parsing of HTML pages to retrieve information about Kobe.</li>
 *   <li>Etc....</li>
 * </ul>
 * 
 * <p>Each {@link ProductServer} has a set of 0 or more QueryHandlers. This is because there may be different ways that an underlying resource can provide data. Imagine that the resource to extract data from is a database and imagine that it has
 * both NBA players information, in the traditional relational format (e.g. tables), but it also has images of the NBA players, stored in binary format. You could create a ProductServer, with 2 query handlers, 1 to handle the relational statistical information for the players, 
 * and another to handle the binary image data to extract. There are obviously more scenarios than this. Which ones can you think of? :-)
 * 
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */

public interface IQueryHandler{
	
	/**
	 * <p>Product query handler query method takes in a QueryObject, specifying the keyword query (and additional metadata), and runs some 
	 * transformation on the query to retrieve data. Data is returned in the QueryObject's result list.</p>
	 * 
	 * @param q The QueryObject specifying the keyword query.
	 * @return A QueryObject with (possibly) populated results.
	 */
	public QueryObject query(QueryObject q);
	
	
}