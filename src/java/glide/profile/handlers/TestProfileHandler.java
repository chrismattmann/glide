package glide.profile.handlers;


import glide.structs.Profile;
import glide.structs.QueryObject;


//import java.util.List;
import java.util.Vector;

public class TestProfileHandler implements IProfileHandler{
	
	public Vector findProfiles(QueryObject qo){
		Vector rProfiles = new Vector();
		rProfiles.addElement(new Profile());
		return rProfiles;
	}
	
}