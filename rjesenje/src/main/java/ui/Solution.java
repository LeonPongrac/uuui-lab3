package ui;

import java.io.FileNotFoundException;

public class Solution {

	public static void main(String ... args) throws FileNotFoundException {
		/*System.out.println("Ovime kreće Vaš program.");
		for(String arg : args) {
			System.out.printf("Predan argument %s%n", arg);
		}*/
		
		Table table = new Table(args[0]);
		DecisionTree test = new DecisionTree();
		test.buildID3(table);
	}

}
