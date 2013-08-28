package glide.util;

import java.util.Vector;


/**
 * <p>A utility class for GLIDE containg static methods that should have been available
 * in JDK 1.1.8, but aren't unfortunately. Think of this as a class that will give you some of the new
 * JDK 1.2xx and 1.4.xx functionality, using the constructs of 1.1.8.</p>
 * 
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */

public class glideUtils{
	
	/**
	 * <p>Equivalent to the method <code>addAll</code> in the JDK 1.4.2 {@link Vector} class.
	 * 
	 * @param v1 One of the vectors to union.
	 * @param v2 The second vector to union.
	 * 
	 * @return A new {@link Vector} representing the <i>union</i> of v1 and v2.
	 */
	public static Vector addAll(Vector v1,Vector v2){
		Vector v3 = new Vector();
		
		for(int i=0; i < v1.size(); i++){
			v3.addElement(v1.elementAt(i));
		}
		
		for(int i=0; i < v2.size(); i++){
			v3.addElement(v2.elementAt(i));
		}
		
		return v3;
	}
	
	
}