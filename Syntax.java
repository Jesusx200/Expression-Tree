package ExpressionTree;
import java.util.*;

public class Syntax {
    Hashtable<String, ArrayList<String>> table;

    public Syntax() {
        this.table = new Hashtable<String, ArrayList<String>>();

        ArrayList<String> init = new ArrayList<String>(Arrays.asList("value", "(", "sin", "cos", "sqrt"));
        this.table.put("init", init);

        ArrayList<String> end = new ArrayList<String>(Arrays.asList("value", ")"));
        this.table.put("end", end);

        ArrayList<String> num = new ArrayList<String>(Arrays.asList("+", "-", "*", "/", ")"));
        this.table.put("value", num);

        ArrayList<String> op1 = new ArrayList<String>(Arrays.asList("value", "(", ")", "sin", "cos", "sqrt"));
        this.table.put("+", op1);
        this.table.put("-", op1);
        this.table.put("*", op1);
        this.table.put("/", op1);

        ArrayList<String> par1 = new ArrayList<String>(Arrays.asList("value", "("));
        this.table.put("(", par1);

        ArrayList<String> par2 = new ArrayList<String>(Arrays.asList("+", "-", "*", "/", ")"));
        this.table.put(")", par2);

        ArrayList<String> unary = new ArrayList<String>(Arrays.asList("("));
        this.table.put("sin", unary);
        this.table.put("cos", unary);
        this.table.put("sqrt", unary);

    }

    public boolean check(ArrayList<ExpressionNode> list) {
        ArrayList<String> next;

        next = this.table.get("init");
        if (next.indexOf(list.get(0).getOperation()) == -1) {
            return false;
        }

        for (int i = 0; i < list.size() - 1; i++) {
            ExpressionNode a = list.get(i);
            ExpressionNode b = list.get(i + 1);

            next = this.table.get(a.getOperation());
            if (next == null) {
                return false;
            }
            if (next.indexOf(b.getOperation()) == -1) {
                return false;
            }
        }

        next = this.table.get("end");
        if (next.indexOf(list.get(list.size() - 1).getOperation()) == -1) {
            return false;
        }
        return true;
    }
}
