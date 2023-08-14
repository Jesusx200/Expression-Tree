package ExpressionTree;
public class ValueNode extends ExpressionNode {

    private float value;
    private String svalue;

    private String operation;

    public ValueNode() {
        this.operator = false;
        this.operation = "value";
    }

    public boolean setValue(String svalue) {
        try {
            this.svalue = svalue;
            this.value = Float.parseFloat(this.svalue);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public float getValue() {
        return this.value;
    }

    public String getOperation() {
        return operation;
    }

    public String toString() {
        String str = "";
        str += svalue;
        return str;
    }
}
