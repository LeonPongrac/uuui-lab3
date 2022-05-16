package ui;

import java.io.FileNotFoundException;

public class Solution {

	public static void main(String ... args) throws FileNotFoundException {
		System.out.println("Ovime kreće Vaš program.");
		for(String arg : args) {
			System.out.printf("Predan argument %s%n", arg);
		}
		
		Table test = new Table(args[0]);
		test.print();
		
	}

}
