package glide.profile.handlers;


import glide.structs.QueryObject;
import java.util.Vector;

public interface IProfileHandler{

	/* gets profiles from associated data store or generates them dynamically */
	public Vector findProfiles(QueryObject qo);
		
}