package com.kishku7.TerraK7.paralithic.sampler.normalizer;

import com.kishku7.TerraK7.paralithic.Expression;
import com.kishku7.TerraK7.paralithic.eval.parser.Parser;
import com.kishku7.TerraK7.paralithic.eval.parser.Parser.ParseOptions;
import com.kishku7.TerraK7.paralithic.eval.parser.Scope;
import com.kishku7.TerraK7.paralithic.eval.tokenizer.ParseException;
import com.kishku7.TerraK7.paralithic.functions.Function;
import com.kishku7.TerraK7.seismic.algorithms.sampler.normalizer.Normalizer;
import com.kishku7.TerraK7.seismic.type.sampler.Sampler;

import java.util.Map;



public class ExpressionNormalizer extends Normalizer {

    private final Expression expression;

    public ExpressionNormalizer(Sampler sampler, Map<String, Function> functions, String eq, Map<String, Double> vars, ParseOptions parseOptions)
    throws ParseException {
        super(sampler);

        Parser p = new Parser(parseOptions);
        Scope scope = new Scope();

        // 'in' was used as the invocation variable but conflicts with
        // the new 'in' keyword in Paralithic used to denote the end of a let
        // expression. To maintain backwards compatibility but also enable the use
        // of let expressions, if they're enabled then use the longer 'input'
        // invocation variable instead.
        if (parseOptions.useLetExpressions()) {
            scope.addInvocationVariable("input");
        } else {
            scope.addInvocationVariable("in");
        }

        vars.forEach(scope::create);
        functions.forEach(p::registerFunction);
        expression = p.parse(eq, scope);
    }

    @Override
    public double normalize(double in) {
        return expression.evaluate(in);
    }
}
