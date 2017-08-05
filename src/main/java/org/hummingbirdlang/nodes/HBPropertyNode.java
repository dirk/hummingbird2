package org.hummingbirdlang.nodes;

import java.lang.reflect.Field;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.builtins.Method;
import org.hummingbirdlang.parser.Token;
import org.hummingbirdlang.types.Property;
import org.hummingbirdlang.types.TypeException;
import org.hummingbirdlang.types.concrete.MethodType;
import org.hummingbirdlang.types.realize.InferenceVisitor;

public class HBPropertyNode extends HBExpressionNode {
  @Child private HBExpressionNode targetNode;
  private final String propertyName;
  @CompilationFinal Property property;

  public HBPropertyNode(HBExpressionNode targetNode, Token token) {
    this.targetNode = targetNode;
    this.propertyName = token.val;
  }

  public HBPropertyNode(HBExpressionNode targetNode, String propertyName) {
    this.targetNode = targetNode;
    this.propertyName = propertyName;
  }

  public void accept(InferenceVisitor visitor) throws TypeException {
    this.targetNode.accept(visitor);
    visitor.visit(this);
  }

  public HBExpressionNode getTargetNode() {
    return this.targetNode;
  }

  public String getPropertyName() {
    return this.propertyName;
  }

  public Property getProperty() {
    return this.property;
  }

  public void setProperty(Property property) {
    this.property = property;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    Object parent = this.targetNode.executeGeneric(frame);

    if (this.type instanceof MethodType) {
      MethodType methodType = (MethodType)this.type;
      return new Method(parent, methodType.getCallTarget(), methodType.getName());
    }

    Class<?> parentClass = parent.getClass();
    try {
      Field field = parentClass.getField(this.propertyName);
      return field.get(parent);
    } catch (NoSuchFieldException | IllegalAccessException error) {
      throw new RuntimeException(error);
    }
  }

  @Override
  public String toString() {
    return "HBPropertyNode(" + this.targetNode.toString() + "." + this.propertyName.toString() + ")";
  }
}
