package fp_growth;

import java.util.*;


/**
 * 
 * @author Ashwin Menon
 * @version 1.0
 * @since 2015-07-10
 *
 */


class FP_Tree {
	
	private Node root; // root node
	private int size;  // size of tree
	private HashMap<String , List<Node>> links_to_tree_nodes; // item -> nodes in the tree mapping
	
	
	public FP_Tree() {
		
		root = new Node("");  // initialize the root node
		size = 0; // initialze the size of the tree
		links_to_tree_nodes = new HashMap<String , List<Node>>();
		
	}
	
	
	
	/**
	 * 
	 * @return size of tree
	 * 
	 */
	
	public int size() {
		
		return size;
	}
	
	
	
	/**
	 * 
	 * @return true if the root has one child (tree has only one path)
	 * 
	 */
	
	public Boolean has_one_path() {
		
		return root.num_children() == 1;
		
	}
	
	
	
	/**
	 * 
	 * @param branch
	 * 
	 */
	
	public void insert(List<Node> branch) {
		
        Node temp = root;
        Node current = root;
        for(int i = 0; i < branch.size(); i++) {
        	temp = insert(branch.get(i) , current);
            current = temp;
            
        }

	}

	
	
	
    private Node insert(Node x , Node y) {
    
    	int index = y.is_child(x);
    	
    	if(index == -1) {
    		
    	    y.add_child(x);
    	    size++;
    	    x.set_parent(y);
    	    form_new_link(x.get_item_name() , x);
    	    return x;
    	    
    	}
    	
    	else {
    		
    	    y.increment_child_count(index);
    	    return y.child(index);
    		
    	}
    	
    	
    }
    
    
    /**
     * 
     * @param item the item whose links in the fp_tree are being modified
     * @param x node to be added to the link
     * 
     */
    
    private void form_new_link(String item , Node x) {
    	
    	if(links_to_tree_nodes.containsKey(item) == false) { // if the item does not have links to nodes
    		
    		List<Node> links = new LinkedList<Node>();
    		links.add(x);
    		links_to_tree_nodes.put(item , links);
    		
    	}
    	
    	else 
    	   links_to_tree_nodes.get(item).add(x); // add node to existing list 
    	
    }
    
    
    
    /**
     * 
     * display tree
     * 
     */
    
    public void print_tree() {
    	
    	System.out.println("fp_tree:" + "\n");
    	List<Node> current = new LinkedList<Node>();
    	
    	/* BFS traversal */
    	current.add(root);
    	while(current.size() != 0) {
    		List<Node> next = new LinkedList<Node>();
    		for(Node x: current) {
    			System.out.print(x.get_item_name() + " ");
    		    for(int i = 0; i < x.num_children(); i++)
    		    	 next.add(x.child(i));
    		
    		}
    		System.out.println();
    		current = next;
    	
    	}
    	
    }
    
    
    
    public List<Itemset> get_patterns(String item , int minsup) {
    	
    	List<Itemset> patterns = new ArrayList<Itemset>();
    	
    	for(Node x: links_to_tree_nodes.get(item)) {
    		
    		Itemset I = new Itemset();
    		
    		I.set_frequency(x.supp_count());
    		
    		Node temp = x;
    		temp = temp.parent;
    		if(temp.get_item_name() == "")
    			continue;
    		
    		while(temp.get_item_name() != "") {
    			
    			I.add_item(temp.get_item_name());
    			temp = temp.parent;
    			
    		}
    		
    		patterns.add(I);
    		
    	}
    	
    	return patterns;
    	
    }
    
    
    
    public List<Itemset> all_combos() {
    	
    	if(root.num_children() > 1) {
    		
    		//Exception
    		return null;
    		
    	}
    	
    	Set<String> branch_items = new HashSet<String>();
    	
    	Node child = root.child(0);
    	int min_sup = 0;
    	while(child != null) {
    		
    		branch_items.add(child.get_item_name());
    		min_sup = child.supp_count();
    		child = child.child(0);
    		
    	}
    	
    	List<Itemset> all_subs = new ArrayList<Itemset>();
    	
    	all_subs = all_combos(branch_items , min_sup);
    	
    	return all_subs;
    	 	
    
    }
    
    
    
    /**
     * 
     * @param s set whose subsets are to be found
     * @param minsup minimum support value
     * @return set of all combinations of values from s
     * 
     */
    
	private List<Itemset> all_combos(Set<String> s , int minsup) {
		
        List<Itemset> all_subs = new ArrayList<Itemset>();
		
        int pow_set_size = (int) Math.pow(2, s.size());
        
        int j;
		for(int c = 0; c < pow_set_size; c++) {
			
			Set<String> subset = new HashSet<String>();
			j = 0;
			for(String x: s) {
				
				if((c & (1<<j)) == 0) 
					subset.add(x);
					
			    j++;
			}
			
			if(subset.size() > 0)
			    all_subs.add(new Itemset(subset , minsup));
			
		}
		
		return all_subs;
        
	}


}
