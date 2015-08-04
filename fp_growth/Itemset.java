package fp_growth;

import java.util.*;


/**
 * 
 * @author Ashwin Menon
 * @version 1.0
 * @since 2015-07-10
 *
 */


class Itemset {
	
	private Set<String> itemset; // set of items
	private int freq; // frequency of itemset
	
	
	public Itemset() {
		
		freq = 1;  // minimum/default frequency = 1
		itemset = new HashSet<String>();  // set of items 
		
	}
	
	
	
	/**
	 * 
	 * @param items
	 * @param count
	 * 
	 */
	
	public Itemset(Set<String> items , int count) {
		
		freq = count;
		itemset = items;
		
	}
	
	
	
	void set_frequency(int f) {
		
		freq = f;
	
	}
	
	
	
	void add_item(String i) {
		
		itemset.add(i); // add an item to the set
		
	}
	
	
	
	int get_frequency() {
		
		return freq;  // return frequency 
		
	}
	
	
	
	void print_itemset_info() {
		
		System.out.println(itemset + " " + freq); // print data
		
	}
	
	
	
	Set<String> get_items() {
		
		return itemset;
		
	}
	
	

}
