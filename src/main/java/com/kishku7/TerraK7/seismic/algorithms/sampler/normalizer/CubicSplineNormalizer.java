package com.kishku7.TerraK7.seismic.algorithms.sampler.normalizer;

import com.kishku7.TerraK7.seismic.type.CubicSpline;
import com.kishku7.TerraK7.seismic.type.sampler.Sampler;


public class CubicSplineNormalizer extends Normalizer {
    private final CubicSpline spline;

    public CubicSplineNormalizer(Sampler sampler, CubicSpline spline) {
        super(sampler);
        this.spline = spline;
    }

    @Override
    public double normalize(double in) {
        return spline.apply(in);
    }
}
