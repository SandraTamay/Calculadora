import java.util.Scanner;
import java.util.Stack;

public class Calculadora {
  public static void main(String[] args) {

    System.out.println("Escribe la expresión: ");
    Scanner entrada = new Scanner(System.in);

    //se depura la expresion 
    String expr = depurar(entrada.nextLine());
    String[] arrayInfix = expr.split(" ");

    //Pila entrada
    Stack < String > E = new Stack < String > (); 
    //Pila temporal para operadores
    Stack < String > P = new Stack < String > (); 
    //Pila salida
    Stack < String > S = new Stack < String > (); 

    
    for (int i = arrayInfix.length - 1; i >= 0; i--) 
    {
      E.push(arrayInfix[i]);
    }

    try {
      //Infijo a Postfijo
      while (!E.isEmpty()) 
      {
        switch (pref(E.peek())){
          case 1:
            P.push(E.pop());
            break;
          case 3:
          case 4:
            while(pref(P.peek()) >= pref(E.peek())) 
            {
              S.push(P.pop());
            }
            P.push(E.pop());
            break; 
          case 2:
            while(!P.peek().equals("(")) 
            {
              S.push(P.pop());
            }
            P.pop();
            E.pop();
            break; 
          default:
            S.push(E.pop()); 
        } 
      }

      //Eliminacion 
      String infix = expr.replace(" ", "");
      String postfix = S.toString().replaceAll("[\\]\\[,]", "");

    
      System.out.println("Infija: " + infix);
      System.out.println("Postfija: " + postfix);

    }catch(Exception ex){ 
      System.out.println("Error de expresión");
      System.err.println(ex);
    }
  }
  private static String depurar(String s) 
  {
    s = s.replaceAll("\\s+", ""); 
    s = "(" + s + ")";
    String simbols = "+-*/()";
    String str = "";
 
    //Deja los espacios 
    for (int i = 0; i < s.length(); i++) 
    {
      if (simbols.contains("" + s.charAt(i))) 
      {
        str += " " + s.charAt(i) + " ";
      }else str += s.charAt(i);
    }
    return str.replaceAll("\\s+", " ").trim();
  }

  private static int pref(String op) {
    int prf = 99;
    if (op.equals("^")) prf = 5;
    if (op.equals("*") || op.equals("/")) prf = 4;
    if (op.equals("+") || op.equals("-")) prf = 3;
    if (op.equals(")")) prf = 2;
    if (op.equals("(")) prf = 1;
    return prf;
  }
}