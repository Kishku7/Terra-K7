package com.kishku7.TerraK7.paralithic.node.unary;

import com.kishku7.TerraK7.paralithic.node.Node;
import com.kishku7.TerraK7.paralithic.node.NodeUtils;
import com.kishku7.TerraK7.paralithic.node.Optimizable;
import com.kishku7.TerraK7.paralithic.node.Statefulness;
import com.kishku7.TerraK7.paralithic.util.Lazy;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.MethodVisitor;


public abstract class UnaryNode implements Optimizable {
    protected Node op;
    private final Lazy<Statefulness> statefulness = Lazy.of(() -> op.statefulness());

    protected UnaryNode(Node op) {
        this.op = op;
    }

    public Node getOp() {
        return op;
    }

    public abstract void applyOperand(MethodVisitor visitor);

    @Override
    public void apply(@NotNull MethodVisitor visitor, String generatedImplementationName) {
        op.apply(visitor, generatedImplementationName); // Push operand result to stack
        applyOperand(visitor); // Apply operator
    }

    @Override
    public @NotNull Node simplify() {
        this.op = NodeUtils.simplify(op);
        statefulness.invalidate();
        return this;
    }

    @Override
    public @NotNull Statefulness statefulness() {
        return statefulness.get();
    }

    @Override
    public @NotNull Node optimize() {
        op = NodeUtils.optimize(op);
        return this;
    }
}
