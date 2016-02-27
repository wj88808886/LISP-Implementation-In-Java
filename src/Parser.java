import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Parser {

	private NewScanner ns;

	public Parser(NewScanner ns) {
		this.ns = ns;
	}

	public void ParseStart() {
		TreeNode root = ParseSexp(new TreeNode(new Token()));
//		System.out.println(printTree(root));
		Evaluation myeval = new Evaluation(root);
		root = myeval.eval(myeval.exp(), myeval.alist(), myeval.dlist());
//		System.out.println(printTree(root));
		clearIsList(root);
		updateIsList(root);
		if (isAllList(root))
			printList(root);
		else
			printNode(root);
		if (!ns.hasNextToken()) {
			System.out.println();
			return;
		} else {
			System.out.println();
			ParseStart();
		}
	}

	public TreeNode ParseSexp(TreeNode root) {
		Token token = ns.getNextToken();
		if (!token.isAtom() && !token.isOpenParenthesis()) {
			if (token.type().equals("Error"))
				System.out.println("ERROR:error in parsing Token \"" + token.value()
						+ "\". It should be a literal atom (e.g., \"XY3Z\"), a numeric atom (e.g., \"3415\").");
			else
				System.out.println("ERROR:error in parsing Token \"" + token.value()
						+ "\". It should be an Atom or OpenParenthesis");
			System.exit(2);
		} else if (token.isAtom()) {
			return new TreeNode(token);
		} else {
			root.setleftChild(ParseSexp(new TreeNode(new Token())));
			Token nexttoken = ns.getNextToken();
			if (nexttoken.isDot()) {
				root.setrightChild(ParseSexp(new TreeNode(new Token())));
				root.isList = root.rightChild.isList;
			} else {
				System.out.println("ERROR:error in parsing Token \"" + token.value() + "\". It should be a Dot");
				System.exit(2);
			}
			Token nexttoken2 = ns.getNextToken();
			if (!nexttoken2.isCloseParenthesis()) {
				System.out.println(
						"ERROR:error in parsing Token \"" + token.value() + "\". It should be a CloseParenthesis");
				System.exit(2);
			}
		}
		return root;
	}

	public void printNode(TreeNode n) {
		// if n is a leaf, print the atom at the leaf (note: NIL will be printed
		// as NIL)
		if (n.isAtom()) {
			System.out.print(n.value());
		}
		// if n is an inner node,
		else {
			System.out.print("(");
			printNode(n.leftChild);
			System.out.print(" . ");
			printNode(n.rightChild);
			System.out.print(")");
		}
		/*
		 * print "(" printNode(n.leftChild) print " . " (note: there are spaces
		 * before/after the dot) printNode(n.rightChild) print ")"
		 */
	}

	public void printList(TreeNode n) {
		if (n.isAtom()) {
			System.out.print(n.value());
		} else {
			System.out.print("(");
			printList(n.leftChild);
			while (!n.rightChild.isAtom()) {
				n = n.rightChild;
				System.out.print(" ");
				printList(n.leftChild);
			}
			System.out.print(")");
		}
		/*
		 * if n is a leaf, print the atom at the leaf (note: NIL will be printed
		 * as NIL) if n is an inner node print "(" printList(n.leftChild) while
		 * (n.rightChild is an inner node) n = n.rightChild print " "
		 * printList(n.leftChild) print ")"
		 */
	}

	public boolean isAllList(TreeNode root) {
		if (root == null)
			return true;
		if (root.isAtom())
			return true;
		if (!root.isList && !root.isAtom())
			return false;
		return isAllList(root.leftChild) && isAllList(root.rightChild);
	}
	
	public void clearIsList(TreeNode root){
		if (root.isAtom()){
			if (root.getToken().isNIL()) root.isList = true;
			else root.isList = false;
			return;
		}
		if (!root.isAtom()) root.isList = false;
		clearIsList(root.leftChild);
		clearIsList(root.rightChild);
	}

	public void updateIsList(TreeNode root){
		if (root.rightChild == null) return;
		if (root.isAtom() && root.value() == "NIL") root.isList = true;
		updateIsList(root.leftChild);
		updateIsList(root.rightChild);
		root.isList = root.rightChild.isList;
	}
	
	public List<List<String>> printTree(TreeNode root) {
		List<List<String>> res = new LinkedList<List<String>>();
		Queue<TreeNode> q = new LinkedList<TreeNode>();
		q.offer(root);
		while (!q.isEmpty()) {
			int b = q.size();
			LinkedList<String> breadth = new LinkedList<String>();

			for (int i = 0; i < b; i++) {
				TreeNode c = q.poll();
				if (c.value().length() == 0)
					breadth.add("inner");
				else
					breadth.add(c.value());
				if (!c.isAtom()) {
					q.offer(c.leftChild);
					q.offer(c.rightChild);
				}
			}
			res.add(breadth);
		}
		return res;
	}

}
