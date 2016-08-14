package id3_decision_tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class Driver {

	public static void main(String[] args) throws FileNotFoundException {
		
        if(args.length <= 0) {
             System.err.println("Enter Training and Test Data File Paths");
             System.exit(0);
        }

		/* building decision tree using training data set */
		ID3 id = new ID3();
		String training_file = args[0];
		id.train_data(training_file , "Play"); // also specifying the decision attribute - Play? Yes or No
		
		// hard coded test data
		/*
		List<String> attr = new ArrayList<String>(); // list of attributes
		attr.add("Outlook");
		attr.add("Humid");
		attr.add("Wind");
		List<String> vals = new ArrayList<String>(); // list of corresponding values
		vals.add("Rain");
		vals.add("High");
		vals.add("Weak");
		id.predict_value(attr, vals); 
		*/

        List<String> attr = new ArrayList<String>();
        List<String> vals = new ArrayList<String>();
		String test_file = args[1];
        Scanner filereader = null;
		try {
		     filereader = new Scanner(new File(test_file));
		} catch (FileNotFoundException e) {
		     e.printStackTrace();
	    }

	    int index = 0;
	    while(filereader.hasNextLine() && index <= 1) { 
	    	Scanner linereader = new Scanner(filereader.nextLine());
            while(linereader.hasNext()) {
		    	String s = linereader.next();
		    	if(index == 0)
		    		attr.add(s);   // create a list of attributes
		    	else 
		    		vals.add(s);       // create a list of values
		    }
		    if(index == 0)            
		    	attr.remove(0);
		    if(vals.size() > 0) {
		       vals.remove(0);
		    }
		    linereader.close();
		    index++;
	    }

	    filereader.close();

        id.predict_value(attr, vals);
	}

}
