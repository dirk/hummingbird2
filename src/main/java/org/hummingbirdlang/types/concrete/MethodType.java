package org.hummingbirdlang.types.concrete;

import org.hummingbirdlang.nodes.HBFunctionRootNode;
import org.hummingbirdlang.nodes.builtins.BuiltinNode;
import org.hummingbirdlang.types.ConcreteType;
import org.hummingbirdlang.types.Property;
import org.hummingbirdlang.types.PropertyNotFoundException;
import org.hummingbirdlang.types.Type;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.NodeFactory;

public final class MethodType extends ConcreteType {
  // Type on which this method is defined.
  private final Type receiverType;
  private final Type returnType;
  private final String name;
  private final CallTarget callTarget;

  public MethodType(
    Type receiverType,
    Type returnType,
    String name,
    CallTarget callTarget
  ) {
    this.receiverType = receiverType;
    this.returnType = returnType;
    this.name = name;
    this.callTarget = callTarget;
  }

  public Type getReceiverType() {
    return this.receiverType;
  }

  public Type getReturnType() {
    return this.returnType;
  }

  public String getName() {
    return this.name;
  }

  public CallTarget getCallTarget() {
    return this.callTarget;
  }

  /**
   * Get a new method instance with a different return type. Methods are
   * currently immutable, so this is used during bootstrap to create methods
   * with an unknown return type and then overwrite them with ones with a
   * real return type.
   */
  public MethodType cloneWithReturnType(Type returnType) {
    return new MethodType(this.receiverType, returnType, this.name, this.callTarget);
  }

  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    throw new PropertyNotFoundException("Not yet implemented");
  }
}
