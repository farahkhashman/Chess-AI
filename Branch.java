
public class Branch {
	boolean isLeaf; 
	Branch left, right;
	
	//leaf
	public Branch() {
		isLeaf = true;
	}
	
	//non-leaf
	public Branch(Branch b1, Branch b2) {
		isLeaf = false;
		left = b1;
		right = b2;
	}

}
