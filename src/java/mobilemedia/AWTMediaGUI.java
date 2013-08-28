package mobilemedia;


import java.awt.event.*;
import java.awt.*;

import java.util.Vector;

 

public class AWTMediaGUI extends Frame implements ActionListener {
  	
	//private JTextField mediaQuery=null;	  	
	private Button queryButton;
	private java.awt.List mp3GenreField=null, mp3QualityField=null;
	private Button btClose; // Declare "Close" button
	AWTMediaQueryGUI parent;
	
		public AWTMediaGUI(AWTMediaQueryGUI p){
			parent = p;
			this.setTitle(parent.name);
			
			
			// Use panel p1 to group text fields
			Panel p1 = new Panel();
			Panel p2 = new Panel();
			Panel p3 = new Panel();

			//p1.setSize(350,45);
			//p2.setSize(350,45);
			//p3.setSize(350,45);
			
			
			mp3GenreField = new java.awt.List(3,true);
			mp3GenreField.addItem("r&b");
			mp3GenreField.addItem("pop");
			mp3GenreField.addItem("ballad");
			
			mp3QualityField = new java.awt.List(2,true);
			mp3QualityField.addItem("192");
			mp3QualityField.addItem("128");
			
			
			p1.setLayout(new FlowLayout());
			p1.add(new Label("Genre"));
			p1.add(mp3GenreField);
			//p1.add(new Label("Media Query"));
			//p1.add(mediaQuery = new JTextField(30));
			
			p2.setLayout(new FlowLayout());
			p2.add(new Label("Quality"));
			p2.add(mp3QualityField);				
			
			p3.setLayout(new FlowLayout());
			p3.add(queryButton = new Button("Query"));
			p3.add(btClose = new Button("Close"));
			
			setLayout(new BorderLayout());
			add(p1,BorderLayout.NORTH);
			add(p2,BorderLayout.CENTER);
			add(p3,BorderLayout.SOUTH);

			// Register listener
			queryButton.addActionListener(this);
			btClose.addActionListener(this);
		}

  public void actionPerformed(ActionEvent e){
  	
	  if (e.getSource() == queryButton) {
	  	Object [] theGenres= mp3GenreField.getSelectedItems();
	  	Object [] theQualities = mp3QualityField.getSelectedItems();
	  	
	  	//System.err.println("the qualities length is "+theQualities.length);
	  	
	  	String theGenreString="",theQualityString="";
	  	
	  	for(int i=0; i < theGenres.length; i++){
	  		String gItem = (String)theGenres[i];
	  		theGenreString+="MP3.Genre = "+gItem+" OR ";
	  	}
	  	
	  	System.err.println("the genre string = "+theGenreString);
	  	
	  	theGenreString = theGenreString.substring(0,theGenreString.length()-4);

	  	for(int i=0; i < theQualities.length; i++){
	  		String qItem = (String)theQualities[i];
	  		//System.err.println("the quality string item = "+qItem);
	  		theQualityString+="MP3.Quality = "+qItem+" OR ";
	  	}
	  	
	  	System.err.println("the quality string = "+theQualityString);
	  	
	  	theQualityString = theQualityString.substring(0,theQualityString.length()-4);
	  	
	  	String theMediaQuery = theGenreString+" AND "+theQualityString;
	  	System.out.println("The Query is "+theMediaQuery);
	  	parent.doQuery(theMediaQuery);
	  }
	  else if (e.getSource() == btClose) {
		  System.out.println(parent.name+" Closing...");
		  this.setVisible(false);    // hide the Frame
		  this.dispose();            // free the system resources
		  System.exit(0);            // close the application  
	  }  
  }		
	
}