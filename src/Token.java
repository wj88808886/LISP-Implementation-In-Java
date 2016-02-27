
public class Token {

	private String[] Funs = new String[] { "T", "NIL", "QUOTE", "COND", "CAR", "CDR", "CONS", "ATOM", "EQ", "NULL",
			"INT", "PLUS", "MINUS", "TIMES", "QUOTIENT", "REMAINDER", "LESS", "GREATER" };
	private String value;
	private String type;
	private boolean isAtom = false;
	private boolean isOpenParenthesis = false;
	private boolean isDot = false;
	private boolean isCloseParenthesis = false;

	private boolean isT = false;
	private boolean isNIL = false;
	private boolean isQuote = false;
	private boolean isCond = false;

	private boolean isCAR = false;
	private boolean isCDR = false;
	private boolean isCONS = false;
	private boolean isATOM = false;
	private boolean isEQ = false;

	private boolean isNULL = false;
	private boolean isINT = false;
	private boolean isPLUS = false;
	private boolean isMINUS = false;
	private boolean isTIMES = false;
	private boolean isQUOTIENT = false;
	private boolean isREMAINDER = false;
	private boolean isLESS = false;
	private boolean isGREATER = false;
	private int index = -1;
	private boolean CanEval = true;

	public Token() {
		this.value = "";
		this.type = "";
	}

	public Token(String value) {
		this.value = value;
		this.type = "LiteralAtom";
		this.isAtom = true;
		if (value.equals("T")) {
			this.isT = true;
		} else if (value.equals("NIL")) {
			this.isNIL = true;
		}
	}

	public Token(String value, String type) {
		this.value = value;
		this.type = type;
		if (type.equals("NumericAtom") || type.equals("LiteralAtom")) {
			this.isAtom = true;
			if (type.equals("LiteralAtom")) {
				for (int i = 0; i < Funs.length; i++) {
					if (value.equals(Funs[i])) {
						if (i == 0)
							this.isT = true;
						if (i == 1)
							this.isNIL = true;
						if (i == 2)
							this.isQuote = true;
						if (i == 3)
							this.isCond = true;
						if (i == 4)
							this.isCAR = true;
						if (i == 5)
							this.isCDR = true;
						if (i == 6)
							this.isCONS = true;
						if (i == 7)
							this.isATOM = true;
						if (i == 8)
							this.isEQ = true;
						if (i == 9)
							this.isNULL = true;
						if (i == 10)
							this.isINT = true;
						if (i == 11)
							this.isPLUS = true;
						if (i == 12)
							this.isMINUS = true;
						if (i == 13)
							this.isTIMES = true;
						if (i == 14)
							this.isQUOTIENT = true;
						if (i == 15)
							this.isREMAINDER = true;
						if (i == 16)
							this.isLESS = true;
						if (i == 17)
							this.isGREATER = true;
						index = i;
						break;
					}

				}
				if (index == -1) {
					this.CanEval = false;
				}
			}
		} else if (type.equals("OpenParenthesis")) {
			this.isOpenParenthesis = true;
		} else if (type.equals("Dot")) {
			this.isDot = true;
		} else if (type.equals("ClosingParenthesis")) {
			this.isCloseParenthesis = true;
		}
	}

	public String value() {
		return value;
	}

	public String type() {
		return type;
	}

	public boolean isNum() {
		return type.equals("NumericAtom");
	}

	public boolean isAtom() {
		return isAtom;
	}

	public boolean isOpenParenthesis() {
		return isOpenParenthesis;
	}

	public boolean isDot() {
		return isDot;
	}

	public boolean isCloseParenthesis() {
		return isCloseParenthesis;
	}

	public boolean isT() {
		return isT;
	}

	public boolean isNIL() {
		return isNIL;
	}

	public boolean isQuote() {
		return isQuote;
	}

	public boolean isCond() {
		return isCond;
	}

	public boolean isCAR() {
		return isCAR;
	}

	public boolean isCDR() {
		return isCDR;
	}

	public boolean isCONS() {
		return isCONS;
	}

	public boolean isATOM() {
		return isATOM;
	}

	public boolean isEQ() {
		return isEQ;
	}

	public boolean isNULL() {
		return isNULL;
	}

	public boolean isINT() {
		return isINT;
	}

	public boolean isPLUS() {
		return isPLUS;
	}

	public boolean isMINUS() {
		return isMINUS;
	}

	public boolean isTIMES() {
		return isTIMES;
	}

	public boolean isQUOTIENT() {
		return isQUOTIENT;
	}

	public boolean isREMAINDER() {
		return isREMAINDER;
	}

	public boolean isLESS() {
		return isLESS;
	}

	public boolean isGREATER() {
		return isGREATER;
	}
	
	public boolean canEval(){
		return CanEval;
	}
}
