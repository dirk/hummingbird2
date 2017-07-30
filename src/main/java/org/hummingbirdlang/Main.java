package org.hummingbirdlang;

import java.io.File;
import java.io.IOException;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.vm.PolyglotEngine;

public final class Main {
    public static void main(String[] args) throws IOException, RuntimeException {
      System.out.println("(running on " + Truffle.getRuntime().getName() + ")");

      PolyglotEngine engine = PolyglotEngine.newBuilder().setIn(System.in).setOut(System.out).build();
      assert engine.getLanguages().containsKey(HBLanguage.MIME_TYPE);

      Source source = Source.newBuilder(new File(args[0])).build();
      engine.eval(source);
    }
}
