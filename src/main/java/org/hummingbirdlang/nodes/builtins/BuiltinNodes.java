package org.hummingbirdlang.nodes.builtins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;

import org.hummingbirdlang.HBLanguage;
import org.hummingbirdlang.nodes.HBFunctionRootNode;
import org.hummingbirdlang.nodes.HBNode;
import org.hummingbirdlang.nodes.arguments.GetArgumentNode;
import org.hummingbirdlang.nodes.arguments.GetThisNode;
import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.concrete.MethodType;

public class BuiltinNodes {
  private HBLanguage language;

  private HashMap<String, MethodTargets> classes = new HashMap<>();

  private BuiltinNodes(HBLanguage language) {
    this.language = language;
    this.addNodeFactories(GlobalNodesFactory.getFactories());
    this.addNodeFactories(IntegerNodesFactory.getFactories());
    this.addNodeFactories(StringNodesFactory.getFactories());
  }

  public static BuiltinNodes bootstrap(HBLanguage language) {
    return new BuiltinNodes(language);
  }

  private void addNodeFactories(List<? extends NodeFactory<? extends HBNode>> factories) {
    for (NodeFactory<? extends HBNode> factory : factories) {
      Class<?> nodeClass = factory.getClass().getAnnotation(GeneratedBy.class).value();
      BuiltinMethod methodAnnotation = this.getMethodAnnotation(nodeClass);
      BuiltinClass classAnnotation = this.getClassAnnotation(nodeClass);

      String methodName = methodAnnotation.value();
      String className = classAnnotation.value();
      MethodTargets methodTargets = this.getOrCreateMethodTargets(className);

      HBNode bodyNode = this.createNode(factory, methodAnnotation);

      HBBuiltinRootNode rootNode = new HBBuiltinRootNode(
        this.language,
        new FrameDescriptor(),
        bodyNode
      );
      CallTarget callTarget = Truffle.getRuntime().createCallTarget(rootNode);
      methodTargets.put(methodName, callTarget);
    }
  }

  private HBNode createNode(NodeFactory<? extends HBNode> factory, BuiltinMethod methodAnnotation) {
    List<List<Class<?>>> signatures = factory.getNodeSignatures();
    assert signatures.size() == 1;

    List<HBNode> argumentsNodes = new ArrayList<>();
    if (methodAnnotation.usesThis()) {
      argumentsNodes.add(new GetThisNode());
    }
    int argumentsCount = methodAnnotation.required();
    for (int index = 0; index < argumentsCount; index++) {
      argumentsNodes.add(new GetArgumentNode(index));
    }

    Object[] arguments = argumentsNodes.toArray(new HBNode[argumentsNodes.size()]);
    return factory.createNode(arguments);
  }

  /**
   * Helper to assist in easily creating a `MethodType`s with a builtin
   * method node as a call target (using `BuiltinMethod` annotation to
   * derive the name).
   */
  public MethodType createMethodType(Type receiverType, Type returnType, Class<?> nodeClass) {
    String name = this.getMethodAnnotation(nodeClass).value();
    CallTarget callTarget = this.getCallTarget(nodeClass);
    return new MethodType(receiverType, returnType, name, callTarget);
  }

  public CallTarget getCallTarget(Class<?> nodeClass) {
    String className = this.getClassAnnotation(nodeClass).value();
    String methodName = this.getMethodAnnotation(nodeClass).value();
    return this.getCallTarget(className, methodName);
  }

  public CallTarget getCallTarget(String className, String methodName) {
    if (!this.classes.containsKey(className)) {
      throw new Error("Missing built-in class: " + className);
    }

    MethodTargets methodTargets = this.classes.get(className);
    if (!methodTargets.contains(methodName)) {
      throw new Error("Missing built-in method: " + className + "." + methodName);
    }
    return methodTargets.get(methodName);
  }

  private MethodTargets getOrCreateMethodTargets(String name) {
    if (this.classes.containsKey(name)) {
      return this.classes.get(name);
    } else {
      MethodTargets methodTargets = new MethodTargets();
      this.classes.put(name, methodTargets);
      return methodTargets;
    }
  }

  private BuiltinMethod getMethodAnnotation(Class<?> nodeClass) {
    return nodeClass.getAnnotation(BuiltinMethod.class);
  }

  private BuiltinClass getClassAnnotation(Class<?> nodeClass) {
    return nodeClass.getEnclosingClass().getAnnotation(BuiltinClass.class);
  }

  public class MethodTargets {
    private HashMap<String, CallTarget> map = new HashMap<>();

    public MethodTargets() {
    }

    public boolean contains(String methodName) {
      return this.map.containsKey(methodName);
    }

    public CallTarget get(String methodName) {
      return this.map.get(methodName);
    }

    public CallTarget put(String methodName, CallTarget callTarget) {
      return this.map.put(methodName, callTarget);
    }
  }
}
