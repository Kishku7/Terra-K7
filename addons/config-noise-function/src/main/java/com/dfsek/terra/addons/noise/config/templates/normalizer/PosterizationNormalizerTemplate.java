/*
 * Copyright (c) 2022 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.terra.addons.noise.config.templates.normalizer;

import com.kishku7.TerraK7.seismic.algorithms.sampler.normalizer.PosterizationNormalizer;
import com.kishku7.TerraK7.seismic.type.sampler.Sampler;
import com.dfsek.tectonic.api.config.template.annotations.Value;

import com.dfsek.terra.api.config.meta.Meta;


@SuppressWarnings({ "unused", "FieldMayBeFinal" })
public class PosterizationNormalizerTemplate extends NormalizerTemplate<PosterizationNormalizer> {
    @Value("steps")
    private @Meta int steps;

    @Override
    public Sampler get() {
        return new PosterizationNormalizer(function, steps);
    }
}
