package ExpressionTree;

public class ExpressionNode {
    protected boolean operator;
    protected ExpressionNode left, rigth, parent;

    protected int lastParIndex;

    public boolean isOperator() {
        return this.operator;
    }

    public ExpressionNode getLeft() {
        return this.left;
    }

    public void setLeft(ExpressionNode left) {
        left.setParent(this);
        this.left = left;
    }

    public ExpressionNode getRigth() {
        return this.rigth;
    }

    public void setRigth(ExpressionNode rigth) {
        rigth.setParent(this);
        this.rigth = rigth;
    }

    public ExpressionNode getParent() {
        return this.parent;
    }

    public void setParent(ExpressionNode parent) {
        this.parent = parent;
    }

    public boolean setValue(String s) {
        return true;
    }

    public boolean setOperation(String op, boolean unary) {
        return true;
    }

    public int getPriority() {
        return 0;
    }

    public String getOperation() {
        return "";
    }

    public float getValue() {
        return 0;
    }

    public boolean isUnary() {
        return false;
    }

    public boolean isParenthesis() {
        return false;
    }

    public int getLastParIndex() {
        return this.lastParIndex;
    }

    public void setLastParIndex(int lastParIndex) {
        this.lastParIndex = lastParIndex;
    }
}
