package org.hummingbirdlang;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeChildren;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.frame.VirtualFrame;

// Basic identifier of a local or global name.
@NodeChildren({@NodeChild("leftNode"), @NodeChild("rightNode")})
@NodeField(name = "operator", type = HBBinaryOperator.class)
public class HBBinaryOperatorNode extends HBExpressionNode {
  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return null;
  }
}
