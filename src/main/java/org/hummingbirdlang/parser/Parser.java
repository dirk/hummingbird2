
package org.hummingbirdlang;

import java.util.ArrayList;
import java.util.List;

public class Parser {
	public static final int _EOF = 0;
	public static final int _identifier = 1;
	public static final int _let = 2;
	public static final int _var = 3;
	public static final int maxT = 9;

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
		} else SynErr(10);
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

	Object  Expression() {
		Object  result;
		result = TernaryExpression();
		return result;
	}

	Token  Identifier() {
		Token  result;
		Expect(1);
		result = t; 
		return result;
	}

	Object  TernaryExpression() {
		Object  result;
		result = null; 
		PostfixExpression();
		if (la.kind == 5) {
			Object thenElse = TernaryThenElse();
		}
		return result;
	}

	void PostfixExpression() {
		GroupExpression();
	}

	Object  TernaryThenElse() {
		Object  result;
		result = null; 
		Expect(5);
		PostfixExpression();
		Expect(6);
		PostfixExpression();
		return result;
	}

	void GroupExpression() {
		if (la.kind == 7) {
			Get();
			Object expression = Expression();
			Expect(8);
		} else if (la.kind == 1) {
			Token atom = Atom();
		} else SynErr(11);
	}

	Token  Atom() {
		Token  result;
		result = Identifier();
		return result;
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
		{_T,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x}

	};
} // end Parser


class Errors {
	public int count = 0;                                    // number of errors detected
	public java.io.PrintStream errorStream = System.out;     // error messages go to this stream
	public String errMsgFormat = "-- line {0} col {1}: {2}"; // 0=line, 1=column, 2=text

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
			case 7: s = "\"(\" expected"; break;
			case 8: s = "\")\" expected"; break;
			case 9: s = "??? expected"; break;
			case 10: s = "invalid Statement"; break;
			case 11: s = "invalid GroupExpression"; break;
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