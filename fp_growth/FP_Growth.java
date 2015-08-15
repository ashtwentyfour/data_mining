package fp_growth;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;


/**
 * 
 * @author Ashwin Menon
 * @version 1.0
 * @since 2015-07-10
 *
 */


public class FP_Growth {
	
	
	/**
	 * 
	 * @param file input file of transactions 
	 * @param minsup minimum support value
	 * 
	 */
	
	public void fp_algorithm(String file , int minsup) {
		
		if(minsup <= 0)
			throw new IllegalArgumentException("minsup > 0");
		
		/* create a table of transactions */
	    HashMap<String , Set<String>> table = new HashMap<String , Set<String>>();
	    
	    try {
			table = create_table(file);   // use input file to generate table 
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    find_freq_patterns(table , minsup);  // invoke FP Growth algorithm    
		
	}
	
	
	
	/**
	 * 
	 * @param table input table of transactions 
	 * @param minsup minimum support value 
	 * 
	 */
	
	private void find_freq_patterns(HashMap<String , Set<String>> table , int minsup) {
		
		/* get each single item and its frequency */
	    HashMap<String , Integer> L = find_freq_1_itemsets(table , minsup);
	    
	    List<String> sorted_by_supp_count = new 
	    	ArrayList<String>(sort_by_supp_count(L));
	    
	    HashMap<String , List<String>> new_table = new 
	    		HashMap<String , List<String>>();
	    
	    new_table = sort_in_L_order(table , sorted_by_supp_count);
	    
		FP_Tree tree = new FP_Tree();  // new FP growth tree
        
        tree = construct_fp_tree(new_table);
		
        List<Itemset> freq_items = new ArrayList<Itemset>();
        
        Set<String> alpha = null;
        mine_itemsets(L , tree , sorted_by_supp_count , 
        		minsup , freq_items , alpha);
        
	}
	
	
	
    /**
     * 
     * main function which prints the frequent itemsets 
     * @param tree fp_tree 
     * @param minsup minimum support value 
     * 
     */
	
	private void mine_itemsets(HashMap<String, Integer> L, FP_Tree tree , 
	List<String> sorted_by_supp_count , int minsup , 
	List<Itemset> freq_items , Set<String> alpha) {
		
		if(tree.has_one_path()) {
			
			List<Itemset> all_subsets = new ArrayList<Itemset>();
			all_subsets = tree.all_combos();
			for(Itemset I: all_subsets) {
			   if(alpha != null) {
				  for(String h: alpha)
				     I.add_item(h);
			   }
			   if(already_found(freq_items , I) == false) {
					freq_items.add(I);
					I.print_itemset_info();
			   }  
			}	
			
		}
		
		else {
			
		    int index = sorted_by_supp_count.size()-1;

		    while(true) {
		    if(index < 0)
		    	break;
		       Set<String> Beta = null;
		       if(alpha != null)
		           Beta = new HashSet<String>(alpha);
			   String Item = sorted_by_supp_count.get(index);
			   
               index--;
               
               if(alpha != null && alpha.contains(Item) == false) {
                   
            	   alpha.add(Item);
            	   Itemset beta = new Itemset(alpha , L.get(Item));
            	   if(already_found(freq_items , beta) == false) {
            		   beta.print_itemset_info();
            		   freq_items.add(beta);
            	   }
            	   
               }
               
               else if(alpha == null) {
            	   
                 alpha = new HashSet<String>();
                 alpha.add(Item);
            	   
               }
               
			   List<Itemset> cond_pattern_base = new ArrayList<Itemset>();
			
			   cond_pattern_base = tree.get_patterns(Item , minsup);
			
			   if(cond_pattern_base.size() == 0)
				     continue;
			
			   HashMap<String , Set<String>> table = new HashMap<String , Set<String>>();
			
			   String dummy_trans = "T";
			   int i = 1;
			
			   for(Itemset I: cond_pattern_base) {
				
				 for(int j = 0; j < I.get_frequency(); j++) {
				    table.put((dummy_trans + Integer.toString(i)), I.get_items());
				    i++;
				 }
			   }

			   HashMap<String , Integer> freq_1_itemsets = find_freq_1_itemsets(table , minsup);
			   
			   List<String> sorted_items = new ArrayList<String>
			   (sort_by_supp_count(freq_1_itemsets));
			    
			   HashMap<String , List<String>> new_table = new HashMap<String , List<String>>();
			    new_table = sort_in_L_order(table , sorted_items);
			    
			   FP_Tree cond_tree = new FP_Tree();
		        
		       cond_tree = construct_fp_tree(new_table);
		       
		       
		       mine_itemsets(freq_1_itemsets , cond_tree , sorted_items , 
		    		   minsup , freq_items , alpha);

		       alpha = null;
		       if(Beta != null) 
		           alpha = new HashSet<String>(Beta);
		    
		     }
			
		  }
		 
	}
	
	
	
	/**
	 * 
	 * @param items list of current itemsets
	 * @param J input itemset
	 * @return true of the itemset already exists
	 * 
	 */
	
	private Boolean already_found(List<Itemset> items , Itemset J) {
		
		for(Itemset T: items) {
			
			if(equal(J.get_items() , T.get_items()))  // equate itemsets  
				    return true; 
		}
		
		return false; // set not found 
		
	}
	
	
	
	/**
	 * 
	 * @param x first set
	 * @param y second set
	 * @return true if the two sets are equal
	 * 
	 */
	
	private Boolean equal(Set<String> x , Set<String> y) {
		
		if(x.size() != y.size()) // number of items don't match
			return false;
		
		for(String s: x) {  // check if each item in set x is part of set y
			
			if(y.contains(s) == false)
				return false;
			else
			    y.remove(s);
		}
		
		return true;
		
	}
	

    
	/**
	 * 
	 * add data to the fp_tree
	 * @param table
	 * @return fp_tree
	 * 
	 */
	
	private FP_Tree construct_fp_tree(HashMap<String , List<String>> table) {
		
		FP_Tree tree = new FP_Tree();
		
        for(Entry<String , List<String>> entry:table.entrySet()) {
        	
        	List<Node> branch = new LinkedList<Node>();
        	for(String x: entry.getValue()){
        		Node n = new Node(x);
        		branch.add(n);
        		
        	}
        	
        	tree.insert(branch);
        	
        }
        			
		return tree;
	
	}
	
	
	
	/**
	 * 
	 * @param table table of transactions 
	 * @param sorted_items items sorted by support count 
	 * @return new table with items sorted by support count 
	 * 
	 */
	
	private HashMap<String , List<String>> sort_in_L_order(HashMap<String , 
		Set<String>> table , List<String> sorted_items) {
		
		HashMap<String , List<String>> new_table = new HashMap<String , List<String>>();
		
		for(Entry<String , Set<String>> entry: table.entrySet()) {
			
			if(entry.getKey() == null)
				continue;
			
			List<String> items = new LinkedList<String>();
			for(String x: sorted_items) {
				
				if((entry.getValue()).contains(x) && 
				items.size() <= (entry.getValue()).size())
					items.add(x);
				
			}
			
			new_table.put(entry.getKey(), items);
			
		}
		
	    return new_table;
	
	}
	
	
	
	/**
	 * 
	 * @param input_file file with the list of transactions and transaction IDs
	 * @return a table of transactions 
	 * @throws IOException if the file cannot be found 
	 * 
	 */
	
    private HashMap<String , Set<String >> create_table(String input_file) 
    		throws IOException {
 	   
    	/* table of transactions */
		HashMap<String , Set<String> > table = new 
		HashMap<String , Set<String> >();
	   
	   /* read input file */
	   Scanner filereader = null;
	    try {
	    	filereader = new Scanner(new File(input_file));
	    } catch (FileNotFoundException e) {
	    	e.printStackTrace();
	    }
	    
	    while(filereader.hasNextLine()) {
	    	Scanner linereader = new Scanner(filereader.nextLine());
	    	int index = 0;
	    	String transaction_id = null;
	    	Set<String> items = new HashSet<String>();
	    	while(linereader.hasNext()) {
	    		String s = linereader.next();
	    		if(index == 0)
	    			transaction_id = s;
	    		if(index > 0) {
	    			items.add(s);
	    		}
	    		index++;
	    	}
	    	table.put(transaction_id, items);
	    	linereader.close();
	    }
	    
	    filereader.close();
	    return table;  
	   
    }
    
    
    
    /**
     * 
     * @param table table of transaction data
     * @param min_sup minimum support value 
     * @return single itemset -> frequency mapping 
     * 
     */
    
    private HashMap<String , Integer> find_freq_1_itemsets(HashMap<String , 
     	   Set<String>> table , int min_sup) {
 	 
 	 /* itemset -> frequency mapping */
 	 HashMap<String , Integer> l1_count = new HashMap<String , Integer>();
 	 /* obtain the frequency of each item */
 	 for(Entry<String, Set<String>> entry: table.entrySet()) { 
 		for(String item : entry.getValue()) {
 			 
 		   if(l1_count.containsKey(item))
 		      l1_count.put(item, l1_count.get(item) + 1);
 		   else 
 			  l1_count.put(item, 1);
 	     }
 	   
 	   }
 	   
 	   /* add items with frequency >= min_sup to the set */
 	   List<String> less_than_min_sup = new ArrayList<String>();
 	   for(Entry<String , Integer> entry: l1_count.entrySet()) {
 		   if(entry.getValue() < min_sup)
 			   less_than_min_sup.add(entry.getKey());
 	   }
 	   
 	   for(String x: less_than_min_sup)
 		   l1_count.remove(x);
 	   
 	   return l1_count;
 	   
    }
    
    
    
    /**
     * 
     * @param L itemset -> frequency mapping 
     * @return itemsets sorted by their frequencies
     * 
     */
    
    private List<String> sort_by_supp_count(HashMap<String , Integer> L) {
    	
       	List<Integer> supp_counts = new ArrayList<Integer>();
       	
       	for(Entry<String , Integer> entry: L.entrySet()) 
       		supp_counts.add(entry.getValue());
	
       	Collections.sort(supp_counts , Collections.reverseOrder());
       	
       	List<String> sorted_items = new ArrayList<String>();
       	
       	for(int x: supp_counts) {
       		
       		for(Entry<String , Integer> entry: L.entrySet()) {
       			
       			if(entry.getValue() == x && 
       			sorted_items.contains(entry.getKey()) == false)
       				sorted_items.add(entry.getKey());
       			
       		}
       		
       	}
       	
       	return sorted_items;
    
    }


    
}