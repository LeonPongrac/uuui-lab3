package ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class DecisionTree {
	Tree tree;

	public DecisionTree() {
		super();
	}
	
	void buildID3(Table table) {
		ArrayList<String> categorys = table.getCategorys();
		Tree tree = new Tree();
		this.ID3(table,categorys, tree);
		this.tree = tree;
		System.out.println("[BRANCHES]:");
		this.tree.print();
		
	}
	
	void ID3(Table baza, ArrayList<String> categorys, Tree tree) {
		//baza.print();
		//System.out.println();
		double entropy = baza.finalEntropy();
		if(entropy == 0) {
			//System.out.println("Sve DA/NE");
			tree.setType(Type.END);
			tree.setName(baza.getFinal());
			/*System.out.println("finish");
			System.out.println();*/
			return;
		}
		if(categorys.size()==1) {
			//System.out.println("Nema kategorija");
			tree.setType(Type.END);
			tree.setName(baza.getFinal());
			return;
		}
		String best = null;
		double bestinfo = 0;
		for (int i = 0; i < categorys.size()-1; i++) {
			double info = baza.infoGain(categorys.get(i));
			System.out.print("IG("+categorys.get(i)+")="+info+" ");
			if(info > bestinfo) {
				bestinfo = info;
				best = categorys.get(i);
			}
		}
		System.out.println();
		tree.setName(best);
		tree.setType(Type.CATEGORY);
		ArrayList<String> options = baza.getOptions(best);
		for (String string : options) {
			Tree opttree = new Tree(tree, string ,Type.OPTION);
			tree.addBranch(opttree);
			Tree nxttree = new Tree(opttree);
			opttree.addBranch(nxttree);
			Table nxttable = baza.subTable(best, string);
			ArrayList<String> nxtcat = (ArrayList<String>) categorys.clone();
			nxtcat.remove(best);
			//nxtcat.stream().forEach(e -> System.out.println(e));
			this.ID3(nxttable, nxtcat, nxttree);
		}
	}
	
	public void predict(Table table) {
		double tocni = 0;
		System.out.print("[PREDICTIONS]:");
		ArrayList<String> cat = table.getCategorys();
		ArrayList<String> opt = table.getOptions(cat.get(cat.size()-1));
		ArrayList<ArrayList<Integer>> tablica = new ArrayList<ArrayList<Integer>>(opt.size());
		for (int i = 0; i < opt.size(); i++) {
			ArrayList<Integer> red = new ArrayList<Integer>();
			for (int j = 0; j < opt.size(); j++) {
				red.add(0);
			}
			tablica.add(red);
		}
		//System.out.println(tablica.size());
		for (int i = 1; i < table.getSize(); i++) {
			ArrayList<String> red = table.getRed(i);
			String prediction = tree.predict(red,cat);
			String real = red.get(red.size()-1);
			int predint = opt.indexOf(prediction);
			int realint = opt.indexOf(real);
			int tren = tablica.get(realint).get(predint);
			tren++;
			tablica.get(realint).set(predint, tren);
			//System.out.print(i + " " + prediction + " " + real + " ");
			if(real.equals(prediction)) {
				tocni++;
				//System.out.print("1");
			}
			//System.out.println();
			System.out.print(" " + prediction);
		}
		System.out.println();
		//table.print();
		//System.out.println(tocni + " " + table.getSize());
		System.out.print("[ACCURACY]: ");
		double acc = tocni/(double)table.getTrueSize();
		System.out.println(round(acc,5));
		System.out.println("[CONFUSION_MATRIX]: ");
		for (int i = tablica.size()-1; i >= 0; i--) {
			ArrayList<Integer> red = tablica.get(i);
			boolean first = true;
			for (int j = red.size()-1; j >= 0; j--) {
				if(!first)
					System.out.print(" ");
				System.out.print(red.get(j));
				first = false;
			}
			System.out.println();
		}
		
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
}
