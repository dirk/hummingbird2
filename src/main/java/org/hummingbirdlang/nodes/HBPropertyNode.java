package org.hummingbirdlang.nodes;

import java.lang.reflect.Field;
import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.parser.Token;
import org.hummingbirdlang.types.TypeException;
import org.hummingbirdlang.types.realize.InferenceVisitor;

public class HBPropertyNode extends HBExpressionNode {
  @Child private HBExpressionNode targetNode;
  private final String property;

  public HBPropertyNode(HBExpressionNode targetNode, Token token) {
    this.targetNode = targetNode;
    this.property = token.val;
  }

  public HBPropertyNode(HBExpressionNode targetNode, String property) {
    this.targetNode = targetNode;
    this.property = property;
  }

  public void accept(InferenceVisitor visitor) throws TypeException {
    this.targetNode.accept(visitor);
    visitor.visit(this);
  }

  public HBExpressionNode getTargetNode() {
    return this.targetNode;
  }

  public String getProperty() {
    return this.property;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) throws Exception, NoSuchFieldException {
    Object receiver = this.targetNode.executeGeneric(frame);
    Class<?> receiverClass = receiver.getClass();
    Field field = receiverClass.getField(this.property);
    return field.get(receiver);
  }

  @Override
  public String toString() {
    return "HBPropertyNode(" + this.targetNode.toString() + "." + this.property.toString() + ")";
  }
}
