package org.hummingbirdlang.nodes.frames;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

import java.util.ArrayList;
import java.util.List;

import org.hummingbirdlang.nodes.HBNode;
import org.hummingbirdlang.objects.Bindings;
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
    for (Fetcher fetcher : fetchers) {
      String name = fetcher.getName();
      Object value = fetcher.fetch(frame, ownBindings);
      bindings.put(name, value);
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

    public abstract Object fetch(VirtualFrame frame, Bindings ownBindings);
  }

  // Fetch a non-local value from the current scope/frame's own bindings.
  protected class FetchFromOwnBindings extends Fetcher {
    public FetchFromOwnBindings(String name) {
      this.name = name;
    }

    public Object fetch(VirtualFrame frame, Bindings ownBindings) {
      return ownBindings.get(name);
    }
  }

  protected class FetchFromLocal extends Fetcher {
    private GetLocalNode getLocalNode;

    public FetchFromLocal(String name) {
      this.name = name;
      this.getLocalNode = GetLocalNodeGen.create(this.name);
    }

    public Object fetch(VirtualFrame frame, Bindings ownBindings) {
      return this.getLocalNode.executeGeneric(frame);
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
