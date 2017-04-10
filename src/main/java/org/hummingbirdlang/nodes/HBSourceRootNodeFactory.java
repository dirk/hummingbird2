package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;

public class HBSourceRootNodeFactory {
  public static HBSourceRootNode create(SourceSection sourceSection, HBStatementNode[] statements) {
    HBBlockNode bodyNode = new HBBlockNode(statements);
    return new HBSourceRootNode(sourceSection, new FrameDescriptor(), bodyNode);
  }
}
