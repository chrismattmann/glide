package mobilemedia.profile.handlers;

import glide.profile.handlers.IProfileHandler;
import glide.structs.Profile;
import glide.structs.ProfileElement;
import glide.structs.QueryObject;
import glide.queryparser.IExpression;
import glide.queryparser.QueryParser;


import java.util.Vector;
//import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.File;

public class MP3FileStoreProfileHandler implements IProfileHandler{
	
	public MP3FileStoreProfileHandler(){}
	
	public Vector findProfiles(QueryObject qo){
		//step #1:  Build list of Profiles from MP3 file store
		//step #2:  Parse the Query
		
		//step #3:  For each profile, assign the profile elements to the parsed query structure
		//step #4:  Evaluate it -- if true, then add that to the list
		//                         if false, move on
		
		Vector returnProfiles = new Vector();
		Vector mp3Profiles = buildProfiles(System.getProperty("mp3.fileStore.loc"));
		IExpression parsedQuery = new QueryParser().parse(new StringTokenizer(qo.getQuery()," "),null);
		
		for(int i=0; i < mp3Profiles.size(); i++){
			Profile theProf = (Profile)mp3Profiles.elementAt(i);
			setQueryParams(parsedQuery,theProf);
			if(parsedQuery.evaluate()){
				returnProfiles.addElement(theProf);
			}
			
		}
		
		return returnProfiles;
		
	}
	
	public static void main(String [] args){
		String kwdQuery = null;
		String usage = "java mobilemedia.profile.handlers.MP3FileStoreProfileHandler --query <String>\n";
		
		for(int i=0; i < args.length; i++){
			if(args[i].equals("--query")){
				kwdQuery = args[++i];
			}
		}
		
		if(kwdQuery==null){
			System.err.println(usage);
			System.exit(1);
		}
		
		
		MP3FileStoreProfileHandler h = new MP3FileStoreProfileHandler();
		QueryObject qo = new QueryObject();
		qo.setQuery(kwdQuery);
		Vector profs = h.findProfiles(qo);
		
		for(int i=0; i < profs.size(); i++){
			Profile p = (Profile)profs.elementAt(i);
			System.out.println(p.toXMLString());
		}
		
	}
	
	private void setQueryParams(IExpression pQuery,Profile p){
		for(int i=0; i < p.getProfileElements().size(); i++){
			ProfileElement pe = (ProfileElement)p.getProfileElements().elementAt(i);
			pQuery.setProfileElementValue(pe.getName(),pe.getValue());
		}
	}
	
	
	private Vector buildProfiles(String loc){
		Vector theProfiles = new Vector();
		File rootDir = new File(loc);
		
		//File [] genreDirs = rootDir.listFiles();
		String [] genreDirs = rootDir.list();
		
		if(genreDirs==null)
			 return null;
		
		for(int i = 0; i < genreDirs.length; i++){
			//System.out.println("Examining "+rootDir.getAbsolutePath()+"\\"+genreDirs[i]);
			
			File genreDir = new File(rootDir.getAbsolutePath()+"\\"+genreDirs[i]);
			String [] qualityDirs = genreDir.list();
			
			if(qualityDirs == null)
				  continue;
			
			for(int j=0; j < qualityDirs.length; j++){
				File qualityDir = new File(genreDir.getAbsolutePath()+"\\"+qualityDirs[j]);
				String [] mp3Files = qualityDir.list();
				
				if(mp3Files == null)
					 continue;
				
				for(int k=0; k < mp3Files.length; k++){
					File mp3File = new File(qualityDir.getAbsolutePath()+"\\"+mp3Files[k]);
					Profile p = createMp3Profile(mp3File,genreDir.getName(),qualityDir.getName());
					theProfiles.addElement(p);
				}
				
			}
		}
		
		return theProfiles;
	}
	
	private Profile createMp3Profile(File mp3,String genre,String quality){
		Profile theProfile = new Profile();
		theProfile.getProfileAttributes().setProfileCreator("MP3FileStoreProfileHandler");
		theProfile.getProfileAttributes().setProfileID("urn:glide:prism:"+mp3.getName());
		
		theProfile.getResourceAttributes().setResourceID(genre+":"+quality+":"+mp3.getName());
		theProfile.getResourceAttributes().setResourceClass("glide.productServer");
		theProfile.getResourceAttributes().getResourceLocations().addElement("urn:glide:prism:MediaProductServer");
		theProfile.getResourceAttributes().getAuthors().addElement("Chris Mattmann");
		theProfile.getResourceAttributes().getCreators().addElement("Chris Mattmann");
		theProfile.getResourceAttributes().getOwners().addElement("Chris Mattmann");
		
		ProfileElement pe1 = new ProfileElement();
		pe1.setName("MP3.Genre");
		pe1.setValue(genre);
		
		ProfileElement pe2 = new ProfileElement();
		pe2.setName("MP3.Quality");
		pe2.setValue(quality);
		
		theProfile.getProfileElements().addElement(pe1);
		theProfile.getProfileElements().addElement(pe2);
		
		return theProfile;
		
	}
}