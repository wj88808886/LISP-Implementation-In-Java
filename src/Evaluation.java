import java.util.LinkedList;
import java.util.List;

/**
 * 
 */

/**
 * @author Jian
 *
 */
public class Evaluation {

	private TreeNode exp;
	private List<Parameters> a;
	private List<Parameters> d;

	public Evaluation(TreeNode exp) {
		this.exp = exp;
		this.a = new LinkedList<Parameters>();
		this.d = new LinkedList<Parameters>();
	}

	public List<Parameters> alist() {
		return a;
	}

	public List<Parameters> dlist() {
		return d;
	}

	public TreeNode exp() {
		return exp;
	}

	public TreeNode eval(TreeNode exp, List<Parameters> a, List<Parameters> d) {
		if (!exp.isAtom()) {
			if (car(exp).getToken().isQuote()) {
				if (args(cdr(exp)) != 1) {
					System.out.println("ERROR:QUOTE must take a list with One argument");
					System.exit(1);
				}
				return car(cdr(exp));
			} else if (car(exp).getToken().isCond()) {
				if (!checkCond(cdr(exp))){
					System.out.println("ERROR:One of condition arguments number is not equal to 2 or Not a NIL at the end");
					System.exit(2);
				}
				return evcon(cdr(exp), a, d);
			} else {
				return apply(car(exp), evlist(cdr(exp), a, d), a, d);
			}
		} else{
			if (!exp.getToken().canEval()){
				System.out.println("ERROR:ERROR:unbounded variable " + exp.value());
				System.exit(1);
			}
			return exp;
		}
			
	}

	public TreeNode evcon(TreeNode x, List<Parameters> a, List<Parameters> d) {
		if (x.getToken().isNIL()) {
			System.out.println("ERROR:No condition argument or No condition is satisfiable.");
			System.exit(2);
		}
		if (!eval(car(car(x)), a, d).getToken().isNIL() && !eval(car(car(x)), a, d).getToken().isT()){
			System.out.println("ERROR:the first element of arguments in condition should be T or NIL");
			System.exit(2);
		}
		if (eval(car(car(x)), a, d).getToken().isT()) {
			return eval(car(cdr(car(x))), a, d);
		} else {
			return evcon(cdr(x), a, d);
		}
	}

	public boolean checkCond(TreeNode x){
		if (x.getToken().isNIL()) return true;
		else if (x.isAtom()) return false;
		return (args(car(x))==2) && checkCond(cdr(x));
	}
	
	public TreeNode evlist(TreeNode x, List<Parameters> a, List<Parameters> d) {
		if (x.getToken().isNIL()) {
			return x;
		} else {
			return cons(eval(car(x), a, d), evlist(cdr(x), a, d));
		}
	}

	public TreeNode apply(TreeNode f, TreeNode x, List<Parameters> a, List<Parameters> d) {
		if (!f.isAtom()) {
			System.out.println("ERROR:f should be an atom");
			System.exit(2);
		}
		if (f.getToken().isCAR()) {
			if (args(x) != 1 || car(x).isAtom()) {
				System.out.println("ERROR:CAR takes one parameter, and it should be a list");
				System.exit(1);
			}
			return car(car(x));
		} else if (f.getToken().isCDR()) {
			if (args(x) != 1 || car(x).isAtom()) {
				System.out.println("ERROR:CDR takes one parameter, and it should be a list");
				System.exit(1);
			}
			return cdr(car(x));
		} else if (f.getToken().isCONS()) {
			if (args(x) != 2) {
				System.out.println("ERROR:ERROR:CONS takes two parameter");
				System.exit(1);
			}
			return cons(car(x), car(cdr(x)));
		} else if (f.getToken().isATOM()) {
			if (args(x) != 1) {
				System.out.println("ERROR:ATOM takes one parameter");
				System.exit(1);
			}
			return atom(car(x));
		} else if (f.getToken().isEQ()) {
			if (args(x) != 2) {
				System.out.println("ERROR:EQ takes two parameter");
				System.exit(1);
			}
			if (!car(x).isAtom() || !car(cdr(x)).isAtom()){
				System.out.println("ERROR:EQ takes Atoms not list");
				System.exit(1);
			}
			return eq(car(x), car(cdr(x)));
		} else if (f.getToken().isNULL()) {
			if (args(x) != 1) {
				System.out.println("ERROR:NULL takes one parameter");
				System.exit(1);
			}
			return isnull(car(x));
		} else if (f.getToken().isINT()) {
			if (args(x) != 1) {
				System.out.println("ERROR:INT takes one parameter");
				System.exit(1);
			}
			if (!car(x).isAtom()){
				System.out.println("ERROR:INT takes a Atom not a list");
				System.exit(1);
			}
			return isint(car(x));
		} else if (f.getToken().isPLUS()) {
			if (args(x) != 2) {
				System.out.println("ERROR:PLUS takes two parameter");
				System.exit(1);
			}
			return plus(car(x), car(cdr(x)));
		} else if (f.getToken().isMINUS()) {
			if (args(x) != 2) {
				System.out.println("ERROR:MINUS takes two parameter");
				System.exit(1);
			}
			return minus(car(x), car(cdr(x)));
		} else if (f.getToken().isTIMES()) {
			if (args(x) != 2) {
				System.out.println("ERROR:TIMES takes two parameter");
				System.exit(1);
			}
			return times(car(x), car(cdr(x)));
		} else if (f.getToken().isQUOTIENT()) {
			if (args(x) != 2) {
				System.out.println("ERROR:QUOTIENT takes two parameter");
				System.exit(1);
			}
			return quotient(car(x), car(cdr(x)));
		} else if (f.getToken().isREMAINDER()) {
			if (args(x) != 2) {
				System.out.println("ERROR:REMAINDER takes two parameter");
				System.exit(1);
			}
			return reminder(car(x), car(cdr(x)));
		} else if (f.getToken().isLESS()) {
			if (args(x) != 2) {
				System.out.println("ERROR:LESS takes two parameter");
				System.exit(1);
			}
			return isless(car(x), car(cdr(x)));
		} else if (f.getToken().isGREATER()) {
			if (args(x) != 2) {
				System.out.println("ERROR:GREATER takes two parameter");
				System.exit(1);
			}
			return isgreater(car(x), car(cdr(x)));
		} else {
			System.out.println("ERROR:" + f.value() + " is not a valid function");
			System.exit(2);
			return null;
		}
	}

	private TreeNode car(TreeNode exp) {
		return exp.leftChild;
	}

	private TreeNode cdr(TreeNode exp) {
		return exp.rightChild;
	}

	private TreeNode cons(TreeNode t1, TreeNode t2) {
		TreeNode temp = new TreeNode(new Token());
		temp.isList = t2.isList;
		temp.setleftChild(t1);
		temp.setrightChild(t2);
		return temp;
	}

	private TreeNode atom(TreeNode exp) {
		return exp.isAtom() ? new TreeNode(new Token("T")) : new TreeNode(new Token("NIL"));
	}

	private TreeNode eq(TreeNode t1, TreeNode t2) {
		return t1.value().equals(t2.value()) ? new TreeNode(new Token("T"))
				: new TreeNode(new Token("NIL"));
	}

	private TreeNode isnull(TreeNode exp) {
		return exp.getToken().isNIL() ? new TreeNode(new Token("T"))
				: new TreeNode(new Token("NIL"));
	}

	private TreeNode isint(TreeNode exp) {
		return exp.getToken().isNum() ? new TreeNode(new Token("T"))
				: new TreeNode(new Token("NIL"));
	}

	private TreeNode plus(TreeNode t1, TreeNode t2) {
		if (!t1.getToken().isNum() || !t2.getToken().isNum()) {
			System.out.println("ERROR:plus error, at least one of parameters are not a number");
			System.exit(1);
		}
		return new TreeNode(
				new Token(Integer.parseInt(t1.getToken().value()) + Integer.parseInt(t2.getToken().value()) + "",
						t1.getToken().type()));
	}

	private TreeNode minus(TreeNode t1, TreeNode t2) {
		if (!t1.getToken().isNum() || !t2.getToken().isNum()) {
			System.out.println("ERROR:minus error, at least one of parameters are not a number");
			System.exit(1);
		}
		return new TreeNode(
				new Token(Integer.parseInt(t1.getToken().value()) - Integer.parseInt(t2.getToken().value()) + "",
						t1.getToken().type()));
	}

	private TreeNode times(TreeNode t1, TreeNode t2) {
		if (!t1.getToken().isNum() || !t2.getToken().isNum()) {
			System.out.println("ERROR:times error, at least one of parameters are not a number");
			System.exit(1);
		}
		return new TreeNode(
				new Token(Integer.parseInt(t1.getToken().value()) * Integer.parseInt(t2.getToken().value()) + "",
						t1.getToken().type()));
	}

	private TreeNode quotient(TreeNode t1, TreeNode t2) {
		if (!t1.getToken().isNum() || !t2.getToken().isNum()) {
			System.out.println("ERROR:quotient error, at least one of parameters are not a number");
			System.exit(1);
		}
		return new TreeNode(
				new Token(Integer.parseInt(t1.getToken().value()) / Integer.parseInt(t2.getToken().value()) + "",
						t1.getToken().type()));
	}

	private TreeNode reminder(TreeNode t1, TreeNode t2) {
		if (!t1.getToken().isNum() || !t2.getToken().isNum()) {
			System.out.println("ERROR:quotient error, at least one of parameters are not a number");
			System.exit(1);
		}
		return new TreeNode(
				new Token(Integer.parseInt(t1.getToken().value()) % Integer.parseInt(t2.getToken().value()) + "",
						t1.getToken().type()));
	}

	private TreeNode isless(TreeNode t1, TreeNode t2) {
		if (!t1.getToken().isNum() || !t2.getToken().isNum()) {
			System.out.println("ERROR:isless error, at least one of parameters are not a number");
			System.exit(1);
		}
		return Integer.parseInt(t1.getToken().value()) < Integer.parseInt(t2.getToken().value())
				? new TreeNode(new Token("T")) : new TreeNode(new Token("NIL"));
	}

	private TreeNode isgreater(TreeNode t1, TreeNode t2) {
		if (!t1.getToken().isNum() || !t2.getToken().isNum()) {
			System.out.println("ERROR:isgreater error, at least one of parameters are not a number");
		}
		return Integer.parseInt(t1.getToken().value()) > Integer.parseInt(t2.getToken().value())
				? new TreeNode(new Token("T")) : new TreeNode(new Token("NIL"));
	}

	private int args(TreeNode t) {
		int c = 0;
		while (!t.isAtom()) {
			t = t.rightChild;
			c++;
		}
		if (!t.getToken().isNIL()){
			System.out.println("ERROR:error in taking arguments, NIL should be at the end");
			System.exit(1);
		}
		return c;
	}
}
