package glide.product.handlers;

import java.util.StringTokenizer;
import java.util.Hashtable;

import glide.structs.QueryObject;
import glide.structs.Result;
import glide.util.mimeTypes;

/**
 * <p>Example Query Handler that contains a hard coded database (a {@link Hashtable}). It accepts a keyword query
 * specified by a {@link QueryObject} and returns {@link Result}s it found in the hard coded DB. Queries are in the format of:<br><br>
 * 
 * <code>KEY=[some key]</code><br>
 * 
 * <p>Replace [some key] with either DRDF, MDS, or Chris</p>
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */

public class HardCodedDBHandler implements IQueryHandler
{
	
    private Hashtable db = null;
    
    /**
     * <p>Initialize the hard coded db and return a new handler.</p>
     * 
     */
    public HardCodedDBHandler(){
    	db = new Hashtable();
    	
    	db.put("DRDF","100%");
    	db.put("MDS","50%");
    	db.put("Chris","150%");
    }
    
    /**
     * <p>Method takes a query object, which specifies a keyword query, and then queries the hard coded
     * database, and returns any results it found.</p>
     * 
     * @param q The QueryObject containing the keyword query.
     * @return A {@link QueryObject} with (possibly) populated results.
     */
	public QueryObject query(QueryObject q){
		//System.err.println("HardCodedDBHandler inside");
		
		String queryStr = q.getQuery();
		
		if(queryStr.indexOf("=") != -1 && queryStr.indexOf("=") == queryStr.lastIndexOf("=")){
			StringTokenizer st = new StringTokenizer(queryStr,"=");
			
			String dataElement = (String)st.nextToken();
			String dataValue = (String)st.nextToken();
			
			//System.err.println("dataElement = "+dataElement);
			//System.err.println("dataValue = "+dataValue);
			
			if(dataElement.equals("KEY")){
				String dbVal = (String)db.get(dataValue);
				q.getResults().addElement(new Result(mimeTypes.TEXT_PLAIN,"HardCodedDBHandler",dbVal));
			}
			else{
				System.err.println("DATA ELEMENT WAS NOT = KEY");
			}
		}
		
		return q;
		
	}
	
}