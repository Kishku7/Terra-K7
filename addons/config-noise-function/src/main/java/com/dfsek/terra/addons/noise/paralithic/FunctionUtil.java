package com.dfsek.terra.addons.noise.paralithic;

import com.kishku7.TerraK7.paralithic.eval.tokenizer.ParseException;
import com.kishku7.TerraK7.paralithic.functions.Function;
import com.kishku7.TerraK7.paralithic.functions.dynamic.noise.NoiseFunction2;
import com.kishku7.TerraK7.paralithic.functions.dynamic.noise.NoiseFunction3;
import com.kishku7.TerraK7.paralithic.functions.dynamic.noise.SaltedNoiseFunction2;
import com.kishku7.TerraK7.paralithic.functions.dynamic.noise.SaltedNoiseFunction3;

import java.util.HashMap;
import java.util.Map;

import com.dfsek.terra.addons.noise.config.DimensionApplicableSampler;
import com.dfsek.terra.addons.noise.config.templates.FunctionTemplate;


public class FunctionUtil {
    private FunctionUtil() { }

    public static Map<String, Function> convertFunctionsAndSamplers(Map<String, FunctionTemplate> functions,
                                                                    Map<String, DimensionApplicableSampler> samplers)
    throws ParseException {
        Map<String, Function> functionMap = new HashMap<>();
        for(Map.Entry<String, FunctionTemplate> entry : functions.entrySet()) {
            functionMap.put(entry.getKey(), UserDefinedFunction.newInstance(entry.getValue()));
        }
        samplers.forEach((id, sampler) -> {
            if(sampler.getDimensions() == 2) {
                functionMap.put(id, new NoiseFunction2(sampler.getSampler()));
                functionMap.put(id + "Salted", new SaltedNoiseFunction2(sampler.getSampler()));
            } else {
                functionMap.put(id, new NoiseFunction3(sampler.getSampler()));
                functionMap.put(id + "Salted", new SaltedNoiseFunction3(sampler.getSampler()));
            }
        });
        return functionMap;
    }
}
