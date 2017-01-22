package org.hummingbirdlang;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.Exception;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.nodes.Node;

@TruffleLanguage.Registration(name = "Hummingbird", version = "0.1", mimeType = HBLanguage.MIME_TYPE)
public final class HBLanguage extends TruffleLanguage<HBContext> {
  public static final String MIME_TYPE = "application/x-hummingbird";

  public static final HBLanguage INSTANCE = new HBLanguage();

  private HBLanguage() {
  }

  @Override
  protected HBContext createContext(Env env) {
    BufferedReader in = new BufferedReader(new InputStreamReader(env.in()));
    PrintWriter out = new PrintWriter(env.out(), true);
    return new HBContext(env, in, out);
  }

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
