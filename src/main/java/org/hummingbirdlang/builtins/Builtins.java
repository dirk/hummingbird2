package org.hummingbirdlang.builtins;

import java.util.HashMap;
import java.util.List;

import org.hummingbirdlang.HBLanguage;
import org.hummingbirdlang.nodes.HBFunctionRootNode;
import org.hummingbirdlang.nodes.HBNode;
import org.hummingbirdlang.nodes.builtins.BuiltinClass;
import org.hummingbirdlang.nodes.builtins.BuiltinMethod;
import org.hummingbirdlang.nodes.builtins.HBBuiltinRootNode;
import org.hummingbirdlang.nodes.builtins.HBStringNodesFactory;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;

public class Builtins {
  private HBLanguage language;

  private HashMap<String, MethodTargets> classes = new HashMap<>();

  public Builtins(HBLanguage language) {
    this.language = language;
    addNodeFactories(HBStringNodesFactory.getFactories());
  }

  private void addNodeFactories(List<? extends NodeFactory<? extends HBNode>> factories) {
    for (NodeFactory<? extends HBNode> factory : factories) {
      Class<?> nodeClass = factory.getClass().getAnnotation(GeneratedBy.class).value();
      BuiltinMethod methodAnnotation = nodeClass.getAnnotation(BuiltinMethod.class);
      BuiltinClass classAnnotation = nodeClass.getEnclosingClass().getAnnotation(BuiltinClass.class); 

      String methodName = methodAnnotation.value();
      String className = classAnnotation.value();
      MethodTargets methodTargets = this.getOrCreateMethodTargets(className);

      HBNode bodyNode = factory.createNode();
      HBBuiltinRootNode rootNode = new HBBuiltinRootNode(
        this.language,
        new FrameDescriptor(),
        bodyNode
      );
      CallTarget callTarget = Truffle.getRuntime().createCallTarget(rootNode);
      methodTargets.put(methodName, callTarget);
    }
  }

  private MethodTargets getOrCreateMethodTargets(String name) {
    if (classes.containsKey(name)) {
      return classes.get(name);
    } else {
      return classes.put(name, new MethodTargets());
    }
  }

  private class MethodTargets {
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
