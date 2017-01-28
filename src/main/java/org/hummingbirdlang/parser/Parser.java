
package org.hummingbirdlang;

import java.util.ArrayList;
import java.util.List;

public class Parser {
	public static final int _EOF = 0;
	public static final int _identifier = 1;
	public static final int _let = 2;
	public static final int _var = 3;
	public static final int maxT = 15;

	static final boolean _T = true;
	static final boolean _x = false;
	static final int minErrDist = 2;

	public Token t;    // last recognized token
	public Token la;   // lookahead token
	int errDist = minErrDist;

	public Scanner scanner;
	public Errors errors;
	private Object program;

	

	public Parser(Scanner scanner) {
		this.scanner = scanner;
		errors = new Errors();
	}

	void SynErr (int n) {
		if (errDist >= minErrDist) errors.SynErr(la.line, la.col, n);
		errDist = 0;
	}

	public void SemErr (String msg) {
		if (errDist >= minErrDist) errors.SemErr(t.line, t.col, msg);
		errDist = 0;
	}

	void Get () {
		for (;;) {
			t = la;
			la = scanner.Scan();
			if (la.kind <= maxT) {
				++errDist;
				break;
			}

			la = t;
		}
	}

	void Expect (int n) {
		if (la.kind==n) Get(); else { SynErr(n); }
	}

	boolean StartOf (int s) {
		return set[s][la.kind];
	}

	void ExpectWeak (int n, int follow) {
		if (la.kind == n) Get();
		else {
			SynErr(n);
			while (!StartOf(follow)) Get();
		}
	}

	boolean WeakSeparator (int n, int syFol, int repFol) {
		int kind = la.kind;
		if (kind == n) { Get(); return true; }
		else if (StartOf(repFol)) return false;
		else {
			SynErr(n);
			while (!(set[syFol][kind] || set[repFol][kind] || set[0][kind])) {
				Get();
				kind = la.kind;
			}
			return StartOf(syFol);
		}
	}

	void Syntax() {
		Object statements = Program();
		this.program = statements;
	}

	Object  Program() {
		Object  result;
		result = ProgramStatements();
		return result;
	}

	Object  ProgramStatements() {
		Object  result;
		List<Object> body = new ArrayList<>();
		while (la.kind == 2 || la.kind == 3) {
			Object statement = Statement();
			body.add(statement);
		}
		result = body;
		return result;
	}

	Object  Statement() {
		Object  result;
		result = null;
		if (la.kind == 2) {
			result = LetDeclaration();
		} else if (la.kind == 3) {
			result = VarDeclaration();
		} else SynErr(16);
		return result;
	}

	Object  LetDeclaration() {
		Object  result;
		Expect(2);
		Expect(4);
		Object expression = Expression();
		result = expression;
		return result;
	}

	Object  VarDeclaration() {
		Object  result;
		Expect(3);
		Expect(4);
		Object expression = Expression();
		result = expression;
		return result;
	}

	HBExpressionNode  Expression() {
		HBExpressionNode  result;
		result = TernaryExpression();
		return result;
	}

	HBExpressionNode  TernaryExpression() {
		HBExpressionNode  result;
		result = null;
		HBExpressionNode condOrValueExpression = LogicalOrExpression();
		if (la.kind == 5) {
			Get();
			HBExpressionNode thenExpression = LogicalOrExpression();
			Expect(6);
			HBExpressionNode elseExpression = LogicalOrExpression();
		}
		return result;
	}

	HBExpressionNode  LogicalOrExpression() {
		HBExpressionNode  result;
		result = null;
		HBExpressionNode left = LogicalAndExpression();
		if (la.kind == 7) {
			Get();
			HBExpressionNode right = LogicalOrExpression();
		}
		return result;
	}

	HBExpressionNode  LogicalAndExpression() {
		HBExpressionNode  result;
		result = null;
		GroupOrTupleExpression();
		if (la.kind == 8) {
			Get();
			HBExpressionNode right = LogicalAndExpression();
		}
		return result;
	}

	void GroupOrTupleExpression() {
		if (la.kind == 9) {
			boolean isTuple = false;
			Get();
			Object expression = Expression();
			if (la.kind == 10) {
				isTuple = true;
				List<Object> elements = new ArrayList<>();
				elements.add(expression);
				Get();
				if (la.kind == 1 || la.kind == 9) {
					Object secondElement = Expression();
					elements.add(secondElement);
					while (la.kind == 10) {
						Get();
						Object nextElement = Expression();
						elements.add(nextElement);
					}
				}
			}
			Expect(11);
		} else if (la.kind == 1) {
			Object result = SuffixExpression();
		} else SynErr(17);
	}

	Object  SuffixExpression() {
		Object  result;
		result = null;
		HBExpressionNode atom = Atom();
		if (StartOf(1)) {
			result = Suffix(atom);
		}
		return result;
	}

	HBExpressionNode  Atom() {
		HBExpressionNode  result;
		Expect(1);
		result = new HBIdentifierNode(t);
		return result;
	}

	Object  Suffix(Object parent) {
		Object  result;
		result = null;
		if (la.kind == 9 || la.kind == 12 || la.kind == 14) {
			if (la.kind == 9) {
				Get();
				HBExpressionNode parameter;
				List<HBExpressionNode> parameters = new ArrayList<>();
				if (la.kind == 1 || la.kind == 9) {
					parameter = Expression();
					parameters.add(parameter);
					while (la.kind == 10) {
						Get();
						parameter = Expression();
						parameters.add(parameter);
					}
				}
				Expect(11);
			} else if (la.kind == 12) {
				Get();
				Object indexer = Expression();
				Expect(13);
			} else {
				Get();
				Expect(1);
			}
			if (StartOf(1)) {
				result = Suffix(result);
			}
		} else if (la.kind == 4) {
			Assignment();
		} else SynErr(18);
		return result;
	}

	void Assignment() {
		Expect(4);
		HBExpressionNode newValue = Expression();
	}



	public Object Parse() {
		la = new Token();
		la.val = "";
		Get();
		Syntax();
		Expect(0);

		return this.program;
	}

	private static final boolean[][] set = {
		{_T,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x},
		{_x,_x,_x,_x, _T,_x,_x,_x, _x,_T,_x,_x, _T,_x,_T,_x, _x}

	};
} // end Parser


class Errors {
	public int count = 0;                                    // number of errors detected
	public java.io.PrintStream errorStream = System.out;     // error messages go to this stream
	public String errMsgFormat = "-- line {0} col {1}: {2}"; // 0=line, 1=column, 2=tex

	protected void printMsg(int line, int column, String msg) {
		StringBuffer b = new StringBuffer(errMsgFormat);
		int pos = b.indexOf("{0}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, line); }
		pos = b.indexOf("{1}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, column); }
		pos = b.indexOf("{2}");
		if (pos >= 0) b.replace(pos, pos+3, msg);
		errorStream.println(b.toString());
	}

	public void SynErr (int line, int col, int n) {
		String s;
		switch (n) {
			case 0: s = "EOF expected"; break;
			case 1: s = "identifier expected"; break;
			case 2: s = "let expected"; break;
			case 3: s = "var expected"; break;
			case 4: s = "\"=\" expected"; break;
			case 5: s = "\"?\" expected"; break;
			case 6: s = "\":\" expected"; break;
			case 7: s = "\"||\" expected"; break;
			case 8: s = "\"&&\" expected"; break;
			case 9: s = "\"(\" expected"; break;
			case 10: s = "\",\" expected"; break;
			case 11: s = "\")\" expected"; break;
			case 12: s = "\"[\" expected"; break;
			case 13: s = "\"]\" expected"; break;
			case 14: s = "\".\" expected"; break;
			case 15: s = "??? expected"; break;
			case 16: s = "invalid Statement"; break;
			case 17: s = "invalid GroupOrTupleExpression"; break;
			case 18: s = "invalid Suffix"; break;
			default: s = "error " + n; break;
		}
		printMsg(line, col, s);
		count++;
	}

	public void SemErr (int line, int col, String s) {
		printMsg(line, col, s);
		count++;
	}

	public void SemErr (String s) {
		errorStream.println(s);
		count++;
	}

	public void Warning (int line, int col, String s) {
		printMsg(line, col, s);
	}

	public void Warning (String s) {
		errorStream.println(s);
	}
} // Errors


class FatalError extends RuntimeException {
	public static final long serialVersionUID = 1L;
	public FatalError(String s) { super(s); }
}
