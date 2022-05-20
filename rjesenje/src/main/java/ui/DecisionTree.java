package ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class DecisionTree {
	Tree tree;
	int maxDepth;

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
		Collections.sort(opt);
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
			System.out.print(i + " " + prediction + " " + predint + " ");
			if (predint != -1) {
				int tren = tablica.get(realint).get(predint);
				tren++;
				tablica.get(realint).set(predint, tren);
			}
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
		DecimalFormat df = new DecimalFormat("#0.00000");
		double acc = tocni/(double)table.getTrueSize();
		System.out.println(df.format(acc));
		System.out.println("[CONFUSION_MATRIX]: ");
		for (int i = 0; i < tablica.size(); i++) {
			ArrayList<Integer> red = tablica.get(i);
			boolean first = true;
			for (int j = 0; j < tablica.size(); j++) {
				if(!first)
					System.out.print(" ");
				System.out.print(red.get(j));
				first = false;
			}
			System.out.println();
		}
		
	}
	
	void buildID3Depth(Table table, int depth) {
		ArrayList<String> categorys = table.getCategorys();
		Tree tree = new Tree();
		this.maxDepth = depth;
		this.ID3Depth(table,categorys, tree, 0);
		this.tree = tree;
		System.out.println("[BRANCHES]:");
		this.tree.print();
		
	}
	
	void ID3Depth(Table baza, ArrayList<String> categorys, Tree tree, int depth) {
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
		if(depth == maxDepth) {
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
			this.ID3Depth(nxttable, nxtcat, nxttree, depth+1);
		}
	}
	
}
