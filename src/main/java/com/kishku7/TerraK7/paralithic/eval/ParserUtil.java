package com.kishku7.TerraK7.paralithic.eval;

import com.kishku7.TerraK7.paralithic.functions.natives.NativeMath;
import com.kishku7.TerraK7.paralithic.node.Node;
import com.kishku7.TerraK7.paralithic.node.binary.BinaryNode;
import com.kishku7.TerraK7.paralithic.node.binary.booleans.AndNode;
import com.kishku7.TerraK7.paralithic.node.binary.booleans.ComparisonNode;
import com.kishku7.TerraK7.paralithic.node.binary.booleans.OrNode;
import com.kishku7.TerraK7.paralithic.node.binary.number.AdditionNode;
import com.kishku7.TerraK7.paralithic.node.binary.number.DivisionNode;
import com.kishku7.TerraK7.paralithic.node.binary.number.ModuloNode;
import com.kishku7.TerraK7.paralithic.node.binary.number.MultiplicationNode;
import com.kishku7.TerraK7.paralithic.node.binary.number.SubtractionNode;
import com.kishku7.TerraK7.paralithic.node.special.function.NativeFunctionNode;

import java.util.Arrays;


public class ParserUtil {
    public static Node createBinaryOperation(BinaryNode.Op op, Node left, Node right) {
        return switch(op) {
            case ADD -> new AdditionNode(left, right);
            case SUBTRACT -> new SubtractionNode(left, right);
            case MULTIPLY -> new MultiplicationNode(left, right);
            case DIVIDE -> new DivisionNode(left, right);
            case POWER -> new NativeFunctionNode(NativeMath.getNativeMathFunction("pow"), Arrays.asList(left, right));
            case MODULO -> new ModuloNode(left, right);
            case LT, LT_EQ, GT, GT_EQ, EQ, NEQ -> new ComparisonNode(left, right, op);
            case AND -> new AndNode(left, right);
            case OR -> new OrNode(left, right);
        };
    }
}
