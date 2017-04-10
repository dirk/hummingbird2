
package org.hummingbirdlang.parser;

import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.source.Source;

import org.hummingbirdlang.nodes.*;

public class Parser {
	public static final int _EOF = 0;
	public static final int _identifier = 1;
	public static final int _let = 2;
	public static final int _var = 3;
	public static final int _func = 4;
	public static final int _lf = 5;
	public static final int _semicolon = 6;
	public static final int _integerLiteral = 7;
	public static final int _floatFractional = 8;
	public static final int maxT = 22;

	static final boolean _T = true;
	static final boolean _x = false;
	static final int minErrDist = 2;

	public Token t;    // last recognized token
	public Token la;   // lookahead token
	int errDist = minErrDist;

	public Scanner scanner;
	public Errors errors;
	private Source source;
	private HBSourceRootNode program;

	

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
		HBSourceRootNode statements = Source();
		this.program = statements;
	}

	HBSourceRootNode  Source() {
		HBSourceRootNode  result;
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
		} else if (la.kind == 4) {
			result = FunctionDeclaration();
		} else if (la.kind == 1 || la.kind == 7 || la.kind == 10) {
			result = Expression();
		} else SynErr(23);
		Terminators();
		return result;
	}

	HBStatementNode  LetDeclaration() {
		HBStatementNode  result;
		Expect(2);
		Expect(9);
		HBExpressionNode expression = Expression();
		result = expression;
		return result;
	}

	HBStatementNode  VarDeclaration() {
		HBStatementNode  result;
		Expect(3);
		Expect(9);
		HBExpressionNode expression = Expression();
		result = expression;
		return result;
	}

	HBFunctionNode  FunctionDeclaration() {
		HBFunctionNode  result;
		Expect(4);
		int start = t.charPos;
		Expect(1);
		String name = t.val;
		Expect(10);
		Expect(11);
		HBBlockNode block = Block();
		int length = (t.charPos + t.val.length()) - start;
		result = new HBFunctionNode(
		 name,
		 block,
		 source.createSection(start, length)
		);
		return result;
	}

	HBExpressionNode  Expression() {
		HBExpressionNode  result;
		result = TernaryExpression();
		return result;
	}

	void Terminators() {
		Terminator();
		while (la.kind == 5 || la.kind == 6) {
			Terminator();
		}
	}

	void Terminator() {
		if (la.kind == 5) {
			Get();
		} else if (la.kind == 6) {
			Get();
		} else SynErr(24);
	}

	HBBlockNode  Block() {
		HBBlockNode  result;
		Expect(12);
		List<HBStatementNode> body = new ArrayList<>();
		while (StartOf(1)) {
			HBStatementNode statement = Statement();
			body.add(statement);
		}
		result = new HBBlockNode(body.toArray(new HBStatementNode[body.size()]));
		Expect(13);
		return result;
	}

	HBExpressionNode  TernaryExpression() {
		HBExpressionNode  result;
		result = null;
		HBExpressionNode condOrValue = LogicalOrExpression();
		result = condOrValue;
		if (la.kind == 14) {
			Get();
			HBExpressionNode then = LogicalOrExpression();
			Expect(15);
			HBExpressionNode els = LogicalOrExpression();
		}
		return result;
	}

	HBExpressionNode  LogicalOrExpression() {
		HBExpressionNode  result;
		result = null;
		HBExpressionNode left = LogicalAndExpression();
		result = left;
		if (la.kind == 16) {
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
		if (la.kind == 17) {
			Get();
			HBExpressionNode right = LogicalAndExpression();
			result = new HBLogicalAndNode(left, right);
		}
		return result;
	}

	HBExpressionNode  GroupOrTupleExpression() {
		HBExpressionNode  result;
		result = null;
		if (la.kind == 10) {
			boolean isTuple = false;
			Get();
			Object expression = Expression();
			if (la.kind == 18) {
				isTuple = true;
				List<Object> elements = new ArrayList<>();
				elements.add(expression);
				Get();
				if (la.kind == 1 || la.kind == 7 || la.kind == 10) {
					Object secondElement = Expression();
					elements.add(secondElement);
					while (la.kind == 18) {
						Get();
						Object nextElement = Expression();
						elements.add(nextElement);
					}
				}
			}
			Expect(11);
			result = null; /* TODO: Tuple expression! */
		} else if (la.kind == 1 || la.kind == 7) {
			HBExpressionNode suffix = SuffixExpression();
			result = suffix;
		} else SynErr(25);
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
		result = null;
		if (la.kind == 1) {
			result = IdentifierAtom();
		} else if (la.kind == 7) {
			result = NumericAtom();
		} else SynErr(26);
		return result;
	}

	HBExpressionNode  Suffix(HBExpressionNode parent) {
		HBExpressionNode  result;
		result = null;
		if (la.kind == 10 || la.kind == 19 || la.kind == 20) {
			if (la.kind == 10) {
				Get();
				HBExpressionNode parameter;
				List<HBExpressionNode> parameters = new ArrayList<>();
				if (la.kind == 1 || la.kind == 7 || la.kind == 10) {
					parameter = Expression();
					parameters.add(parameter);
					while (la.kind == 18) {
						Get();
						parameter = Expression();
						parameters.add(parameter);
					}
				}
				Expect(11);
				result = HBCallNodeFactory.create(parent, parameters);
			} else if (la.kind == 20) {
				Get();
				HBExpressionNode index = Expression();
				Expect(21);
				result = new HBIndexerNode(parent, index);
			} else {
				Get();
				Expect(1);
				result = new HBPropertyNode(parent, t);
			}
			if (StartOf(2)) {
				result = Suffix(result);
			}
		} else if (la.kind == 9) {
			HBExpressionNode newValue = Assignment();
			result = new HBAssignmentNode(parent, newValue);
		} else SynErr(27);
		return result;
	}

	HBExpressionNode  IdentifierAtom() {
		HBExpressionNode  result;
		Expect(1);
		result = new HBIdentifierNode(t);
		return result;
	}

	HBExpressionNode  NumericAtom() {
		HBExpressionNode  result;
		result = null;
		Expect(7);
		StringBuilder number = new StringBuilder(t.val);
		if (StartOf(3)) {
			result = new HBIntegerLiteralNode(number.toString());
		} else if (la.kind == 19) {
			Get();
			number.append(t.val);
			while (la.kind == 8) {
				Get();
				number.append(t.val);
			}
			result = new HBFloatLiteralNode(number.toString());
		} else SynErr(28);
		return result;
	}

	HBExpressionNode  Assignment() {
		HBExpressionNode  newValue;
		Expect(9);
		newValue = Expression();
		return newValue;
	}



	public HBSourceRootNode Parse() {
		la = new Token();
		la.val = "";
		Get();
		Syntax();
		Expect(0);

		return this.program;
	}

	private static final boolean[][] set = {
		{_T,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x},
		{_x,_T,_T,_T, _T,_x,_x,_T, _x,_x,_T,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x},
		{_x,_x,_x,_x, _x,_x,_x,_x, _x,_T,_T,_x, _x,_x,_x,_x, _x,_x,_x,_T, _T,_x,_x,_x},
		{_x,_x,_x,_x, _x,_T,_T,_x, _x,_T,_T,_T, _x,_x,_T,_T, _T,_T,_T,_T, _T,_T,_x,_x}

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
			case 4: s = "func expected"; break;
			case 5: s = "lf expected"; break;
			case 6: s = "semicolon expected"; break;
			case 7: s = "integerLiteral expected"; break;
			case 8: s = "floatFractional expected"; break;
			case 9: s = "\"=\" expected"; break;
			case 10: s = "\"(\" expected"; break;
			case 11: s = "\")\" expected"; break;
			case 12: s = "\"{\" expected"; break;
			case 13: s = "\"}\" expected"; break;
			case 14: s = "\"?\" expected"; break;
			case 15: s = "\":\" expected"; break;
			case 16: s = "\"||\" expected"; break;
			case 17: s = "\"&&\" expected"; break;
			case 18: s = "\",\" expected"; break;
			case 19: s = "\".\" expected"; break;
			case 20: s = "\"[\" expected"; break;
			case 21: s = "\"]\" expected"; break;
			case 22: s = "??? expected"; break;
			case 23: s = "invalid Statement"; break;
			case 24: s = "invalid Terminator"; break;
			case 25: s = "invalid GroupOrTupleExpression"; break;
			case 26: s = "invalid Atom"; break;
			case 27: s = "invalid Suffix"; break;
			case 28: s = "invalid NumericAtom"; break;
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
