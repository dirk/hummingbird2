package org.hummingbirdlang.objects;

import com.oracle.truffle.api.CallTarget;

/**
 * The actual variable that exists in memory while we form a method call.
 * It's returned by an `HBPropertyNode` and then potentially acted on by an
 * `HBCallNode`.
 */
public final class Method {
  // Receiver of the call.
  private final Object receiver;
  // Where we eventually need to make a call.
  private final CallTarget callTarget;
  // For debugging.
  private final String name;

  public Method(Object receiver, CallTarget callTarget, String name) {
    this.receiver = receiver;
    this.callTarget = callTarget;
    this.name = name;
  }

  public Object getReceiver() {
    return this.receiver;
  }

  public CallTarget getCallTarget() {
    return this.callTarget;
  }
}
