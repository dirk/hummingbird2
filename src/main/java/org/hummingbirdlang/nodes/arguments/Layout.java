package org.hummingbirdlang.nodes.arguments;

import java.util.ArrayList;
import java.util.List;

public final class Layout {
  public static int TARGET_INDEX = 0;
  public static int RECEIVER_INDEX = 1;
  public static int ARGUMENTS_OFFSET = 2;

  private Object target;
  // Accessible via `this` in method calls.
  private Object receiver;
  private List<Object> arguments = new ArrayList<>();

  public Layout(Object target, Object receiver) {
    this.target = target;
    this.receiver = receiver;
  }

  public void setReceiver(Object receiver) {
    this.receiver = receiver;
  }

  public void addArgument(Object argument) {
    this.arguments.add(argument);
  }

  // Generates an array of arguments suitable for passing to a Truffle call.
  public Object[] getCallArguments() {
    List<Object> callArguments = new ArrayList<>();
    callArguments.add(this.target);
    callArguments.add(this.receiver);
    callArguments.addAll(this.arguments);
    return callArguments.toArray(new Object[callArguments.size()]);
  }
}
