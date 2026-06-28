package com.kishku7.TerraK7.paralithic.node.binary.number;

import com.kishku7.TerraK7.paralithic.functions.natives.NativeMath;
import com.kishku7.TerraK7.paralithic.node.Constant;
import com.kishku7.TerraK7.paralithic.node.Node;
import com.kishku7.TerraK7.paralithic.node.binary.BinaryNode;
import com.kishku7.TerraK7.paralithic.node.binary.CommutativeBinaryNode;
import com.kishku7.TerraK7.paralithic.node.special.function.NativeFunctionNode;
import com.kishku7.TerraK7.paralithic.node.unary.NegationNode;
import com.kishku7.TerraK7.seismic.util.VMConstants;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.MethodVisitor;

import java.util.List;

import static org.objectweb.asm.Opcodes.DADD;


public class AdditionNode extends CommutativeBinaryNode {
    public AdditionNode(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected BinaryNode newInstance(Node left, Node right) {
        return new AdditionNode(left, right);
    }

    @Override
    public void applyOperand(MethodVisitor visitor, String generatedImplementationName) {
        visitor.visitInsn(DADD);
    }

    @Override
    public Op getOp() {
        return Op.ADD;
    }

    @Override
    public Constant constantSimplify() {
        return Constant.of(((Constant) left).getValue() + ((Constant) right).getValue());
    }

    @Override
    public @NotNull Node finalSimplify() {
        if(left instanceof Constant c && c.getValue() == 0) {
            return right;
        }
        if(right instanceof Constant c && c.getValue() == 0) {
            return left;
        }
        super.finalSimplify();
        if(left instanceof NegationNode n) {
            return new SubtractionNode(right, n.getOp());
        }
        if(right instanceof NegationNode n) {
            return new SubtractionNode(left, n.getOp());
        }
        return this;
    }

    @Override
    public @NotNull Node finalOptimize() {
        if(VMConstants.HAS_FAST_SCALAR_FMA && left instanceof MultiplicationNode m) {
            return new NativeFunctionNode(NativeMath.getNativeMathFunction("fma"), List.of(m.getLeft(), m.getRight(), right));
        }
        if(VMConstants.HAS_FAST_SCALAR_FMA && right instanceof MultiplicationNode m) {
            return new NativeFunctionNode(NativeMath.getNativeMathFunction("fma"), List.of(m.getLeft(), m.getRight(), left));
        }
        return super.finalOptimize();
    }

    @Override
    public double eval(double[] localVariables, double... inputs) {
        return left.eval(localVariables, inputs) + right.eval(localVariables, inputs);
    }
}
