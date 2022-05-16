package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Table {
	private ArrayList<ArrayList<String>> baza;
	
	Table(String filename) throws FileNotFoundException {
		File file = new File(filename);
		ArrayList<ArrayList<String>> baza = new ArrayList<>();
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
		sc.close();
		this.baza = baza;
	}
	
	Table(ArrayList<ArrayList<String>> baza){
		super();
		this.baza = baza;
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
	
	public ArrayList<String> getCategorys(){
		return baza.get(0);
	}
	
	public ArrayList<String> getOptions(String category){
		ArrayList<String> categorys = this.getCategorys();
		int y = categorys.indexOf(category);
		ArrayList<String> options = new ArrayList<>();
		for (int i = 1; i < baza.size(); i++) {
			String option = baza.get(i).get(y);
			if(!options.contains(option)) {
				options.add(option);
			}
		}		
		return options;
	}
	
	public Table subTable(String category, String option) {
		ArrayList<ArrayList<String>> novaBaza = new ArrayList<>();
		ArrayList<String> red = this.getCategorys();
		int y = red.indexOf(category);
		novaBaza.add(red);
		for (int i = 1; i < baza.size(); i++) {
			String opt = baza.get(i).get(y);
			if(opt.contains(option)) {
				red = baza.get(i);
				novaBaza.add(red);
			}
		}
		return new Table(novaBaza);
	}
	
	public static double log2(double N)
    {
        double result = (Math.log(N) / Math.log(2));
        return result;
    }
	
	public double finalEntropy() {
		ArrayList<String> categorys = this.getCategorys();
		ArrayList<String> options = this.getOptions(categorys.get(categorys.size()-1));
		HashMap<String, Integer> kazo = new HashMap<>();
		for (String string : options) {
			kazo.put(string, 0);
		}
		for (int i = 1; i < baza.size(); i++) {
			String opt = baza.get(i).get(categorys.size()-1);
			kazo.replace(opt, kazo.get(opt)+1);
		}
		int n = baza.size()-1;
		double entropija = 0;
		for (String string : options) {
			double temp = (double)(kazo.get(string)/n)*log2(n);
			entropija = entropija - temp;
		}
		
		return entropija;
	}
	
	public double entropija(String category, String option) {
		Table temp = this.subTable(category, option);
		double entropija = temp.finalEntropy();
		return entropija;
	}
	
}
