package com.kishku7.TerraK7.paralithic.functions.node;

import com.kishku7.TerraK7.paralithic.node.Node;
import com.kishku7.TerraK7.paralithic.node.Statefulness;
import com.kishku7.TerraK7.paralithic.node.special.TernaryIfNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class TernaryIfFunction implements NodeFunction {
    @Override
    public @NotNull Node createNode(@NotNull List<Node> params) {
        return new TernaryIfNode(params.get(0), params.get(1), params.get(2));
    }

    @Override
    public int getArgNumber() {
        return 3;
    }

    @Override
    public @NotNull Statefulness statefulness() {
        return Statefulness.STATELESS; // If function is stateless.
    }
}
