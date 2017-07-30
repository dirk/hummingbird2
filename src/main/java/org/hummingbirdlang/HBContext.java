package org.hummingbirdlang;

import java.io.BufferedReader;
import java.io.PrintWriter;
import com.oracle.truffle.api.TruffleLanguage;

public final class HBContext {
  private final TruffleLanguage.Env env;
  private final BufferedReader input;
  private final PrintWriter output;

  public HBContext(TruffleLanguage.Env env, BufferedReader input, PrintWriter output) {
    this.env = env;
    this.input = input;
    this.output = output;
  }
}
