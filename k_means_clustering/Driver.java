package k_means_clustering;

public class Driver {

	public static void main(String[] args) {
		
		// input file
		String input = "/Users/ashtwentyfour/Documents/Ashwin/Programming_Projects/Data_Mining/k_means_clustering"
				      + "/src/k_means_clustering/test_cases/test_input.txt";
		
		// create an object of class K_Means
		K_Means Cluster = new K_Means();
		
		// set parameters (file , k , number of iterations)
		Cluster.cluster_by_k_means(input , 2 , 10);     // prints clusters

	}

}
