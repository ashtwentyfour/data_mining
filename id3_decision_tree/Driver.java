package id3_decision_tree;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Driver {

	public static void main(String[] args) throws FileNotFoundException {
		
		/* building decision tree using training data set */
		ID3 id = new ID3();
		String file = "/Users/ashtwentyfour/Documents/Ashwin/Programming_Projects"
				+ "/Data_Mining/decision_tree_id3/src/id3_decision_tree/test_cases/test_input.txt";
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
