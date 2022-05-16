package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Table {
	private ArrayList<ArrayList<String>> baza;
	
	Table(String filename) throws FileNotFoundException{
		File file = new File(filename);
		Scanner sc = new Scanner(file);
		while(sc.hasNext()) {
			String line = sc.nextLine();
			String[] a = line.split(",");
			ArrayList<String> red = new ArrayList<String>();
			for (String string : a) {
				red.add(string);
			}
			baza.add(red);
		}
	}
	
	void print() {
		for (ArrayList<String> arrayList : baza) {
			boolean first = true;
			for (String string : arrayList) {
				if(!first)
					System.out.print(",");
				System.out.print(string);
				first = false;
			}
			System.out.println();
		}
	}
	
}
