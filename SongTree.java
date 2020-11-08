import java.io.PrintWriter;

/* This is my custom Binary Search Tree class, it consists of an inner class which is for the node of the tree, the node contains 3 fields
 * 1 for song, 1 for artist and 1 for average number of streams. It has a construtor, a custom toString method and a displayNode method.
 * Then is a SongTree class which is the BST, it only has a find and insert method, and a displayTree method which uses inorder traversal
 * to display the entire tree in ascending alphabetical order, I didn't include a delete method because this project doesn't require any
 * deleting. */

public class SongTree {
	
	//inner node class
	public class Node {
		protected String song;
		protected String artist;
		protected String avgStreams;
		
		protected Node leftChild;
		protected Node rightChild;
		
		//constructor
		public Node(String song, String artist, String avgStreams) {
			this.song = song;
			this.artist = artist;
			this.avgStreams = avgStreams;
		}
		
		//custom toString method
		public String toString() {
			String display = song + " by " + artist + "\nAverage number of streams in the month of September 2020: " + avgStreams + "\n";
			return display;
		}
		
		//displayNode method
		public void displayNode() {System.out.println(toString());}
	}
	
	//field for root node
	protected Node root;
	
	//constructor
	public SongTree() {
		root = null;
	}
	
	//find method
	public Node find(String key) { 
		
		Node current = root; // starts at the root
	
		while(current.song != key) { // while the key is not the current code
			if(key.toLowerCase().charAt(0) < current.song.toLowerCase().charAt(0)) //checks if its smaller
				current = current.leftChild; //if smaller the go left
			else current = current.rightChild; //otherwise go right
			
			if(current == null) //returns null if it didnt find it
				return null;
		}
		return current; //when the key matches the current node, exits the loop and returns the current node
	}
	
	//insert method
	public void insert(String song, String artist, String avgStreams) {
		Node newNode = new Node(song, artist, avgStreams); //instantiate a new node
		
		if(root == null) //if the root is null (meaning tree is empty, set root to newNode
			root = newNode;
		
		else { //else if the root is not null
			Node current = root; //start at the root and go through the tree
			Node parent;
			while(true) { 
				parent = current;
				
				if(song.toLowerCase().charAt(0) < current.song.toLowerCase().charAt(0)) { //checks if its smaller
					current = current.leftChild; //if smaller than the node, then goes to the left of the node
				
					if(current == null) { //if null then then it inserts on the left 
						parent.leftChild = newNode;
						return;
					}
				} //end of if
				
				else { //if the node is bigger (it comes after alphabetically) then it goes to the right
					current = current.rightChild;
					
					if(current == null) { // if null then it inserts on the right
						parent.rightChild = newNode;
						return;
					}
				} // end of inner else 
			} // end of while 
		} // end of outer else 
	} //end of insert method
	
	//this is a displayTree method thats called in the main method but without parameters, the main method can't access the root node
	//this method calls the display tree method below it and sends the root as the parameter
	public void displayTree(PrintWriter output) {displayTree(output, root);}
	
	//this displayTree method is called by the one before it, it takes the root as an argument, then does inorder traversal to recursively
	//traverse the entire tree in ascending alphabetical order, every time it visits a node it calls its toString method to print to a 
	//printwriter
	public void displayTree(PrintWriter output, Node localRoot) {
		if(localRoot != null) {
			displayTree(output, localRoot.leftChild);
			
			output.println(localRoot.toString());
			displayTree(output, localRoot.rightChild);
		} //end of if
	} //end of displayTree method
} //end of SongTree class