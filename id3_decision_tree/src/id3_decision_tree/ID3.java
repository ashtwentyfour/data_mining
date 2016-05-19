package id3_decision_tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * 
 * class acts as a wrapper around the decision tree object and invokes its methods 
 * to build the tree and make decisions on input tuples
 * 
 * @author Ashwin Menon
 * @version 1.0
 * @since 2015-07-15
 *
 */

public class ID3 {
	
	private List<Tuple> table;  // input table with training data
	private Decision_Tree dt;   // decision tree to be built
	
	
	/**
	 * function invokes the build decision tree functions
	 * @param file input file with the data set
	 * @param class_attribute attribute on which the final decision is made 
	 * 
	 */
	
	public void train_data(String file , String class_attribute) {
		
		table = new ArrayList<Tuple>(make_table(file));    // create a list of tuples as the table
		dt  = new Decision_Tree(table , class_attribute);  // create and build the decision tree
		dt.build_decision_tree(table);
		
	}
	
	
	
	/**
	 * 
	 * process the input file to create the required table
	 * @param file input file with training data
	 * @return table - array of tuples
	 * 
	 */
	
	private List<Tuple> make_table(String file) {
		
	    Scanner filereader = null;
		try {
		     filereader = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
		     e.printStackTrace();
	    }
		
		List<String> attributes = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		List<Tuple> table = new ArrayList<Tuple>();
		int index = 0;
		
		/* begin reading file line by line */
		while(filereader.hasNextLine()) {            
			Scanner linereader = new Scanner(filereader.nextLine());
		    while(linereader.hasNext()) {
		    	String s = linereader.next();
		    	if(index == 0)
		    		attributes.add(s);   // create a list of attributes
		    	else 
		    		values.add(s);       // create a list of values
		    }
		    if(index == 0)            
		    	attributes.remove(0);
		    index++;
		    if(values.size() > 0) {
		       values.remove(0);
		       List<String> tuple_values = new ArrayList<String>(values); // create new tuple 
		       Tuple t = new Tuple(attributes , tuple_values);
		       values.clear();
		       table.add(t);
		    }
		    linereader.close();
		    
		}
		
		filereader.close();
		
		return table;
		
	}
	
	

	/**
	 * 
	 * predicts the output value for the tuple
	 * @param attr attributes of the input tuple 
	 * @param vals corresponding values
	 * 
	 */
	
	public void predict_value(List<String> attr , List<String> vals) {
		
		if (dt.size() == 0) {
		   throw new RuntimeException("the decision tree is empty");
		}
			
		Tuple p = new Tuple(attr , vals);
		dt.print_decision(p);
		
	}
	

}
