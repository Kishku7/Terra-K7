/*
 * Copyright (c) 2020-2025 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.terra.addons.noise.config.templates.noise.fractal;


import com.kishku7.TerraK7.seismic.algorithms.sampler.noise.fractal.RidgedFractalSampler;
import com.kishku7.TerraK7.seismic.type.sampler.Sampler;


public class RidgedFractalTemplate extends FractalTemplate<RidgedFractalSampler> {
    @Override
    public Sampler get() {
        RidgedFractalSampler sampler = new RidgedFractalSampler(salt, function, fractalGain, fractalLacunarity, weightedStrength, octaves);
        return sampler;
    }
}
