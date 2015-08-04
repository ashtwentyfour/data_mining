import apriori_algo.*;
import java.util.*;

public class Driver {

	public static void main(String[] args) {
		
	   Apriori a = new Apriori();  // new Apriori object 
	   // input file
	   String file = "/Users/ashtwentyfour/Documents/Ashwin/Programming_Projects/Data_Mining"
	   		         + "/apriori_algo/src/test_input.txt";
	   // get frequent itemsets
	   Set<Set<String>> frequent_sets = new HashSet<Set<String>>(a.freq_itemsets(file , 2));
	   System.out.print(frequent_sets);

	}

}
