package fp_growth;

public class Driver {

	public static void main(String[] args) {
		
		String file = "/Users/ashtwentyfour/Documents/Ashwin/Programming_Projects/"
				     + "Data_Mining/apriori_algo/src/test_input.txt";
		FP_Growth fp = new FP_Growth();
		fp.fp_algorithm(file , 2);

	}

}
