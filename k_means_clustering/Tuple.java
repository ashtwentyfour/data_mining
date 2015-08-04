package k_means_clustering;

import java.util.*;


/**
 * 
 * the objects of this class represent a tuple in the table of the input data set
 * each tuple comprises of a list of attributes, a corresponding list of values, and
 * a attribute -> value mapping
 * 
 * @author Ashwin Menon
 * @version 1.0
 * @since 2015-07-31
 *
 *
 */

class Tuple {
	
	private List<String> list_of_attr;   // list of attributes
	private List<String> list_of_vals;   // list of corresponding values
	private HashMap<String , String> value_set;   // attribute -> value mapping
	private Tuple center;  // the center representing the cluster the tuple currently belongs to
	private Double tol;    // tolerance limit for the comparison of two tuple objects 
	
	
	/**
	 * constructor for initialization 
	 * @param attr list of attributes 
	 * @param values corresponding values
	 * 
	 */
	
	public Tuple(List<String> attr , List<String> values) {
		
		list_of_attr = new ArrayList<String>(attr);    // assign attributes
		list_of_vals = new ArrayList<String>(values);  // assign values
		center = null;
		tol = 0.0001;
		
		// input check
		if((list_of_attr.size() != list_of_vals.size()) || 
			 list_of_attr.size() == 0 || list_of_vals.size() == 0) {
			
			throw new IllegalArgumentException("Incorrect size of attribute/value lists");
					
		}
		
		value_set = new HashMap<String , String>();   // create attribute -> value mapping

		for(int index = 0; index < list_of_attr.size(); index++) {
			 value_set.put(list_of_attr.get(index), list_of_vals.get(index));
		}
			
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
	 * @return list of values
	 * 
	 */
	
	public List<String> get_val_list() {
		
		return list_of_vals;
		
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
	
	
	
	/**
	 * 
	 * @return the attributes that have numerical values 
	 * 
	 */
	
	public List<String> get_numeric_attributes() {
		
		List<String> numeric_attrs = new ArrayList<String>();
		
		for(String attr: list_of_attr) {
			
			if(is_numeric(value_set.get(attr)) == true) {
				
				numeric_attrs.add(attr);
				
			}
			
		}
		
		if(numeric_attrs.size() == 0)
			throw new RuntimeException("no numeric arguments present");
		
		
		return numeric_attrs;
		
	}
	
	
	
	/**
	 * 
	 * @param val tuple to be tested
	 * @return true if the value is numerical, false otherwise
	 * 
	 */
	
	private Boolean is_numeric(String val) {  
		
	  try  {  
	    @SuppressWarnings("unused")
		Double d = Double.parseDouble(val);  
	  }  
	  catch(NumberFormatException nfe)  {  
	    return false;  
	  }  
	  return true;  
	}
	
	
	
	/**
	 * 
	 * @param c tuple which represents the center of the cluster
	 * 
	 */
	
	public void set_center(Tuple c) {
		
		
		if(center == null)  
			center = new Tuple(list_of_attr , c.get_val_list());
			
		
		else 
		    update_center(c);
		
	}
	
	
	
	/**
	 * 
	 * @return the current center of the cluster
	 * 
	 */
	
	public Tuple get_center() {
		
		return center;
		
	}
    
	
	/**
	 * 
	 * @param c new center tuple
	 * 
	 */
	
	private void update_center(Tuple c) {

		center = null;    
		center = new Tuple(c.get_attr_list() , c.get_val_list());	
		   
	}
	
	
	
	public boolean equals(Object obj) {   // compare two tuple objects 
		
		if(obj == null)
			return false;
		
		if(getClass() != obj.getClass())
			return false;
		
		final Tuple other = (Tuple) obj;
		
		// return false if the tuples have different number of arguments of values
		if((list_of_attr.size() != other.get_attr_list().size()) || 
		   (list_of_vals.size() != other.get_val_list().size()))
			       return false;
		
		
		/* 
		 * compute the distance between the two tuples 
		 * if dist < tolerance the two tuples are equal
		 * Here, since we compare tuples to compare centers, we ignore the
		 * differences in the non-numerical values
		 * 
		 */
		
		Double sum = 0.0;
		for(int i = 0; i < list_of_attr.size(); i++) {
			
			// if attributes titles do not match 
			if(!list_of_attr.get(i).equals(other.get_attr_list().get(i))) 
				return false;

			
			// if the attributes have numerical values compute (x1 - x2)^2
			if(is_numeric(get_value(list_of_attr.get(i))) && 
			is_numeric(other.get_value(list_of_attr.get(i)))) {
				
				Double diff = Math.abs(Double.parseDouble(get_value(list_of_attr.get(i))) - 
				Double.parseDouble(other.get_value(list_of_attr.get(i))));
				
	            sum += diff*diff;  // add to (x1 - x2)^2 + (y1 - y2)^2 ........
				
			}
			
		}
		
		Double norm = Math.sqrt(sum);  // compute the distance 
		
		if(norm > tol)   // 
		   return false;
		
	
		return true;
		
	
	}
	
	
	
	public int hashCode() {  // compute hashCode for the object 
		
		int hash = 17;
		
		hash = 23*hash + list_of_vals.get(1).hashCode();
		hash = 53*hash + (list_of_vals.get(2) != null ? list_of_vals.get(2).hashCode() : 0);
		
		return hash;
		
	}
	
	
	
	/**
	 * 
	 * print the values of the numerical attributes
	 * 
	 */
	
	public void print_numerical_values() {
		
		List<String> num_vals = center.get_numeric_attributes();
		System.out.print("[ ");
		for(String nv: num_vals) 
			System.out.print(center.get_value(nv) + " ");
		System.out.print("]");
		System.out.println("\n");
			
	}
	
}
