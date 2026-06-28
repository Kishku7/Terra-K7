package com.dfsek.terra.addons.noise.config.templates.normalizer;

import com.kishku7.TerraK7.seismic.algorithms.sampler.normalizer.ScaleNormalizer;
import com.kishku7.TerraK7.seismic.type.sampler.Sampler;
import com.dfsek.tectonic.api.config.template.annotations.Value;

import com.dfsek.terra.api.config.meta.Meta;


public class ScaleNormalizerTemplate extends NormalizerTemplate<ScaleNormalizer> {
    @Value("amplitude")
    private @Meta double amplitude;

    @Override
    public Sampler get() {
        return new ScaleNormalizer(function, amplitude);
    }
}
