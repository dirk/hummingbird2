package org.hummingbirdlang;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.Exception;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.source.Source;

import org.hummingbirdlang.nodes.builtins.BuiltinNodes;
import org.hummingbirdlang.nodes.HBSourceRootNode;
import org.hummingbirdlang.parser.ParserWrapper;
import org.hummingbirdlang.types.realize.Index;
import org.hummingbirdlang.types.realize.InferenceVisitor;

@TruffleLanguage.Registration(name = "Hummingbird", version = "0.1", mimeType = HBLanguage.MIME_TYPE)
public final class HBLanguage extends TruffleLanguage<HBContext> {
  public static final String MIME_TYPE = "application/x-hummingbird";

  public HBLanguage() {
  }

  @Override
  protected HBContext createContext(Env env) {
    BufferedReader in = new BufferedReader(new InputStreamReader(env.in()));
    PrintWriter out = new PrintWriter(env.out(), true);
    return new HBContext(env, in, out);
  }

  @Override
  protected CallTarget parse(ParsingRequest request) throws Exception {
    Source source = request.getSource();
    HBSourceRootNode program = ParserWrapper.parse(this, source);

    System.out.println(program.toString());

    // Bootstrap the builtin node targets and the builtin types in the
    // type-system.
    BuiltinNodes builtinNodes = BuiltinNodes.bootstrap(this);
    Index index = Index.bootstrap(builtinNodes);

    InferenceVisitor visitor = new InferenceVisitor(index);
    program.accept(visitor);

    return Truffle.getRuntime().createCallTarget(program);
  }

  // TODO: Fully remove this deprecated implementation.:
  //
  //   @Override
  //   protected CallTarget parse(Source source, Node node, String... argumentNames) throws Exception {
  //     Object program = ParserWrapper.parse(source);
  //     System.out.println(program.toString());
  //     return null;
  //   }

  // Called when some other language is seeking for a global symbol.
  @Override
  protected Object findExportedSymbol(HBContext context, String globalName, boolean onlyExplicit) {
    return null;
  }

  @Override
  protected Object getLanguageGlobal(HBContext context) {
    // Context is the highest level global.
    return context;
  }

  @Override
  protected boolean isObjectOfLanguage(Object object) {
    return false;
  }
}
