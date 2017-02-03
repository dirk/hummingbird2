package org.hummingbirdlang;

import com.oracle.truffle.api.source.Source;

public class ParserWrapper {
  public static Object parse(Source source) throws Exception {
    Parser parser = new Parser(source);
    Object program = parser.Parse();

    // if (parser.errors.size() > 0) {
    //   StringBuilder message = new StringBuilder("Error(s) parsing script:\n");
    //   for (String error : parser.errors) {
    //     message.append(error).append("\n");
    //   }
    //   throw new Exception(message.toString());
    // } else {
    //
    // }

    if (parser.errors.count > 0) {
      throw new Exception("Error parsing program");
    }

    return program;
  }
}