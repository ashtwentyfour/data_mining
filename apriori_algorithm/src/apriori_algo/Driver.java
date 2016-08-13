package apriori_algo;

import apriori_algo.*;
import java.util.*;
import java.io.FileNotFoundException;

public class Driver {

	public static void main(String[] args) throws FileNotFoundException {

           if(args.length <= 0) {
              System.err.println("Enter Input File path as argument");
              System.exit(0);
           }
		
	   Apriori a = new Apriori();  // new Apriori object 
	   // input file
	   String file = args[0];
	   // get frequent itemsets
	   Set<Set<String>> frequent_sets = new HashSet<Set<String>>(a.freq_itemsets(file , 2));
	   System.out.print(frequent_sets);
	   System.out.println("");

	}

}
