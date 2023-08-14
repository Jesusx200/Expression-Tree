package ExpressionTree;
/**
 * 
 * @author R. Jesus Cardenas
 * Implementado:
 *  Sumas, Restas, multiplicacion y Division
 *  Utilizacion de parentesis (anidados)
 *  Operadores unarios: sin, cos y sqrt
 *  
 */
public class MainExpression {

    public static void main(String[] args) {
        ExpressionTree tree = new ExpressionTree();
        // float result = tree.eval("-5*3*2 + 210 - 60/0.5");
        // float result = tree.eval("34/2 * 50/5");
        // float result = tree.eval("-3 * -3 - 3 - -3.5");
        // float result = tree.eval("2*(3+5) + (4*2 + (6-4)) + (4)");
        float result = tree.eval(" 2*sin(45)+cos(33 + sqrt(9))+sqrt(100/4)");
        System.out.println("\nResult: " + result);

        String[] vars = new String[] { "x", "y", "z" };
        float[] values = new float[] { 20, 10 , 0};

        result = tree.eval("x + x/y - cos(x*y)", vars, values);
        System.out.println("Result: " + result);
    }

}
