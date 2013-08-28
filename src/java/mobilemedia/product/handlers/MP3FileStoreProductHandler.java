package mobilemedia.product.handlers;


import glide.product.handlers.IQueryHandler;
import glide.structs.QueryObject;
import glide.structs.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.w3c.tools.codec.Base64Encoder;
import org.w3c.tools.codec.Base64Decoder;
import org.w3c.tools.codec.Base64FormatException;



import java.util.StringTokenizer;


public class MP3FileStoreProductHandler implements IQueryHandler{
	
	public MP3FileStoreProductHandler(){}
	
	public QueryObject query(QueryObject q){
		//step #1:  get the File descriptor from the given ID
		//step #2:  read the contents of the file into a byte array
		//step #3:  create a String from the byte array
		//step #4:  create a Result with the given String, add it to the query object and send back
		
		String productID = q.getQuery();
		File theMP3 = getMp3(System.getProperty("mp3.fileStore.loc"),productID);
		
		FileInputStream fis = null;
		ByteArrayOutputStream out = null;
		
		System.err.println("Reading MP3 File "+theMP3.getName());
		try{
			fis = new FileInputStream(theMP3);
		}
		catch(FileNotFoundException fne){
		  System.out.println("Error, cannot find file "+theMP3.getName());
		  fne.printStackTrace();
		  System.out.println(fne.getMessage());
		  return q;
		}
	
		out = new ByteArrayOutputStream();		
		Base64Encoder encoder = new Base64Encoder(fis,out);
		try{
	        encoder.process();
			
		}
		catch(IOException theIOException){
            System.out.println(theIOException.toString());			
		}
		
		//String mp3Data = out.toString();
		Result theResult = new Result("application/mp3","MP3FileStoreProductHandler",null,out.toByteArray());
		QueryObject rq = new QueryObject();
		rq.setQueryOrig(q.getQueryOrig());
		rq.setQuery(q.getQuery());
		rq.getResults().addElement(theResult);
		return rq;
		
		
	}
	
	public static void main(String [] args){
		String kwdQuery = null;
		String usage = "java mobilemedia.product.handlers.MP3FileStoreProductHandler --query <String>\n";
		
		for(int i=0; i < args.length; i++){
			if(args[i].equals("--query")){
				kwdQuery = args[++i];
			}
		}
		
		if(kwdQuery==null){
			System.err.println(usage);
			System.exit(1);
		}
		
		MP3FileStoreProductHandler fph = new MP3FileStoreProductHandler();
		QueryObject qo = new QueryObject();
		qo.setQuery(kwdQuery);
		qo.setQueryOrig("MP3FileStoreProductHandler Test");
		
		qo = fph.query(qo);
		
        FileOutputStream outFile=null;
        
        try{
        	outFile = new FileOutputStream(new File("/returnedMP3.mp3"));
        	Result theResult = (Result)qo.getResults().elementAt(0);
            String theData = (String)theResult.getData();
            Base64Decoder decoder = new Base64Decoder(new ByteArrayInputStream(theData.getBytes()),outFile);
            decoder.process();
        }
        catch(IOException theIOException){
        	theIOException.printStackTrace();
        	System.err.println(theIOException.getMessage());
        }
        catch (Base64FormatException e) {
            System.out.println(e.toString());
         }

		
		
	}
	
	private MP3Info getMP3Info(String ID){
		StringTokenizer st = new StringTokenizer(ID,":");
		
		MP3Info mpi = new MP3Info(st.nextToken(),st.nextToken(),st.nextToken());
		return mpi;
	}
	
	
	private File getMp3(String loc,String ID){
		MP3Info theMP3Info = getMP3Info(ID);
		File rootDir = new File(loc);
		
		String [] genreDirs = rootDir.list();
		
		for(int i = 0; i < genreDirs.length; i++){
			File genreDir = new File(rootDir.getAbsolutePath()+"\\"+genreDirs[i]);
			if(genreDir.getName().equals(theMP3Info.getGenre())){
				String [] qualityDirs = genreDir.list();
				
				for(int j=0; j < qualityDirs.length; j++){
					File qualityDir = new File(genreDir.getAbsolutePath()+"\\"+qualityDirs[j]);
					
					if(qualityDir.getName().equals(theMP3Info.getQuality())){
						String [] mp3Files = qualityDir.list();
						
						for(int k=0; k < mp3Files.length; k++){
							File mp3File = new File(qualityDir.getAbsolutePath()+"\\"+mp3Files[k]);
							
							if(mp3File.getName().equals(theMP3Info.getName())){
								return mp3File;
							}
							
						}						
					}	
				}				
			}
		}
		
		return null;
	}
	
	class MP3Info{
		protected String genre=null;
		protected String quality=null;
		protected String name=null;
		
		public MP3Info(String g,String q,String n){
			genre = g;
			name = n;
			quality = q;
		}
		
		public String getGenre(){return genre;}
		public String getQuality(){return quality;}
		public String getName(){return name;}
		
		public void setGenre(String g){genre = g;}
		public void setQuality(String q){genre = q;}
		public void setName(String n){name = n;}
	}
	
}