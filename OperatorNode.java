package ExpressionTree;
public class OperatorNode extends ExpressionNode {

    private String operation;
    private boolean unary;
    private boolean parenthesis;
    private String valid;
    private int[] prior;
    private String[] validUnary;
    private int priority;

    public OperatorNode(String tokens, int[] prior, String[] unary) {
        this.operator = true;
        this.valid = tokens;
        this.prior = prior;
        this.validUnary = unary;
    }

    public boolean setOperation(String op, boolean unary) {
        this.operation = op;
        this.unary = unary;

        if (op.length() > 1) {
            this.unary = isValidUnary();
            return this.unary;
        } else {
            boolean ver = isValidOP();
            if (ver) {
                if (op.compareTo("(") == 0 || op.compareTo(")") == 0) {
                    this.parenthesis = ver;
                    return ver;
                }
            }
            return ver;
        }
    }

    private boolean isValidOP() {
        int indx = this.valid.indexOf(this.operation);
        if (indx > -1) {
            this.priority = this.prior[indx];
            return true;
        }
        return false;
    }

    private boolean isValidUnary() {
        for (String s : validUnary) {
            if (s.compareTo(this.operation) == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isUnary() {
        return this.unary;
    }

    public String getOperation() {
        return this.operation;
    }

    public int getPriority() {
        return this.priority;
    }

    public boolean isParenthesis() {
        return this.parenthesis;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String toString() {
        String str = "";
        str += this.operation;
        return str;
    }
}
