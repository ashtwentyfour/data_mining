package apriori_algo;

import java.util.*;
import java.util.Map.Entry;
import java.io.*;


/**
 * 
 * class which implements the 'Apriori Algorithm' to mine frequent itemsets
 * the input is a table of transaction IDs with their corresponding item sets 
 * (items)
 * 
 * @author Ashwin Menon
 * @version 1.0
 * @since 2015-06-30
 *
 * 
 */

public class Apriori {

	 /**
	  *  
	  * returns the frequent itemsets 
	  * @param input_file file with list of transaction IDs and corresponding items
	  * @param min_sup minimum support value
	  * @return frequent itemsets
	  * 
	 */
	
     public Set<Set<String>> freq_itemsets(String input_file , int min_sup) {
    	 
       if(min_sup == 0) 
    	   throw new IllegalArgumentException("min_sup > 0");
    	   
	   /* input table with transaction -> items mapping */	 
	   HashMap<String , Set<String> > D = new HashMap<String , Set<String> >();	
       /* generate table from the input */
       D = create_table(input_file);
       /* list of frequent itemsets of size 1 */
       Set<String> L = find_freq_1_itemsets(D , min_sup);
       
       /* iteration no. 1 */
       int k = 1;
       /* current list of frequent itemsets */
       Set<Set<String>> Lk = null;
       /* initial candidate itemsets */
       Set<Set<String>> Ck;
       
       
       while(true) {
    	
    	/* for the first iteration initialize Lk */
    	if(k == 1) {
    		Lk = new HashSet<Set<String>>();
    		for(String l : L) {
    			Set<String> temp = new HashSet<String>();
    		    temp.add(l);
    		    Lk.add(temp);
    		}
    	}
    	
    	k++;  // increment k
    	
    	/* get the candidate itemsets of size (k+1) */
    	Ck = apriori_gen(Lk);
    	
    	/* else if Ck = null break -> return Lk as frequent itemsets */
    	if(Ck.size() == 0) 
    		break;
  
    	Lk.clear();
    	
    	int count = 0;  // if count >= min_sup add candidate set to Lk
    	
    	/* loop over the candidate sets */
    	for(Set<String> c: Ck) {
           /* loop over the transaction items */
    	   for(Map.Entry<String, Set<String>> transaction : D.entrySet()) {
    		   /* if the set c is a subset increment count */
    		   if(is_subset(transaction.getValue() , c))
    			   count++;
    	   }
    	   
       	   if (count >= min_sup)
    		   Lk.add(c);
       	   
       	   count = 0;    // reset count
    	
    	}
    				
       }

       return Lk;
          	 	
     }
    
     
    /** 
     * @param table transaction table
     * @param min_sup minimum support count 
     * @return list of frequent 1 itemsets 
    */
     
    private Set<String> find_freq_1_itemsets(HashMap<String , 
    	   Set<String>> table , int min_sup) {
	 
     /* set of frequent-1 itermsets */
	 Set<String> L1 = new HashSet<String>();
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
	   for(Entry<String , Integer> entry: l1_count.entrySet()) {
		   if(entry.getValue() >= min_sup)
			   L1.add(entry.getKey());
	   }
	   
	   return L1;
	   
    }
   
    
    /**
     * @param input_file path to the file which contains the input data
     * @return transaction -> corresponding items mapping
    */
    
    private HashMap<String , Set<String >> create_table(String input_file) {
	   
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
     * This function determines which candidate itemsets of size k must be
     * selected for the next iteration
     * 
     * @param frequent itemsets from the previous iteration of size k-1
     * @return candidate itemsets for the current iteration
    */
	
    
    private Set<Set<String>> apriori_gen(Set<Set<String>> L ) {
	
	    /* candidate itemset set */
	    Set<Set<String>> Ck = null;
	
	    if(L.size() == 0) {
		   return Ck;
	    }
	
	    Ck = new HashSet<Set<String>>();
	
	    /* loop through L and join 2 sets if they share the 1st k-2 items */
	    for(Set<String> j : L) {
	       for(Set<String> k : L) {
		      if(can_be_joined(k,j) == true) { // if the sets can be joined
			      Set<String> u = union(k,j); // form a union
			  
			      /* check whether the join already exists and whether the set does
			         not have infrequent itemsets */
			      if(contains_set(Ck , u) == false && 
			      has_infreq_subset(u , L) == false) {
				       Ck.add(u);		  
		          }
		      }	  
	       }
	    }
	
        return Ck;

    }
	
    
    /**
     * @param a set 1
     * @param b set 2
    */

    private Boolean can_be_joined(Set<String> a , Set<String> b) {
	
    	/* join conditions for k = 1*/
	    if(a.size() == 1 && is_equal(a,b) == false)
		      return true;
	    if(a.size() == 1 && is_equal(a,b) == true)
		      return false;
	
	
	    int limit = a.size()-1;
	    int i = 0;
	    Iterator<String> it_1 = a.iterator();
	    Iterator<String> it_2 = b.iterator();
	   
	    /* check whether the first k-2 items are the same */
	    while(i < limit) {
		   if(it_1.next() != it_2.next())
			  return false;
		   i++;
	    }
	
	    return true;
    }
 
    
    /**
     * @param k set 1
     * @param j set 2
     */

    private Boolean is_equal(Set<String> k , Set<String> j) {
	
	   Set<String> clone = new HashSet<String>(j);
	
	   /* compare the 2 sets */
	   for(String s : k) {
	      if(clone.contains(s)) {
		    clone.remove(s);
	      } else {
		    return false;
	      }
	   }
	
	   return true;
    }
	

    /**
     * union of 2 sets after determining whether they can be joined
     * @param k
     * @param j
     * return union of set 1 and set 2
    */

    private Set<String> union(Set<String> k , Set<String> j) {
   
       /* union set */
	   Set<String> union = new HashSet<String>();
	
	   /* first all the elements of set 1 to the union set */
	   for(String s : k) 
		  union.add(s);
	
	   /* now only add the elements that are have not been added yet */
	   for(String r : j) {
		  if(union.contains(r) == false)
			 union.add(r);
	   }
	
	   return union;

    }

    
    /**
     * @param C set of candidate itemsets 
     * @param s possible subset of C
     * @return
    */

    private Boolean contains_set(Set<Set<String>> C , Set<String> s) {

	   for(Set<String> r : C) {
		  if(is_equal(s , r))
			  return true;	
       }
	 
	   return false;
    }

    
    /** 
     * check whether set 2 is a subset of set 1
     * @param s set 1
     * @param r set 2
    */

    private Boolean is_subset(Set<String> s , Set<String> r) {
	   
	   if(s.size() < r.size())
		  return false;
	
	    for(String t : r) {
		  if(s.contains(t) == false)
			  return false;
	    }
	
	    return true;
	
    }

    
    /**
     * Determine whether a set has infrequent itemsets -> all subsets of the set must be 
     * frequent itemsets of size k and their total number is k!/(k-1)! = k
     * 
     * @param s set 
     * @param L frequent k-1 itemsets 
    */

    private Boolean has_infreq_subset(Set<String> s , Set<Set<String>> L) {
	
	   int count = 0;
	   
	   /* count the number of frequent k-1 itemsets are subsets of set s */
	   for(Set<String> l : L) {
	      if(is_subset(s,l)) {
		      count++;
	      }
	   }
	
	   /* if count == k return false */
	   if(count == s.size()) 
		   return false;

	   return true;
	   
    }
   

}





