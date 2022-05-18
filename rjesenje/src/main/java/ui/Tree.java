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
	
	public void print(int i) {

		for (Tree tree : branches) {
			switch (type) {
			case CATEGORY:
				System.out.print(i+":"+name+"=");
				break;
			case OPTION:
				System.out.print(name+" ");
				break;
			case END:
				System.out.print(name);
				System.out.println();
				break;
			default:
				break;
			}
			tree.print(i+1);
		}
	}
	
	public void printl(int i) {
		
		switch (type) {
		case CATEGORY:
			System.out.println(name);
			break;
		case OPTION:
			System.out.println(name);
			break;
		case END:
			System.out.println(name);
			System.out.println();
			break;
		default:
			break;
		}
		for (Tree tree : branches) {			
			tree.printl(i+1);
		}

		for (Tree tree : branches) {
			
			tree.printl(i+1);
		}
	}
	
}

