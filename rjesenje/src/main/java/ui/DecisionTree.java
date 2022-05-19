package ui;

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
		baza.print();
		System.out.println();
		double entropy = baza.finalEntropy();
		if(entropy == 0) {
			//System.out.println("Sve DA/NE");
			tree.setType(Type.END);
			tree.setName(baza.getFinal());
			System.out.println("finish");
			System.out.println();
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
	
}
