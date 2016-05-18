package fp_growth;

import java.io.FileNotFoundException;

public class Driver {

	public static void main(String[] args) throws FileNotFoundException {
		
                if(args.length == 1) {
                   try { 
		     String file = args[0];
		     FP_Growth fp = new FP_Growth();
		     fp.fp_algorithm(file , 2);
                   } catch (FileNotFoundException e) {
                       System.err.println("Input File not found");
                   }
                }
                else {
                   System.out.println("One argument required: Input File");
                }
	}
}
