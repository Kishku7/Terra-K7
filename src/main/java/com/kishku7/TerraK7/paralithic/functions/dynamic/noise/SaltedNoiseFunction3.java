package com.kishku7.TerraK7.paralithic.functions.dynamic.noise;

import com.kishku7.TerraK7.paralithic.functions.dynamic.Context;
import com.kishku7.TerraK7.paralithic.functions.dynamic.DynamicFunction;
import com.kishku7.TerraK7.paralithic.node.Statefulness;
import com.kishku7.TerraK7.seismic.type.sampler.Sampler;
import org.jetbrains.annotations.NotNull;


public class SaltedNoiseFunction3 implements DynamicFunction {
    private final Sampler gen;

    public SaltedNoiseFunction3(Sampler gen) {
        this.gen = gen;
    }

    @Override
    public double eval(double... args) {
        throw new UnsupportedOperationException("Cannot evaluate seeded function without seed context.");
    }

    @Override
    public double eval(Context context, double... args) {
        return gen.getSample(((SeedContext) context).seed() + (long) args[3], args[0], args[1], args[2]);
    }

    @Override
    public int getArgNumber() {
        return 4;
    }

    @Override
    public @NotNull Statefulness statefulness() {
        return Statefulness.CONTEXTUAL;
    }
}
