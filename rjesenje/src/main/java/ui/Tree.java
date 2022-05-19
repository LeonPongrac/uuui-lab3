package ui;

import java.util.ArrayList;

public class Tree {
	Tree head;
	ArrayList<Tree> branches;
	String name;
	Type type;
	public Tree() {
		super();
		this.branches = new ArrayList<>();
	}
	
	public Tree(Tree head) {
		super();
		this.branches = new ArrayList<>();
		this.head = head;
	}

	public Tree(Tree head, Type type) {
		super();
		this.branches = new ArrayList<>();
		this.head = head;
		this.type = type;
	}
	
	public Tree(Tree head, String name, Type type) {
		super();
		this.branches = new ArrayList<>();
		this.head = head;
		this.name = name;
		this.type = type;
	}
	public Tree getHead() {
		return head;
	}
	public void setHead(Tree head) {
		this.head = head;
	}
	public ArrayList<Tree> getBranches() {
		return branches;
	}
	public void setBranches(ArrayList<Tree> branches) {
		this.branches = branches;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public void addBranch(Tree tree) {
		this.branches.add(tree);
	}
	
	public void print() {

		StringBuilder string = new StringBuilder();
		int i = 1;
		Tree tree = this;
		inerprint(string, i, tree);
	}
	
	private static void inerprint(StringBuilder string, int i, Tree tree) {
		Type type = tree.type;
		switch (type) {
		case CATEGORY:
			string.append(i+":"+tree.name+"=");
			i++;
			break;
		case OPTION:
			string.append(tree.name+" ");
			break;
		case END:
			string.append(tree.name);
			System.out.println(string.toString());
			return;
		}
		ArrayList<Tree> branches = tree.getBranches();
		for (Tree tree2 : branches) {
			StringBuilder newString = new StringBuilder(string.toString());
			inerprint(newString, i, tree2);
		}
		return;
	}
	
}

