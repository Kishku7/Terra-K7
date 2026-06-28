/*
 * Copyright (c) 2020-2024 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.kishku7.TerraK7.seismic.algorithms.sampler.normalizer;


import com.kishku7.TerraK7.seismic.type.sampler.Sampler;


public abstract class Normalizer implements Sampler {
    private final Sampler sampler;

    public Normalizer(Sampler sampler) {
        this.sampler = sampler;
    }

    public abstract double normalize(double in);

    @Override
    public double getSample(long seed, double x, double y) {
        return normalize(sampler.getSample(seed, x, y));
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return normalize(sampler.getSample(seed, x, y, z));
    }
}
