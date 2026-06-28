package com.kishku7.TerraK7.seismic.algorithms.sampler.normalizer;


import com.kishku7.TerraK7.seismic.type.sampler.Sampler;


public class ScaleNormalizer extends Normalizer {
    private final double scale;

    public ScaleNormalizer(Sampler sampler, double scale) {
        super(sampler);
        this.scale = scale;
    }

    @Override
    public double normalize(double in) {
        return in * scale;
    }
}
