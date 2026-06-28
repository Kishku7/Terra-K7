/*
 * Copyright (c) 2020-2024 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.kishku7.TerraK7.paralithic.functions.dynamic.noise;

import com.kishku7.TerraK7.paralithic.functions.dynamic.Context;
import com.kishku7.TerraK7.paralithic.functions.dynamic.DynamicFunction;
import com.kishku7.TerraK7.paralithic.node.Statefulness;
import com.kishku7.TerraK7.seismic.type.sampler.Sampler;
import org.jetbrains.annotations.NotNull;


public class NoiseFunction2 implements DynamicFunction {
    private final Sampler gen;

    public NoiseFunction2(Sampler gen) {
        this.gen = gen;
    }

    @Override
    public double eval(double... args) {
        throw new UnsupportedOperationException("Cannot evaluate seeded function without seed context.");
    }

    @Override
    public double eval(Context context, double... args) {
        return gen.getSample(((SeedContext) context).seed(), args[0], args[1]);
    }

    @Override
    public int getArgNumber() {
        return 2;
    }

    @Override
    public @NotNull Statefulness statefulness() {
        return Statefulness.CONTEXTUAL;
    }
}
