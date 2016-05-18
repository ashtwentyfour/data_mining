package fp_growth;

import java.util.*;


/**
 * 
 * objects of this class represent nodes in the FP-Tree
 * @author Ashwin Menon
 * @verison 1.0
 * @since 2015-07-10
 *
 *
 */



class Node {
	
	Node parent;
	private List<Node> children;
	private int supp_count;
	private String item;
	
	
	
	/**
	 * constructor
	 * @param I itemset
	 * 
	 */
	
	public Node(String I) {
		
		parent = null;    // initialize parent node to null
		children = new LinkedList<Node>();	 // list of child nodes
		supp_count = 1;   // initialize support count
		item = I;    // assign name of itemset
	
	}
	
	
	
	/**
	 * 
	 * @param p parent node
	 * 
	 */
	
	public void set_parent(Node p) {
		
		parent = p;
	}
	
	
	
	/**
	 * 
	 * @param c child node to be added
	 * 
	 */
	
	public void add_child(Node c) {
		
		children.add(c);
		
	}
	
	
	
	/**
	 * 
	 * increase support count by 1
	 * 
	 */
	
	public void increment_count() {
		
		supp_count++;
		
	}
	
	
	
	/**
	 * 
	 * @return support count for the itemset
	 * 
	 */
	
	public int supp_count() {
		
		return supp_count;
	}
	
	
	
	/**
	 * 
	 * @return true is x is child node
	 * 
	 */
	
	int is_child(Node x) {
		
		if (children.size() == 0) // node does not have children 
			return -1;
		
		for(Node n: children) {
			
			if(n.item == x.item)
				return children.indexOf(n);
			
		}
		
		return -1; // node not found among children 
		
	}
	
	
	
	/**
	 * 
	 * @param index index of child node whose support count is to be increased
	 * 
	 */
	
	void increment_child_count(int index) {
		
		(children.get(index)).increment_count();
		
	}
	
	
	
	/**
	 * 
	 * @return item 
	 * 
	 */
	
	public String get_item_name() {
		
		return item;
	
	}
	
	
	
	/**
	 * 
	 * @return number of child nodes
	 * 
	 */
	
	public int num_children() {
		
		return children.size();
		
	}
	
	
	
	/**
	 * 
	 * @param i index of child node to be returned
	 * @return ith child node
	 * 
	 */
	
	public Node child(int i) {
		
		if(i >= 0 && i < children.size())
			return children.get(i);
		else 
		    return null;
		
	}
	
	
	
	/**
	 * 
	 * @return return parent of node
	 * 
	 */
	
    public Node get_parent() {
    	
    	return parent;
    
    }



}
