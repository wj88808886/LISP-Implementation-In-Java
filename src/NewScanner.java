public class NewScanner {

	private String s;

	int pointer = 0;

	public NewScanner(String s) {
		this.s = s;
	}

	public Token getNextToken() {
		if (!hasNextToken()) {
			System.out.println("ERROR:No next Token! Incomplete S-expression");
			System.exit(2);
		}
		char c = s.charAt(pointer);
		while (c == ' ') {
			pointer++;
			c = s.charAt(pointer);
		}
		pointer++;
		if (c == '(') {
			return new Token("(", "OpenParenthesis");
		} else if (c == ')') {
			return new Token(")", "ClosingParenthesis");
		} else if (c == '.') {
			return new Token(".", "Dot");
		} else {
			pointer--;
			StringBuilder sb = new StringBuilder();
			boolean isNumeric = true;
			if (c == '-') {
				sb.append(c);
				pointer++;
				if (pointer >= s.length()) {
					System.out.println("parse negative number error");
					System.exit(1);
				}
				c = s.charAt(pointer);
			}
			while ((isNumber(c) || isLiteral(c))) {
				if (isLiteral(c))
					isNumeric = false;
				sb.append(c);
				pointer++;
				if (!hasNextToken())
					break;
				c = s.charAt(pointer);
			}
			if (sb.length() == 1 && sb.charAt(0) == '-') {
				return new Token(sb.toString(), "Error");
			} else if (isNumber(sb.charAt(0)) && !isNumeric) {
				return new Token(sb.toString(), "Error");
			} else if (sb.charAt(0) == '-' && !isNumeric) {
				return new Token(sb.toString(), "Error");
			} else if (isNumeric) {
				return new Token(sb.toString(), "NumericAtom");
			} else {
				return new Token(sb.toString(), "LiteralAtom");
			}
		}

	}

	public boolean hasNextToken() {
		if ((pointer >= s.length()))
			return false;
		while (s.charAt(pointer) == ' ') {
			pointer++;
			if ((pointer >= s.length()))
				return false;
		}
		return (pointer < s.length());
	}

	private boolean isNumber(char c) {
		return (c >= '0' && c <= '9');
	}

	private boolean isLiteral(char c) {
		return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
	}

}
