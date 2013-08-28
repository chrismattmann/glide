package glide.structs;

/**
 * <p>An interface for GLIDE data components to explicitly deconstruct an object (i.e.
 * nullify its memory consumption immediately. All data componnets should implement this interface
 * because you don't want to wait for the garbage collector to come along because that could take forever.
 * In the PRISM setting, it would possibly be inefficient.</p>
 * 
 * 
 * 
 * @author USC Software Architecture Research Group and JPL OODT Research Group
 * @version 1.0
 * 
 */

public interface IDeletable{
	
	/**
	 * <p>Implement this method as a means of nulling out any dynamic memory that the data component class
	 * might be consuming.</p>
	 * 
	 * @return void.
	 */
	public void deconstruct();
}