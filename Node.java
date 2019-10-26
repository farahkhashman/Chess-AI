import java.util.ArrayList;

public class Node {
	
	private int info;
	private Node parent;
	private ArrayList<Node> children = new ArrayList<Node>();
	
	public Node(int in, Node prnt){
		info = in;
		parent = prnt;
	}
	
	public Node(int in, ArrayList<Node> chldrn){
		info = in;
		children = chldrn;
		
	}
	
	public Node(int in) {
		info = in;
	}
	
	public int GetInfo() {
		return info;
	}
	
	public void AddChild(Node child) {
		children.add(child);
	}
	
	public ArrayList<Node> getChildren(){
		return children;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void UpdateInfo(int score, int team) {
		info = score;
		if(hasParent() && team == 1 && score>parent.GetInfo()) {
			parent.UpdateInfo(score , (team+1)%2);
		}
		if(hasParent() && team == 0 && score<parent.GetInfo()) {
			parent.UpdateInfo(score , (team+1)%2);
		}
	}
	
	public boolean hasParent() {
		if(parent != null)
			return true;
		return false;
	}

}
