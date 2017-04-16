package org.hummingbirdlang.parser;

import com.oracle.truffle.api.source.Source;

import org.hummingbirdlang.nodes.HBSourceRootNode;

public class ParserWrapper {
  public static HBSourceRootNode parse(Source source) throws Exception {
    Parser parser = new Parser(source);
    HBSourceRootNode program = parser.Parse();

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
