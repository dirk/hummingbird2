package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.source.SourceSection;

import org.hummingbirdlang.HBLanguage;

public class HBSourceRootNodeFactory {
  public static HBSourceRootNode create(HBLanguage language, SourceSection sourceSection, HBStatementNode[] statements) {
    HBBlockNode bodyNode = new HBBlockNode(statements);
    return new HBSourceRootNode(language, sourceSection, new FrameDescriptor(), bodyNode);
  }
}
