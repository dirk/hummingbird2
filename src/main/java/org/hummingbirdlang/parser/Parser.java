
package org.hummingbirdlang;

import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.source.Source;

public class Parser {
	public static final int _EOF = 0;
	public static final int _identifier = 1;
	public static final int _let = 2;
	public static final int _var = 3;
	public static final int _lf = 4;
	public static final int _semicolon = 5;
	public static final int maxT = 17;

	static final boolean _T = true;
	static final boolean _x = false;
	static final int minErrDist = 2;

	public Token t;    // last recognized token
	public Token la;   // lookahead token
	int errDist = minErrDist;

	public Scanner scanner;
	public Errors errors;
	private Source source;
	private Object program;

	

	// public Parser(Scanner scanner) {
	// 	this.scanner = scanner;
	// 	errors = new Errors();
	// }

	public Parser(Source source) {
		this.scanner = new Scanner(source.getInputStream());
		this.source = source;
		this.errors = new Errors();
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
		Object statements = Source();
		this.program = statements;
	}

	Object  Source() {
		Object  result;
		HBStatementNode[] statements = SourceStatements();
		result = HBSourceRootNodeFactory.create(source.createUnavailableSection(), statements);
		return result;
	}

	HBStatementNode[]  SourceStatements() {
		HBStatementNode[]  result;
		List<HBStatementNode> body = new ArrayList<>();
		while (StartOf(1)) {
			HBStatementNode statement = Statement();
			body.add(statement);
		}
		result = body.toArray(new HBStatementNode[body.size()]);
		return result;
	}

	HBStatementNode  Statement() {
		HBStatementNode  result;
		result = null;
		if (la.kind == 2) {
			result = LetDeclaration();
		} else if (la.kind == 3) {
			result = VarDeclaration();
		} else if (la.kind == 1 || la.kind == 11) {
			result = Expression();
		} else SynErr(18);
		Terminator();
		while (la.kind == 4 || la.kind == 5) {
			Terminator();
		}
		return result;
	}

	HBStatementNode  LetDeclaration() {
		HBStatementNode  result;
		Expect(2);
		Expect(6);
		HBExpressionNode expression = Expression();
		result = expression;
		return result;
	}

	HBStatementNode  VarDeclaration() {
		HBStatementNode  result;
		Expect(3);
		Expect(6);
		HBExpressionNode expression = Expression();
		result = expression;
		return result;
	}

	HBExpressionNode  Expression() {
		HBExpressionNode  result;
		result = TernaryExpression();
		return result;
	}

	void Terminator() {
		if (la.kind == 4) {
			Get();
		} else if (la.kind == 5) {
			Get();
		} else SynErr(19);
	}

	HBExpressionNode  TernaryExpression() {
		HBExpressionNode  result;
		result = null;
		HBExpressionNode condOrValue = LogicalOrExpression();
		result = condOrValue;
		if (la.kind == 7) {
			Get();
			HBExpressionNode then = LogicalOrExpression();
			Expect(8);
			HBExpressionNode els = LogicalOrExpression();
		}
		return result;
	}

	HBExpressionNode  LogicalOrExpression() {
		HBExpressionNode  result;
		result = null;
		HBExpressionNode left = LogicalAndExpression();
		result = left;
		if (la.kind == 9) {
			Get();
			HBExpressionNode right = LogicalOrExpression();
			result = new HBLogicalOrNode(left, right);
		}
		return result;
	}

	HBExpressionNode  LogicalAndExpression() {
		HBExpressionNode  result;
		result = null;
		HBExpressionNode left = GroupOrTupleExpression();
		result = left;
		if (la.kind == 10) {
			Get();
			HBExpressionNode right = LogicalAndExpression();
			result = new HBLogicalAndNode(left, right);
		}
		return result;
	}

	HBExpressionNode  GroupOrTupleExpression() {
		HBExpressionNode  result;
		result = null;
		if (la.kind == 11) {
			boolean isTuple = false;
			Get();
			Object expression = Expression();
			if (la.kind == 12) {
				isTuple = true;
				List<Object> elements = new ArrayList<>();
				elements.add(expression);
				Get();
				if (la.kind == 1 || la.kind == 11) {
					Object secondElement = Expression();
					elements.add(secondElement);
					while (la.kind == 12) {
						Get();
						Object nextElement = Expression();
						elements.add(nextElement);
					}
				}
			}
			Expect(13);
			result = null; /* TODO: Tuple expression! */
		} else if (la.kind == 1) {
			HBExpressionNode suffix = SuffixExpression();
			result = suffix;
		} else SynErr(20);
		return result;
	}

	HBExpressionNode  SuffixExpression() {
		HBExpressionNode  result;
		HBExpressionNode atom = Atom();
		result = atom;
		if (StartOf(2)) {
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

	HBExpressionNode  Suffix(HBExpressionNode parent) {
		HBExpressionNode  result;
		result = null;
		if (la.kind == 11 || la.kind == 14 || la.kind == 16) {
			if (la.kind == 11) {
				Get();
				HBExpressionNode parameter;
				List<HBExpressionNode> parameters = new ArrayList<>();
				if (la.kind == 1 || la.kind == 11) {
					parameter = Expression();
					parameters.add(parameter);
					while (la.kind == 12) {
						Get();
						parameter = Expression();
						parameters.add(parameter);
					}
				}
				Expect(13);
				result = HBCallNodeFactory.create(parent, parameters);
			} else if (la.kind == 14) {
				Get();
				HBExpressionNode index = Expression();
				Expect(15);
				result = new HBIndexerNode(parent, index);
			} else {
				Get();
				Expect(1);
				result = new HBPropertyNode(parent, t);
			}
			if (StartOf(2)) {
				result = Suffix(result);
			}
		} else if (la.kind == 6) {
			HBExpressionNode newValue = Assignment();
			result = new HBAssignmentNode(parent, newValue);
		} else SynErr(21);
		return result;
	}

	HBExpressionNode  Assignment() {
		HBExpressionNode  newValue;
		Expect(6);
		newValue = Expression();
		return newValue;
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
		{_T,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x},
		{_x,_T,_T,_T, _x,_x,_x,_x, _x,_x,_x,_T, _x,_x,_x,_x, _x,_x,_x},
		{_x,_x,_x,_x, _x,_x,_T,_x, _x,_x,_x,_T, _x,_x,_T,_x, _T,_x,_x}

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
			case 4: s = "lf expected"; break;
			case 5: s = "semicolon expected"; break;
			case 6: s = "\"=\" expected"; break;
			case 7: s = "\"?\" expected"; break;
			case 8: s = "\":\" expected"; break;
			case 9: s = "\"||\" expected"; break;
			case 10: s = "\"&&\" expected"; break;
			case 11: s = "\"(\" expected"; break;
			case 12: s = "\",\" expected"; break;
			case 13: s = "\")\" expected"; break;
			case 14: s = "\"[\" expected"; break;
			case 15: s = "\"]\" expected"; break;
			case 16: s = "\".\" expected"; break;
			case 17: s = "??? expected"; break;
			case 18: s = "invalid Statement"; break;
			case 19: s = "invalid Terminator"; break;
			case 20: s = "invalid GroupOrTupleExpression"; break;
			case 21: s = "invalid Suffix"; break;
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
