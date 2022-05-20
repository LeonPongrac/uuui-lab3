package ui;

import java.io.FileNotFoundException;

public class Solution {

	public static void main(String ... args) throws FileNotFoundException {
		/*System.out.println("Ovime kreće Vaš program.");
		for(String arg : args) {
			System.out.printf("Predan argument %s%n", arg);
		}*/
		System.out.println(args.length);
		if (args.length == 2) {
			Table table = new Table(args[0]);
			Table testTable = new Table(args[1]);
			DecisionTree test = new DecisionTree();
			test.buildID3(table);
			test.predict(testTable);
		}
		if (args.length == 3) {
			Table table = new Table(args[0]);
			Table testTable = new Table(args[1]);
			int depth = Integer.parseInt(args[2]);
			DecisionTree test = new DecisionTree();
			test.buildID3Depth(table, depth);
			test.predict(testTable);
		}
	}

}
