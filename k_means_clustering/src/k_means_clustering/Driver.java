package k_means_clustering;

import java.io.FileNotFoundException;

public class Driver {

	public static void main(String[] args) throws FileNotFoundException {
		
                // check number of input arguments
                if(args.length <= 0) {
                   System.err.println("Enter Input File path");
                   System.exit(0);
                }

		// input file
		String input = args[0];
		
		// create an object of class K_Means
		K_Means Cluster = new K_Means();
		
		// set parameters (file , k , number of iterations)
		Cluster.cluster_by_k_means(input , 2 , 10);     // prints clusters

	}

}
