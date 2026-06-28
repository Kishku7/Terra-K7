package com.kishku7.TerraK7.paralithic.functions.dynamic;

import com.kishku7.TerraK7.paralithic.functions.Function;
import com.kishku7.TerraK7.paralithic.node.Node;
import com.kishku7.TerraK7.paralithic.node.Statefulness;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;


public interface DynamicFunction extends Function {
    double eval(double... args);

    default double eval(@Nullable Context context, double... args) {
        return eval(args);
    }

    @Override
    default DynamicFunction withSimplifyRule(java.util.function.Function<List<Node>, Optional<Node>> rule) {
        return new DynamicFunction() {
            @Override
            public double eval(double... args) {
                return DynamicFunction.this.eval(args);
            }

            @Override
            public double eval(@Nullable Context context, double... args) {
                return DynamicFunction.super.eval(context, args);
            }

            @Override
            public int getArgNumber() {
                return DynamicFunction.this.getArgNumber();
            }

            @Override
            public @NotNull Statefulness statefulness() {
                return DynamicFunction.this.statefulness();
            }

            @Override
            public Optional<Node> simplify(List<Node> args) {
                return DynamicFunction.super.simplify(args).or(() -> rule.apply(args));
            }
        };
    }
}
