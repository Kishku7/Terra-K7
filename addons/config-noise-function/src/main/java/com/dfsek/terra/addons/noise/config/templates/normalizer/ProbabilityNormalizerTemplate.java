package com.dfsek.terra.addons.noise.config.templates.normalizer;

import com.kishku7.TerraK7.seismic.algorithms.sampler.normalizer.ProbabilityNormalizer;
import com.kishku7.TerraK7.seismic.type.sampler.Sampler;


public class ProbabilityNormalizerTemplate extends NormalizerTemplate<ProbabilityNormalizer> {
    @Override
    public Sampler get() {
        return new ProbabilityNormalizer(function);
    }
}
