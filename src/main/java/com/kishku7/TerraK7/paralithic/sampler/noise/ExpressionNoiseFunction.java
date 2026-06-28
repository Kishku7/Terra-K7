package com.kishku7.TerraK7.paralithic.sampler.noise;

import com.kishku7.TerraK7.paralithic.Expression;
import com.kishku7.TerraK7.paralithic.eval.parser.Parser;
import com.kishku7.TerraK7.paralithic.eval.parser.Parser.ParseOptions;
import com.kishku7.TerraK7.paralithic.eval.parser.Scope;
import com.kishku7.TerraK7.paralithic.eval.tokenizer.ParseException;
import com.kishku7.TerraK7.paralithic.functions.Function;
import com.kishku7.TerraK7.paralithic.functions.dynamic.noise.SeedContext;
import com.kishku7.TerraK7.seismic.algorithms.sampler.noise.NoiseFunction;

import java.util.Map;


/**
 * NoiseSampler implementation using Paralithic expression
 */
public class ExpressionNoiseFunction extends NoiseFunction {
    private final Expression expression;

    public ExpressionNoiseFunction(Map<String, Function> functions, String eq, Map<String, Double> vars, ParseOptions parseOptions) throws ParseException {
        super(1, 0);
        Parser parser = new Parser(parseOptions);
        Scope scope = new Scope();

        scope.addInvocationVariable("x");
        scope.addInvocationVariable("y");
        scope.addInvocationVariable("z");

        vars.forEach(scope::create);

        functions.forEach(parser::registerFunction);

        expression = parser.parse(eq, scope);
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y) {
        return expression.evaluate(new SeedContext(seed), x, 0, y);
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y, double z) {
        return expression.evaluate(new SeedContext(seed), x, y, z);
    }
}
