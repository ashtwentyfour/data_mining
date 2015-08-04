package k_means_clustering;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map.Entry;


/**
 * 
 * the objects of this class are used to read the input data table
 * and print out the tuples with the cluster/group they belong to 
 * 
 * @author Ashwin Menon
 * @version 1.0
 * @since 2015-08-1
 *
 *
 */


public class K_Means {
	
	
	
	/**
	 * 
	 * @param file input file (table of data)
	 * @param k number of clusters required 
	 * @param num_iter maximum number of k-means iterations
	 * 
	 */
	
	public void cluster_by_k_means(String file , int k , int num_iter) {
		
		
		/* create table from input (list of tuples) */
        List<Tuple> table = new ArrayList<Tuple>(make_table(file));
        
        /*  input specs */
        if(k >= table.size() || k <= 1)
        	throw new IllegalArgumentException("k < number of tuples and k > 1");
        
        if(num_iter < 10 || num_iter % 10 != 0)
        	throw new IllegalArgumentException("number of iterations must be divisible by 10");
		
        /* first k-centers must be chosen randomly among the existing tuples */
        List<Integer> centers = new ArrayList<Integer>();
        
        for(int i = 0; i < table.size(); i++) {
        	
        	centers.add(i);
       
        }
        
        /* shuffle the indices */
        Collections.shuffle(centers);
        
        /* choose the first k tuples from the index array above */
        List<Tuple> k_centers = new ArrayList<Tuple>();
        
        for(int j = 0; j < k; j++) 
        	k_centers.add(table.get(centers.get(j)));
        
        /* prints the tuples and the clusters they belong to */
        find_clusters(table , k_centers , num_iter); 
        
		
	}
	
	
	
	/**
	 * 
	 * @param table input table
	 * @param k_centers list of initial k-centers 
	 * @param num_iter max. number of iterations before termination
	 * 
	 */
	
	private void find_clusters(List<Tuple> table , List<Tuple> k_centers , int num_iter) {
		
		/* current mapping of the tuple -> center of cluster */
		HashMap<Tuple , Tuple> center_mapping = new HashMap<Tuple , Tuple>();
		
		/* for each tuple assign a center based on lowest distance value */
		for(Tuple t: table) {
			
			Double dist = Double.POSITIVE_INFINITY;
			Double n = 0.0;    // initialize the 2-norm value
			for(Tuple k: k_centers) {
				
				n = norm(t , k);  // compute norm 
				if(n < dist) {    // if a smaller value if found 
					
					dist = n;
					t.set_center(k);   // assign new center 
					center_mapping.put(t, k);  // update center mapping info.
				
				}
			
			}
			
		}


        int iter = 0;  // initialize number of iterations 
        
		while(iter < num_iter) {  // begin iterations 
	        
			/* get new centers by computing the mean values */
			List<Tuple> new_centers = new ArrayList<Tuple>(get_centers(table));
			
			/* now assign new centers */
			for(Tuple r: table) {
				
				Double d = Double.POSITIVE_INFINITY;
				Double norm = 0.0;   // initialize the norm value
				for(Tuple c: new_centers) {
					
					norm = norm(r , c);  // compute distance 
					if(norm < d) {  // for a closer center 
					
						d = norm;   // update smallest distance 
						r.set_center(c); // update center of tuple 
					
					}
					
				}
				
			}
			
			iter++;  // increment number of iterations 
			
			/* count number of tuples whose centers have not changed */
			int stabilized = 0;
			
			for(Tuple y: table) {
				
				/* if the center has changed to a new cluster */
				if(norm(center_mapping.get(y) , y.get_center()) > 0.0001) 
					center_mapping.put(y , y.get_center());
				else
					stabilized++;
				
			}

			
			if(stabilized == table.size())  // all the tuples do not have new centers 
			    break;
			
		}

		/* print out the tuples and their respective cluster centers */
		System.out.println("Clusters:" + "\n");
		for(Tuple k: table)  {
			
			k.print_tuple();
			System.out.println("Center: ");
			k.print_numerical_values();
			
		}
		
	}
	
	
	
	/**
	 * 
	 * @param table table of data tuples 
	 * @return new list of centers (after computing the mean values)
	 * 
	 */
	
	private List<Tuple> get_centers(List<Tuple> table) {
		
		/* mapping of center -> tuples with this center */
		HashMap<Tuple , List<Tuple>> centers = new HashMap<Tuple , List<Tuple>>();
		
		/* create a center -> list of tuples with this center mapping */
		for(Tuple t: table) {
			
			if(centers.containsKey(t.get_center()) == false) {
				List<Tuple> temp = new ArrayList<Tuple>();
				temp.add(t);
				centers.put(t.get_center(), temp); // add tuple to the list 
				
			}
			
			else 
				centers.get(t.get_center()).add(t);	// add tuple to the list 

		}
		
		
		List<Tuple> new_centers = new ArrayList<Tuple>(); // list of new centers 
		
		for(Entry<Tuple , List<Tuple>> entry:centers.entrySet()) {
			
			Tuple c = compute_center(entry.getValue()); // compute new center by averaging
			new_centers.add(c);
			
		}
		
		return new_centers;  // return the list of new centers 
		
	}
	
	
	
	/**
	 * 
	 * @param t list of tuples for belonging to the same cluster
	 * @return new center 
	 * 
	 */
	
	private Tuple compute_center(List<Tuple> t) {
		
		String dummy = "dummy";  // dummy string value for non-numeric attributes 
		
		// list of numerical attributes
		List<String> attributes = t.get(0).get_numeric_attributes();
		
		// create attribute -> mean value mapping 
		HashMap<String , Double> means = new HashMap<String , Double>();
		
		/* for each value compute the mean over all the tuples 
		 * 
		 * mean = (x1 + x2 ......)/n
		 * 
		 * */
		
		for(String attr: attributes) {
			
			Double mean = 0.0; // initialize mean
			for(Tuple r: t) 
				mean += Double.parseDouble(r.get_value(attr)); // update sum 
				
			mean = mean/t.size(); // compute mean 
			
			means.put(attr , mean);  // update mean value for the current attribute
			
		}
		
		// create a value list for the new center 
		List<String> vals = new ArrayList<String>();
		
		for(String a: t.get(0).get_attr_list()) {
			
			// if the attribute is not numerical
			if(is_numeric(t.get(0).get_value(a)) == false) 
				vals.add(dummy);
				
			else 
				vals.add(String.valueOf((means.get(a))));
			
		}
		
		// create new center object 
		Tuple c = new Tuple(t.get(0).get_attr_list() , vals);
		
		// return center 
		return c;
			
	
	}
	
	
	
	/**
	 * 
	 * @param t1 tuple 1
	 * @param t2 tuple 2
	 * @return 2-norm between the two tuples (of their difference)
	 * 
	 */
	
	private Double norm(Tuple t1 , Tuple t2) {
		
		/* list of numerical attributes */
		List<String> attributes = new ArrayList<String>(t1.get_numeric_attributes());
		
		/* vector of differences in values between the two tuples
		 * 
		 * vector = [(x1 - x2) , (y1 - y2), .........]
		 * 
		 * */
		
		List<Double> diff_vector = new ArrayList<Double>();
		
		// compute differences 
		Double diff = 0.0;
		
		for(String val: attributes) {
			
			diff = Math.abs(Double.parseDouble(t1.get_value(val)) - 
				   Double.parseDouble(t2.get_value(val)));
			diff_vector.add(diff);
			
		}
		
		// compute norm of the difference vector 
		Double sum = 0.0;
		
		for(Double x : diff_vector) {
			
			sum += x*x;
			
		}
		
		return Math.abs(Math.sqrt(sum));  // return distance
		
	
	}
	
	
	
	/**
	 * 
	 * @param file input file path/name
	 * @return list of input tuple objects 
	 * 
	 */
	
	private List<Tuple> make_table(String file) {
		
	    Scanner filereader = null;
		try {
		     filereader = new Scanner(new File(file));  // if the file does not exist
		} catch (FileNotFoundException e) {
		     e.printStackTrace();
	    }
		
		List<String> attributes = new ArrayList<String>(); // attribute list
		List<String> values = new ArrayList<String>(); // list of values 
		List<Tuple> table = new ArrayList<Tuple>(); // table of tuples 
		int index = 0; // line count 
		
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
		    if(index == 0)      // remove the first attribute (ID No. No. etc.)     
		    	attributes.remove(0);
		    index++;
		    if(values.size() > 0) {
		       values.remove(0);  // remove the first value (tuple ID, ID etc.)
		       List<String> tuple_values = new ArrayList<String>(values); // create new tuple 
		       Tuple t = new Tuple(attributes , tuple_values); 
		       values.clear();
		       table.add(t);
		    }
		    linereader.close();
		    
		}
		
		filereader.close();
		
		return table; // return new table 
		
	}
	
	
	
	/**
	 * 
	 * @param val value to be tested 
	 * @return true if the value is numeric, otherwise false 
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


}
