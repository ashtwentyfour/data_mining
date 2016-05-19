package id3_decision_tree;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Driver {

	public static void main(String[] args) throws FileNotFoundException {
		
                if(args.length <= 0) {
                   System.err.println("Enter Input File path");
                   System.exit(0);
                }

		/* building decision tree using training data set */
		ID3 id = new ID3();
		String file = args[0];
		id.train_data(file , "Play"); // also specifying the decision attribute - Play? Yes or No
		
		/* test case/tuple - Decision ?*/
		List<String> attr = new ArrayList<String>(); // list of attributes
		attr.add("Outlook");
		attr.add("Humid");
		attr.add("Wind");
		List<String> vals = new ArrayList<String>(); // list of corresponding values
		vals.add("Rain");
		vals.add("High");
		vals.add("Weak");
		id.predict_value(attr, vals);

	}

}
