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
import org.hummingbirdlang.nodes.arguments.GetThisNode;

public class BuiltinNodes {
  private HBLanguage language;

  private HashMap<String, MethodTargets> classes = new HashMap<>();

  private BuiltinNodes(HBLanguage language) {
    this.language = language;
    this.addNodeFactories(HBStringNodesFactory.getFactories());
  }

  public static BuiltinNodes bootstrap(HBLanguage language) {
    return new BuiltinNodes(language);
  }

  private void addNodeFactories(List<? extends NodeFactory<? extends HBNode>> factories) {
    for (NodeFactory<? extends HBNode> factory : factories) {
      Class<?> nodeClass = factory.getClass().getAnnotation(GeneratedBy.class).value();
      BuiltinMethod methodAnnotation = nodeClass.getAnnotation(BuiltinMethod.class);
      BuiltinClass classAnnotation = nodeClass.getEnclosingClass().getAnnotation(BuiltinClass.class); 

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

    Object[] arguments = argumentsNodes.toArray(new HBNode[argumentsNodes.size()]);
    return factory.createNode(arguments);
  }

  public MethodTargets get(String name) {
    if (!this.classes.containsKey(name)) {
      throw new Error("Missing built-in: " + name);
    }
    return this.classes.get(name);
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
