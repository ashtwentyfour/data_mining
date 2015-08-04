package id3_decision_tree;

import java.util.*;


/**
 * 
 * the objects of this class represent a tuple in the table of the input data set
 * each tuple comprises of a list of attributes, a corresponding list of values, and
 * a attribute -> value mapping
 * 
 * @author Ashwin Menon
 * @version 1.0
 * @since 2015-07-15
 *
 *
 */

class Tuple {
	
	private List<String> list_of_attr;   // list of attributes
	private List<String> list_of_vals;   // list of corresponding values
	private HashMap<String , String> value_set;   // attribute -> value mapping
	
	
	/**
	 * constructor for initialization 
	 * @param attr list of attributes 
	 * @param values corresponding values
	 * 
	 */
	
	public Tuple(List<String> attr , List<String> values) {
		
		list_of_attr = new ArrayList<String>(attr);    // assign attributes
		list_of_vals = new ArrayList<String>(values);  // assign values
		
		// input check
		if((list_of_attr.size() != list_of_vals.size()) || 
			 list_of_attr.size() == 0 || list_of_vals.size() == 0) 
			
			throw new IllegalArgumentException("Incorrect size of attribute/value lists");
					
		
		value_set = new HashMap<String , String>();   // create attribute -> value mapping

		for(int index = 0; index < list_of_attr.size(); index++) 
			 value_set.put(list_of_attr.get(index), list_of_vals.get(index));
				
	}
	
	
	
	/**
	 * 
	 * returns the value of the attribute specified
	 * @param attr attribute name 
	 * @return value of attribute  
	 * 
	 */
	
	public String get_value(String attr) {
		
	   return value_set.get(attr);
	
	}
	
	
	
	/**
	 * 
	 * @return list of attributes
	 * 
	 */
	
	public List<String> get_attr_list() {
		
		return list_of_attr;
	
	}
	
	
	
	/**
	 * 
	 * prints the tuple data
	 * 
	 */
	
	public void print_tuple() {
		
        System.out.println("tuple: " + list_of_attr + " " + list_of_vals);
		
	}
	
	
	
	/**
	 * 
	 * @return size of tuple
	 * 
	 */
	
	public int tuple_size() {
		
		return value_set.size();
		
	}
	
	
}
