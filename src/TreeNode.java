
public class TreeNode {
	
	private Token token;
	public TreeNode leftChild;
	public TreeNode rightChild;
	public boolean isList = false;
	private boolean isAtom = false;
	
	public TreeNode(Token token){
		this.token = token;
		this.isAtom = token.isAtom();
		this.isList = token.value().equals("NIL") || token.value().length() == 0;
	}
	
	public boolean isAtom(){
		return isAtom;
	}
	
	public void setleftChild(TreeNode leftChild){
		this.leftChild = leftChild;
	}
	
	public void setrightChild(TreeNode rightChild){
		this.rightChild = rightChild;
	}
	
	public Token getToken(){
		return token;
	}
	
	public String value(){
		return token.value();
	}
	
	
	
}
