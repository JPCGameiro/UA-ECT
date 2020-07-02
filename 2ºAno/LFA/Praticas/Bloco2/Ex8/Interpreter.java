import java.util.HashMap;
import java.util.Scanner;

public class Interpreter extends FractionsCalculatorBaseVisitor<Fractions> {

   HashMap<String, Fractions> map = new HashMap<String, Fractions>();

   @Override public Fractions visitProgram(FractionsCalculatorParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Fractions visitStat(FractionsCalculatorParser.StatContext ctx) {
      try{ visit(ctx.expr()); }
      catch(NullPointerException e) {
         try{ visit(ctx.print());}
         catch(NullPointerException e0) { 
            try{ visit(ctx.assignment()); }
            catch(NullPointerException e1){ }
         }
      }
      return null;
   }

   @Override public Fractions visitAssignment(FractionsCalculatorParser.AssignmentContext ctx) {
      map.put(ctx.ID().getText(), visit(ctx.expr()));
      return null;
   }

   @Override public Fractions visitPrint(FractionsCalculatorParser.PrintContext ctx) {
      Fractions result = visit(ctx.expr());
      if(result!=null)
         System.out.println(result);
      return null;
   }

   @Override public Fractions visitReduce(FractionsCalculatorParser.ReduceContext ctx) {
      return visit(ctx.expr()).reduce();
   }

   @Override public Fractions visitRead(FractionsCalculatorParser.ReadContext ctx) {
      Scanner ler = new Scanner(System.in);
      System.out.print(ctx.ID().getText()+": ");
      String fraction = ler.next();
      try{
         String aux[] = fraction.split("/");
         return new Fractions(Integer.parseInt(aux[0]), Integer.parseInt(aux[1]));
      }
      catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {
         System.err.println("ERROR: invalid sintax (insert <number></><number>)");
         return null;
      }
   }

   @Override public Fractions visitExprAddSub(FractionsCalculatorParser.ExprAddSubContext ctx) {
      Fractions op0 = visit(ctx.expr(0));
      Fractions op1 = visit(ctx.expr(1));
      String op = ctx.op.getText();
      Fractions result = new Fractions(1);
      switch(op){
         case "+":
            result = op0.sum(op1);   //op0+op1
            break;
         case "-":
            result = op0.sub(op1);   //op0-op1
            break;
      }
      return result;
   }

   @Override public Fractions visitExprRead(FractionsCalculatorParser.ExprReadContext ctx) {
      return visit(ctx.read());
   }

   @Override public Fractions visitExprParent(FractionsCalculatorParser.ExprParentContext ctx) {
      String signal0 = "+";
      try { signal0 = ctx.signal0.getText(); }
      catch(NullPointerException e) { signal0 = "+"; }
      Fractions aux = visit(ctx.expr());

      String signal1;
      try { signal1 = ctx.signal1.getText(); }
      catch(NullPointerException e) { signal1 = "+"; }

      int exp;
      try{ exp = Integer.parseInt(ctx.INT().getText()); }
      catch(NullPointerException e) { exp = 1; }

      if(signal1.equals("-"))
         exp = exp*-1;


      if(signal0.equals("-"))
         return new Fractions(aux.numerator()*-1, aux.denominator()).exp(exp);
      else
         return aux.exp(exp);
   }

   @Override public Fractions visitExprInt(FractionsCalculatorParser.ExprIntContext ctx) {
      return new Fractions(Integer.parseInt(ctx.INT().getText()));
   }

   @Override public Fractions visitExprMultDiv(FractionsCalculatorParser.ExprMultDivContext ctx) {
      Fractions op0 = visit(ctx.expr(0));
      Fractions op1 = visit(ctx.expr(1));
      String op = ctx.op.getText();
      Fractions result = new Fractions(1);
      switch(op){
         case "*":
            result = op0.mult(op1);  //op0*op1
            break;
         case ":":
            result = op0.div(op1);   //op0/op1
            break;
      }
      return result;
   }

   @Override public Fractions visitExprReduce(FractionsCalculatorParser.ExprReduceContext ctx) {
      return visit(ctx.reduce());
   }

   @Override public Fractions visitExprId(FractionsCalculatorParser.ExprIdContext ctx) {
      Fractions result = null;
      if(map.containsKey(ctx.ID().getText()))
         result = map.get(ctx.ID().getText());
      else
         System.err.println("ERROR: uninitialed variable");
      return result;
   }

   @Override public Fractions visitExprFr(FractionsCalculatorParser.ExprFrContext ctx) {
      String signal;
      try { signal = ctx.signal.getText(); }
      catch(NullPointerException e) { signal = "+"; }
      int aux = 1;
      if(signal.equals('-'))
         aux = -1;
      return new Fractions(Integer.parseInt(ctx.INT(0).getText())*aux, Integer.parseInt(ctx.INT(1).getText()));
   }
}

