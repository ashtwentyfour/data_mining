package id3_decision_tree;

import java.util.*;



/**
 * 
 * @author Ashwin Menon
 * @version 1.0
 * @since 2015-07-16
 *
 */


class Decision_Tree {
	
	private Node root;  // root node
	private List<String> attr_list; // attribute list
	private String c_attr; // decision attribute
	private int size; // size of tree
	
	
	/**
	 * 
	 * @param table input data table (list of tuples)
	 * @param class_attr decision attribute
	 * 
	 */
	
	public Decision_Tree(List<Tuple> table , String class_attr) {
		
		List<String> attributes = (table.get(0)).get_attr_list(); // create a list of attributes
		attr_list = new ArrayList<String>(attributes);
		root = new Node(table , class_attr , attributes); // initialize the root node 
		c_attr = class_attr;
		size = 0;
			
	}
	
	
	
	/**
	 * 
	 * builds decision tree from the input data table
	 * @param table input data table
	 * 
	 */
	
	public void build_decision_tree(List<Tuple> table) {
		
		build_tree(root , table , c_attr); // build tree
		
	}
	
	
	
	/**
	 * 
	 * forms a subset of the table for attributes with numeric values
	 * @param table input table 
	 * @param attr attribute whose values the table is split on
	 * @param split_pt split point for numeric values
	 * @param greater boolean value to look for values greater than or less than the 
	 *        split point
	 * @return required subset of the table
	 * 
	 */
	
	private List<Tuple> form_subset(List<Tuple> table , String attr, 
		    Double split_pt , Boolean greater) {
		
		/* create a new list of tuples as the subset */
		List<Tuple> subset = new ArrayList<Tuple>();  
		
		for(Tuple t: table) {
			
		  if(greater == true)
			 if(Double.valueOf(t.get_value(attr)) > split_pt) // greater than split point
			     subset.add(t);
		  else
			  if(Double.valueOf(t.get_value(attr)) <= split_pt) // less than or equal to split point
				  subset.add(t);
		
		}
		
		return subset;
		
	}
	
	
	
	/**
	 * 
	 * form subset for attribute with non-numeric data
	 * @param table input table
	 * @param attr_value value of attribute based on which the subset is formed
	 * @param attr attribute whose value is used to form the subset
	 * @return subset of the table
	 * 
	 */
	
	private List<Tuple> form_subset(List<Tuple> table , String attr_value , 
			String attr) {
		
		List<Tuple> subset = new ArrayList<Tuple>();
		
		for(Tuple t: table) {
			
			/* if a tuple has the value add it to the subset */
			if(t.get_value(attr).equals(attr_value))
			    subset.add(t);
			
		}
		
		return subset;
		
	}
	
	
	
	/**
	 * 
	 * prints the decision value based on the input tuple
	 * @param t tuple for which decision/classification is to be made
	 * 
	 */
	
	public void print_decision(Tuple t) {
		
		find_value(t , root);
		
	}
	
	
	
	/**
	 * 
	 * prints the decision/classification based on the input 
	 * @param t input tuple 
	 * @param N current node of the decision tree 
	 * 
	 */
	
	private void find_value(Tuple t , Node N) {
		
		/* retrieve result if the node is a leaf node */
		if(N.is_leaf() || N.num_children() == 0)
			N.retrieve_result();
		
		String attr = N.splitting_attribute(); // get the attribute on which the node was split
		
		
		if(N.binary_split()) { // if the split was made at a split point 
			
			Double pt = Double.valueOf(t.get_value(attr));
			if(pt <= N.split_pt())
				find_value(t , N.get_left());  // go left 
			else
				find_value(t , N.get_right()); // else go right 
			
		}
		
		else {
			
			String val = t.get_value(attr);
			/* look for child with the matching splitting attribute value */
			for(int i = 0; i < N.num_children() && N.num_children() != 0; i++) { 
				
				if(N.child(i).get_parent_attr_val().equals(val)) {
					find_value(t , N.child(i));
					break;
				}
				
			}
			
		}
		
	}
	
	
	
	/**
	 * 
	 * recursive function which splits a node further and recurses on the children
	 * @param N current node which can be split further
	 * @param table current table 
	 * @param c_attr decision attribute
	 * 
	 */
	
	private void build_tree(Node N , List<Tuple> table , String c_attr) {
		
		if(N.is_leaf()) {  // stop if the node is a leaf node 
			return;
		}
		
		if(attr_list.size() == 1) { // if there are no attributes left, stop

			N.set_as_leaf();
			return;
			
		}
		
		/* get the splitting attribute */
		List<String> splitting_attr = new ArrayList<String>(N.get_splitting_attribute());
		
		String splt_attr = splitting_attr.get(0);	
		//System.out.println(splt_attr);
		
		attr_list.remove(splt_attr); // update the attribute list
		
		if(splitting_attr.size() == 2) { // if the splitting attribute has numeric values
			
			Double splitting_pt = Double.valueOf(splitting_attr.get(1));
			
			List<Tuple> g_split_pt = new ArrayList<Tuple>(form_subset(table ,   // form subsets based on the split point
					splt_attr , splitting_pt , true));
			List<Tuple> l_split_pt = new ArrayList<Tuple>(form_subset(table , splt_attr , 
					splitting_pt , false));
			
			Node left = new Node(l_split_pt , c_attr , attr_list);   // create left child
			Node right = new Node(g_split_pt , c_attr , attr_list);  // create right child
			
			size += 2;  // update tree size
			
			/* update parent and child information */
			N.set_left(left);
			N.set_right(right);
			left.set_parent(N);
			right.set_parent(N);
			
			build_tree(left , l_split_pt , c_attr);  // recursive call on left child
			build_tree(right , g_split_pt , c_attr); // recursive call on right child
			
		}
		
		else {
			
			Set<String> value_types = new HashSet<String>(); // set of values covered so far
			
			for(Tuple t: table) {
				
				String value = t.get_value(splt_attr); // tuple's value for the splitting attribute
				
				if(value_types.contains(value) == false) { 

					value_types.add(value);  // add the value to the list
					List<Tuple> subset = new ArrayList<Tuple>(form_subset(table , value , splt_attr));
					/* create a new child node */
					Node child = new Node(subset , c_attr , attr_list);
					child.set_parent(N);
					child.set_parent_split_attr_val(value);
					
					N.add_child(child); // add child to the list of children 
					
					size++; // increment tree size
					
					if(subset.size() == 0) { // if a subset cannot be formed the child is a leaf node 

						child.set_as_leaf();
						build_tree(child , subset , c_attr); // recursive call
						
					}
					
					else {

						build_tree(child , subset , c_attr);  // recursive call
												
					}
					
				}
				
			}	
			
		}		
		
	}
	
	
	
	/**
	 * 
	 * @return size of the tree
	 * 
	 */
	
	public int size() {
		
		return size;
		
	}


}
