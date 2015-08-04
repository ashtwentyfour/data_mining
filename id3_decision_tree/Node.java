package id3_decision_tree;

import java.util.*;
import java.util.Map.Entry;


/**
 * 
 * objects of this class represent a node in the decision tree
 * @author Ashwin Menon
 * @version 1.0
 * @since 2015-07-18
 *
 */

class Node {
	
	private Boolean is_pure;          // true is the node is a leaf node
	private List<Tuple> table_subset; // subset of the original table 
	private String class_attr;        // decision attribute 
	private String splitting_attribute;  // attribute on which the node is further split
	private List<Node> children;     // list of children
	private Node left;             // left child (for numeric splitting attributes)
	private String parent_split_attr_value; // splitting attribute value of the parent node 
	private Node right; // right child (for numeric splitting attributes)
	private Double split_value; // split value/point 
	private Node parent; // parent node 
	private List<String> list_of_attr; // list of attributes 

    /**
     * 
     * @param subset subset of the original input table
     * @param c_attr decision attribute 
     * @param attr_list list of attributes
     * 
     */
	
	Node(List<Tuple> subset , String c_attr , List<String> attr_list) {
		
		class_attr = c_attr;
		table_subset = new ArrayList<Tuple>(subset);
		left = null;
		right = null;
		parent_split_attr_value = null;
		is_pure = false;
		splitting_attribute = "";
		children = null;
		split_value = Double.NEGATIVE_INFINITY;
		parent = null;
		list_of_attr = new ArrayList<String>(attr_list);

	}
	
	
	
	/**
	 * 
	 * @param v attribute on which the parent node has been split 
	 */
	
	public void set_parent_split_attr_val(String v) {
		
		parent_split_attr_value = v;
		
	}
	
	
	
	/**
	 * 
	 * get attribute on which the parent node was split 
	 * 
	 */
	
	public String get_parent_attr_val() {
		
		return parent_split_attr_value;
		
	}
	
	
	
	/**
	 * 
	 * return the left child of the node
	 * 
	 */
	
	public Node get_left() {
		
		return left;
	}
	
	
	
	/**
	 * 
	 * return the right child of the node
	 * 
	 */

	public Node get_right() {
		
		return right;
	}
	
	
	
	/**
	 * 
	 * @return splitting attribute 
	 * 
	 */
	
	public String splitting_attribute() {
		
		return splitting_attribute;
	}
	
	
	
	/**
	 * 
	 * @param l Node object to be set as left node of current node 
	 * 
	 */
	
	public void set_left(Node l) {
		
		left = l;
	}
	
	
	
	/**
	 * 
	 *  @param x index of child Node to be returned
	 * 
	 */
	
	public Node child(int x) {
		
		return children.get(x);
		
	}
	
	
	
	/**
	 * 
	 * @param r Node object to be set as right node of current node
	 * 
	 */
	
	public void set_right(Node r) {
		
		right = r;
	}
	
	
	
	/**
	 * 
	 * @return the Node's split value
	 * 
	 */
	
	public Double split_pt() {
		
		return split_value;
	}
	
	
	
	/**
	 *
	 * print the decision/classification of the input tuple
	 * 
	 */
	
	public void retrieve_result() {
		
		if(is_leaf() == true)
			System.out.println("Decision: " + (table_subset.get(0)).get_value(class_attr));
		else 
			System.out.println("Decision: " + majority_vote());
		
	}
	
	
	
	/**
	 * 
	 * @return the decision attribute with the highest frequency/count
	 * 
	 */
	
	private String majority_vote() {
		
		HashMap<String , Integer> count = new HashMap<String , Integer>();
		
		for(Tuple t: table_subset) {
			
			if(count.containsKey(t.get_value(class_attr)) == false)
				count.put(t.get_value(class_attr), 1);
			else
				count.put(t.get_value(class_attr), count.get(t.get_value(class_attr)) + 1);
				
		}
		
		int majority = -Integer.MAX_VALUE;
		String result = null;
		for(Entry<String , Integer> entry:count.entrySet()) {
			
			if(entry.getValue() > majority) {
				majority = entry.getValue();
				result = entry.getKey();
			}
			
		}
		
		return result;
		
	}
	
	
	
	/**
	 * 
	 * @return true if the node is a leaf node 
	 * 
	 */
	
	private Boolean is_pure() {
         
		 if(is_pure == true || table_subset.size() == 0) {
			 return true;
		 }
			 
		 /* if the decision attribute value is not the same throughout the table
		  * the node cannot be pure */
		 
		 String class_attr_val = null;
		 class_attr_val = (table_subset.get(0)).get_value(class_attr);
		 for(Tuple t: table_subset) {

			 if(t.get_value(class_attr).equals(class_attr_val) == false) {
				     return false;
			 }
		 }
		 
		 is_pure = true;
		 
		 return true;
		
	}
	
	
	
	/**
	 *
	 * set node as leaf node
	 * 
	 */
	
	public void set_as_leaf() {
		
		is_pure = true;
		
	}
	
	
	
	/**
	 * 
	 * @return true if node is a leaf node 
	 * 
	 */
	
	public Boolean is_leaf() {
		
		return is_pure();
	
	}
	
	
	
	/**
	 * 
	 * @param p new parent of node 
	 * 
	 */
	
	public void set_parent(Node p) {
		
		parent = p;
		
	}
	
	
	
	/**
	 * 
	 * @return parent of node
	 * 
	 */
	
	public Node parent() {
		
		return parent;
		
	}
	
	
	
	/**
	 * 
	 * @return number of children 
	 * 
	 */
	
	public int num_children() {
		
		if(binary_split())  // if the node has left and right children 
			return 2;
		else if(children != null && children.size() > 0 && binary_split()==false)
			return children.size();
		else
			return 0;
		
	}
	
	
	
	/**
	 * 
	 * @return true if the node has been split into children two by numeric value
	 * 
	 */
	
	public Boolean binary_split() {
		
		return split_value != Double.NEGATIVE_INFINITY;
		
	}
	
	
	
	/**
	 * 
	 * @param c child Node object to be added to the list of children 
	 * 
	 */
	
	public void add_child(Node c) {
		
		if(children == null) {   // if c is the first child to be added 
			
			children = new LinkedList<Node>();
			children.add(c);
		
		}
		
		else
		    children.add(c);
		
	}
	

	
	/**
	 * 
	 * @return get the attribute on which the node has to be split 
	 * 
	 */
	
	public List<String> get_splitting_attribute() {
		
		return attribute_selection_method();
		
	}
	
	
	
    /**
     * 
     * @return splitting attribute based on attribute with the highest 
     *         information gain value
     */
	
	private List<String> attribute_selection_method() {
		
		 /* returns expected information needed to classify a tuple in the table */
         Double info_d = info_D(table_subset);   
         
         /* initialize the maximum gain value */
         Double max_gain = Double.NEGATIVE_INFINITY;
         
         /* initialize the splitting attribute */
         String splitting_attr = list_of_attr.get(0);
         List<Double> final_list = new ArrayList<Double>();
 
         for(String attr: list_of_attr) {
        	 
        	 if(attr.equals(class_attr) == false) {
        		 
        	   Double gain = info_d - info_A(attr).get(0); // compute information gain 

        	   if(gain > max_gain) { // update the maximum gain value 
        		   
        		  final_list = info_A(attr);
        		  max_gain = gain; 
        		  splitting_attr = attr; // update splitting attribute 
        				 
        	   }
        	 
        	 }
         }
         
         List<String> result = new ArrayList<String>();
         result.add(splitting_attr);   
         splitting_attribute = splitting_attr;


         if(final_list.size() == 2) {  // if a split value was computed add this value to result
        	 
        	 result.add(String.valueOf(final_list.get(1)));
        	 split_value = final_list.get(1);
        	 
         }

         return result;
		
	}
	
	
	
	
	/**
	 * 
	 * @param attr attribute for which info_A must be computed 
	 * @return expected information needed to classify a tuple if the 
	 *         tuples are partitioned by age 
	 *         
	 */
	
	private List<Double> info_A(String attr) {
		
		String o = (table_subset.get(0)).get_value(attr);
		
		if(is_numeric(o)) {  // if the attribute has numeric values 
			
			List<Double> array_of_numbers = new ArrayList<Double>(); 
			
			for(Tuple t: table_subset) 
				array_of_numbers.add(Double.valueOf(t.get_value(attr)));
			
			Collections.sort(array_of_numbers); // sort the values 
			
			List<Double> array_of_split_pts = new ArrayList<Double>(); // form a array of split points 
			
			Double mid = 0.0;
			for(int j = 0; j < array_of_numbers.size() - 1; j++) {
				
				mid = (array_of_numbers.get(j) + array_of_numbers.get(j+1))/2; // mid = (a_i + a_(i+1))/2
				array_of_split_pts.add(mid);
				
			}
			
			Double inf_A = Double.POSITIVE_INFINITY; // initialize min info_A value
			Double total = 0.0;
			Double split_pt = 0.0;
			for(Double x: array_of_split_pts) {
				
				Double inf = compute_inf(x , attr , table_subset);
				total += inf;
				if(inf < inf_A) {
					inf_A = inf;
				    split_pt = x;
			    }
				
			}
			
			List<Double> result = new ArrayList<Double>();
			result.add(total);
			result.add(split_pt);
			return result;
			
		}
		
		else {
				
			Set<String> types = new HashSet<String>();
			for(Tuple t: table_subset) {
				
				types.add(t.get_value(attr));
				
			}
			
			List<Double> inf_list = new ArrayList<Double>();
			
			List<Tuple> new_table = new ArrayList<Tuple>(table_subset);
			
			
			for(String p: types) {
				
				Double inf = compute_inf(new_table , p , attr);
				inf_list.add(inf);
				
			}
			
			Double sum = 0.0;
			for(Double x: inf_list)
				sum += x;
			
			List<Double> result = new ArrayList<Double>();
			result.add(sum);
			
			return result;
			
		}
		
	
	}
	
    
	
	/**
	 * 
	 * @param split_pt split point 
	 * @param attr attribute for which we compute expected info
	 * @param table_subset
	 * @return expected info for split point 'split_pt' of attribute 'attr'
	 * 
	 */
	
	private Double compute_inf(Double split_pt , String attr , List<Tuple> table_subset) {

		 HashMap<String , Double> count = new HashMap<String , Double>();
		 
		 /* 
		  * compute sizes of sets of tuples satisfying A > split_pt 
		  * and A <= split_pt 
		  * 
		  * */
		 
		 for(Tuple t: table_subset) {
			 
			 if(Double.valueOf(t.get_value(attr)) <= split_pt) {
				 
				 if(count.containsKey("L") == false)
					 count.put("L" , 1.0);	 
				 else
					 count.put("L", count.get("L") + 1.0);
					 	 
			 }
			 
			 else {
				 
				 if(count.containsKey("G") == false)
					 count.put("G" , 1.0);	 
				 else
					 count.put("G", count.get("G") + 1.0);
				 
			 }
			 	 
		 }
		 
		 /* compute info value for the current split point */
		 Double sum_of_p = count.get("G") + count.get("L");
		 Double p_1 = count.get("G")/sum_of_p;
		 Double p_2 = count.get("L")/sum_of_p;
		 
		 Double inf = (sum_of_p/table_subset.size())*(-p_1*log(p_1 , 2) - p_2*log(p_2 , 2));
		 
		 return inf;
		
	
	}
	
	
	
	/**
	 * 
	 * @param table_subset current subset of the table 
	 * @param p value of attribute for which we compute expected info 
	 * @param attr attribute for which we compute expected info
	 * @return expected info for value 'p' of attribute 'attr'       
	 * 
	 */
	
	private Double compute_inf(List<Tuple> table_subset , String p , String attr) {
		
		HashMap<String , Double> count = new HashMap<String , Double>();	
		
		/* 
		 * computing the frequency of different values of the decision attribute for 
		 * the value 'p' of attribute 'attr'
		 * 
		 * */
		
		for(Tuple t: table_subset) {
			
			if(p.equals(t.get_value(attr))) {

				if(count.containsKey(t.get_value(class_attr)) == false)
					count.put(t.get_value(class_attr) , 1.0);
				else 
					count.put(t.get_value(class_attr), count.get(t.get_value(class_attr)) + 1.0);
				
			}

				
		}
	
		/* compute info value */
		int sum_of_p = 0;
		
		for(Entry<String , Double> entry:count.entrySet()) {
		   sum_of_p += entry.getValue();
		}
		
		Double sum = 0.0;
		
		for(Entry<String , Double> entry:count.entrySet()) {
			
			Double p_i = entry.getValue()/sum_of_p;
			sum += p_i*log(p_i , 2);
			
		}
		
		sum = (-sum);
		
		return (sum_of_p/table_subset.size())*sum;
		
	
	}
	
	
	
	/**
	 * 
	 * compute expected information needed to classify a tuple in the table
	 * @param tablse_subset current subset of the table 
	 * @return info_D - expected info value 
	 * 
	 */
	
	private Double info_D(List<Tuple> table_subset) {
		
		HashMap<String , Double> class_count = new HashMap<String , Double>();
		
		/* count number of each type of decision attribute value */
		for(Tuple t: table_subset) {
			
			if(class_count.containsKey(t.get_value(class_attr)) == false)
				class_count.put(t.get_value(class_attr) , 1.0);
			else 
				class_count.put(t.get_value(class_attr) , 
				class_count.get(t.get_value(class_attr)) + 1.0);
					
		}
		
		/* compute info_D */
		Double sum = 0.0;
		int mod_D = table_subset.size();
		
		for(Entry<String , Double> entry:class_count.entrySet()) {
			
			Double p_i = entry.getValue()/mod_D;
			sum += p_i*log(p_i , 2);
			
		}
		
		return -sum; // return result 
			
	}
	
	
	
	/**
	 * 
	 * @return log of a to the base 2
	 * 
	 */
	
	private Double log(Double a , int b) {
		
		if(a == 1.0)
			return 0.0;
		else
			return Math.log(a)/Math.log(b);
		
	}
	
	
	
    /**
     * 
     * @param s value from the table 
     * @return true if the value is numeric 
     * 
     */
	
	private Boolean is_numeric(String s) {
		
		try {  
		  @SuppressWarnings("unused")
		Double d = Double.parseDouble(s);  
		}  
		catch(NumberFormatException nfe) {  
		  return false;  
		}  
		  return true;
	
	}
	

}
