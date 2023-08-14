package ExpressionTree;
import java.util.*;
import java.util.regex.*;

public class ExpressionTree {
    private ExpressionNode root;
    private String empty = "";
    private String primaryTokens = "+-*/()";
    private int[] priority;
    private String[] validUnary = { "sin", "cos", "sqrt" };

    public ExpressionTree() {
        this.priority = new int[] { 1, 2, 3, 4, 5, 5 };
    }

    public float eval(String expr) {
        this.root = null;
        float resp = Float.MIN_VALUE;
        String nexpr = check(expr);
        int par = parenthesisCheck(expr);
        if (par > -1) {
            System.out.println("Parenthesis error at row: " + par);
            return resp;
        }

        if (nexpr.compareTo(this.empty) != 0) {
            ArrayList<String> cont = getTokens(nexpr);
            boolean complete = convertTree(cont);
            if (!complete) {
                return resp;
            }
            resp = calculate(this.root);
        }

        return resp;
    }

    public float eval(String expr, String[] vars, float[] values) {
        float resp = Float.MIN_NORMAL;
        if (vars.length != values.length) {
            System.out.println("Missing variables or values!");
            return resp;
        }

        for (int i = 0; i < vars.length; i++) {
            if (conflictName(expr, vars[i])) {
                System.out.println("possible var: " + vars[i] + " conflict");
                return resp;
            }
        }

        String tmpExpr = new String(expr);
        for (int i = 0; i < values.length; i++) {
            if (expr.indexOf(vars[i]) == -1) {
                System.out.println("var: " + vars[i] + " not used!");
            }
            String val = "" + values[i];
            tmpExpr = tmpExpr.replaceAll(vars[i], val);
        }
        resp = eval(tmpExpr);

        return resp;
    }

    // verify possible conflict on var name with unary operators
    private boolean conflictName(String expr, String vars) {
        for (int i = 0; i < this.validUnary.length; i++) {
            if (this.validUnary[i].indexOf(vars) > -1) {
                if (expr.indexOf(this.validUnary[i]) > -1) {
                    return true;
                }
            }
        }
        return false;
    }

    private float calculate(ExpressionNode node) {
        if (node == null) {
            throw new NullPointerException("Parse error");
        }

        if (node.isOperator()) {

            if (node.isParenthesis()) {
                float mid = calculate(node.getRigth());
                return mid;
            }

            if (node.isUnary()) {
                float mid = calculate(node.getRigth());
                String unaryOP = node.getOperation();

                switch (unaryOP) {
                case "sin":
                    return (float) Math.sin(mid);
                case "cos":
                    return (float) Math.cos(mid);
                case "sqrt":
                    return (float) Math.sqrt(mid);
                default:
                    throw new IllegalArgumentException("Unkown Operator");
                }
            }

            String op = node.getOperation();
            float a = calculate(node.getLeft());
            float b = calculate(node.getRigth());

            switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("Zero Division");
                }
                return a / b;

            default:
                throw new IllegalArgumentException("Unkown Operator");
            }
        }

        return node.getValue();
    }

    // Integrity
    public String check(String expr) {
        expr = removeSpaces(expr);
        if (!spaceNumbers(expr)) {
            return this.empty;
        }
        boolean ok = validChars(expr);
        if (!ok) {
            System.out.println("Invalid chars!");
            return this.empty;
        }
        return expr;
    }

    // No spaces
    private String removeSpaces(String expr) {
        String str = " ";
        char ult = ' ';

        expr += "  ";
        for (int i = 0; i < expr.length() - 2; i++) {
            char ch = expr.charAt(i);
            if (ch != ' ') {
                str += ch;
                ult = ch;
            } else {
                if (onlyDigits(ult + "") && onlyDigits(expr.charAt(i + 1) + "")) {
                    str += '_';
                }
            }
        }
        str = str.substring(1);
        return str;
    }

    // Space Between numbers
    private boolean spaceNumbers(String expr) {
        if (expr.indexOf("_") > -1) {
            System.out.println("Missing operator!");
            return false;
        }
        return true;
    }

    // valid chars only
    private boolean validChars(String expr) {
        // Pattern p = Pattern.compile("[[0-9./\\(\\)\\*\\+\\-]+]*");
        Pattern p = Pattern.compile("[0-9./+\\-/*/\\\\\\(\\)(sin|cos|sqrt)]+");
        Matcher m = p.matcher(expr);
        return m.matches();
    }

    // parenthesis check
    private int parenthesisCheck(String expr) {
        int count = 0;

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == '(') {
                count++;
            }
            if (c == ')') {
                count--;
            }
            if (count < 0) {
                return i;
            }
        }
        if (count > 0) {
            return expr.length();
        }

        return -1;
    }

    // Tokens
    private ArrayList<String> getTokens(String expr) {
        ArrayList<String> cont = new ArrayList<String>();
        String tmp = "";
        String tmpexpr = "+" + expr + "+";

        for (int i = 0; i < tmpexpr.length(); i++) {
            char ch = tmpexpr.charAt(i);
            if (isToken(ch)) {
                if (!tmp.isEmpty()) {
                    cont.add(tmp);
                    // Adjust negative values
                    if (cont.size() > 2) {
                        String last2 = cont.get(cont.size() - 2);
                        if (last2.length() == 1) {
                            char op1 = last2.charAt(0);
                            if (op1 == '-') {
                                String last3 = cont.get(cont.size() - 3);
                                if (last3.length() == 1) {
                                    char op2 = last3.charAt(0);
                                    if (isToken(op2)) {
                                        cont.remove(cont.size() - 2);
                                        cont.remove(cont.size() - 1);
                                        cont.add("-" + tmp);
                                    }
                                }
                            }
                        }
                    }

                }
                cont.add(ch + "");
                tmp = this.empty;
            } else {
                tmp += ch;
            }
        }

        cont.remove(0);
        cont.remove(cont.size() - 1);
        return cont;
    }

    private boolean isToken(char c) {
        if (this.primaryTokens.indexOf(c) > -1) {
            return true;
        }
        return false;
    }

    // ArrayList to Tree format
    private boolean convertTree(ArrayList<String> cont) {
        ArrayList<ExpressionNode> list = new ArrayList<ExpressionNode>();

        for (int i = 0; i < cont.size(); i++) {
            String tmp = cont.get(i);
            if (onlyDigits(tmp)) {
                ExpressionNode node = new ValueNode();
                node.setValue(tmp);
                list.add(node);
            } else {
                ExpressionNode node = new OperatorNode(this.primaryTokens, this.priority, this.validUnary);
                boolean ok = node.setOperation(tmp, false);
                if (!ok) {
                    System.out.println("Error in Operator!");
                    return false;
                }
                list.add(node);
            }
        }

        Syntax syn = new Syntax();
        if (!syn.check(list)) {
            System.out.println("Syntax Error!");
            return false;
        }

        root = join(list, 0);
        return true;
    }

    // number format check
    private boolean onlyDigits(String s) {
        if (s.isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile("^-?[0-9]\\d*(\\.\\d+)?$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    private ExpressionNode join(ArrayList<ExpressionNode> list, int indx) {
        ExpressionNode top = null, last = null;
        ExpressionNode tmp = list.get(indx);
        Stack<ExpressionNode> stack = new Stack<ExpressionNode>();
        top = tmp;
        last = tmp;

        // recursive parenthesis
        int j = indx;
        if (tmp.isParenthesis()) {
            j++;
            ExpressionNode mid = join(list, j);
            tmp.setRigth(mid);
            j = mid.getLastParIndex();
        } else if (tmp.isUnary()) {
            j += 2;
            ExpressionNode mid = join(list, j);
            tmp.setRigth(mid);
            j = mid.getLastParIndex();
        }
        j++;

        int i = 0;
        for (i = j; i < list.size(); i++) {
            tmp = list.get(i);

            if (tmp.isParenthesis() && tmp.getOperation().compareTo(")") == 0) {
                break;
            }

            if (tmp.isOperator() && !tmp.isParenthesis() && !tmp.isUnary()) {
                stack.push(tmp);
            } else {
                ExpressionNode snode = stack.pop();

                // recursive parenthesis
                if (tmp.isParenthesis()) {
                    i++;
                    ExpressionNode mid = join(list, i);
                    tmp.setRigth(mid);
                    i = mid.getLastParIndex();
                } else if (tmp.isUnary()) {
                    i += 2;
                    ExpressionNode mid = join(list, i);
                    tmp.setRigth(mid);
                    i = mid.getLastParIndex();
                }

                snode.setRigth(tmp);

                if ((!last.isOperator() || last.isParenthesis() || last.isUnary()) && last.getParent() == null) {
                    snode.setLeft(last);
                    top = snode;
                } else {
                    int priority = snode.getPriority();
                    last = last.getParent();
                    if (priority >= last.getPriority()) {
                        // Switch last node
                        snode.setLeft(last.getRigth());
                        last.setRigth(snode);
                    } else {
                        while (last.isOperator() && last.getParent() != null) {
                            last = last.getParent();
                        }
                        snode.setLeft(top);
                        top = snode;
                    }
                }
                last = tmp;
            }

        }
        top.setLastParIndex(i);
        return top;
    }
}
