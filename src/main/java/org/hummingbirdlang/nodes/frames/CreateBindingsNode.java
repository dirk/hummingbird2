package org.hummingbirdlang.nodes.frames;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;

import java.util.ArrayList;
import java.util.List;

import org.hummingbirdlang.nodes.HBNode;
import org.hummingbirdlang.runtime.bindings.Binding;
import org.hummingbirdlang.runtime.bindings.Bindings;
import org.hummingbirdlang.runtime.bindings.MaterializedBinding;
import org.hummingbirdlang.types.FunctionType;
import org.hummingbirdlang.types.scope.Resolution;
import org.hummingbirdlang.types.scope.Scope;

// Assembles the `Bindings` for a function.
@NodeField(name = "type", type = FunctionType.class)
public abstract class CreateBindingsNode extends HBNode {
  protected abstract FunctionType getType();

  public abstract Bindings executeCreateBindings(VirtualFrame frame);

  @Specialization(guards = {"needsBindings()"})
  public Bindings createBindings(
    VirtualFrame frame,
    @Cached("createGetBindingsNode()") GetBindingsNode getBindingsNode,
    @Cached(value = "createFetchers()", dimensions = 1) Fetcher[] fetchers
  ) {
    Bindings ownBindings = getBindingsNode.executeGetBindings(frame);
    Bindings bindings = new Bindings();
    if (fetchers.length > 0) {
      MaterializedFrame materializedFrame = frame.materialize();
      for (Fetcher fetcher : fetchers) {
        String name = fetcher.getName();
        Binding binding = fetcher.fetch(materializedFrame, ownBindings);
        bindings.add(name, binding);
      }
    }
    return bindings;
  }

  @Specialization(guards = {"!needsBindings()"})
  public Bindings skipBindings(VirtualFrame frame) {
    return null;
  }

  protected GetBindingsNode createGetBindingsNode() {
    return GetBindingsNodeGen.create();
  }

  protected Fetcher[] createFetchers() {
    List<Fetcher> fetchers = new ArrayList<>();

    Scope ownScope = this.getOwnScope();
    Scope declarationScope = this.getDeclarationScope();

    for (Resolution resolution : ownScope.getNonLocalResolutions()) {
      String name = resolution.getName();
      if (resolution.getHighestScope() == declarationScope) {
        // If the resolution ends at the current scope then we want to look for
        // a local variable.
        fetchers.add(new FetchFromLocal(name));
      } else {
        // If the resolution doesn't end at the current scope then we need to
        // fetch from our own bindings.
        fetchers.add(new FetchFromOwnBindings(name));
      }
    }

    return fetchers.toArray(new Fetcher[fetchers.size()]);
  }

  protected abstract class Fetcher {
    protected String name;

    public String getName() {
      return this.name;
    }

    public abstract Binding fetch(MaterializedFrame frame, Bindings ownBindings);
  }

  // Inherit a non-local value from the current frame's own bindings.
  protected class FetchFromOwnBindings extends Fetcher {
    public FetchFromOwnBindings(String name) {
      this.name = name;
    }

    public Binding fetch(MaterializedFrame frame, Bindings ownBindings) {
      return ownBindings.get(this.name);
    }
  }

  protected class FetchFromLocal extends Fetcher {
    public FetchFromLocal(String name) {
      this.name = name;
    }

    public Binding fetch(MaterializedFrame frame, Bindings ownBindings) {
      return new MaterializedBinding(this.name, frame);
    }
  }

  protected boolean needsBindings() {
    // If there's a scope (ie. it's not a builtin function) then it will
    // need bindings.
    return (this.getOwnScope() != null);
  }

  protected Scope getOwnScope() {
    return this.getType().getOwnScope();
  }

  protected Scope getDeclarationScope() {
    return this.getType().getDeclarationScope();
  }
}
