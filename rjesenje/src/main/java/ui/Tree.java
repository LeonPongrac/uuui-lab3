package ui;

import java.util.ArrayList;

public class Tree {
	Tree head;
	ArrayList<Tree> branches;
	String name;
	Type type;
	public Tree() {
		super();
	}
	public Tree(Tree head, Type type) {
		super();
		this.head = head;
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
	
	
}

