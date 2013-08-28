package glide.profile;

import java.util.Vector;
import glide.structs.Profile;
import glide.structs.QueryObject;


public interface IProfileServer{
	
	public Vector findProfiles(QueryObject q);
	
}