public class Interpreter extends SuffixCalculatorBaseVisitor<Double> {

   @Override public Double visitProgram(SuffixCalculatorParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Double visitStat(SuffixCalculatorParser.StatContext ctx) {
      System.out.println("result: "+visit(ctx.expr()));
      return null;
   }

   @Override public Double visitExprNumber(SuffixCalculatorParser.ExprNumberContext ctx) {
      double result = Double.parseDouble(ctx.getText());
      return result;
   }

   @Override public Double visitExprSuffix(SuffixCalculatorParser.ExprSuffixContext ctx) {
      double op0 = visit(ctx.expr(0));
      double op1 = visit(ctx.expr(1));
      String op = ctx.op.getText();
      double result=0;
      
      switch(op){
         case "+":
            result = op0+op1;
            break;
         case "-":
            result = op0-op1;
            break;
         case "/":
            result = op0/op1;
            break;
         case "*":
            result = op0*op1;
            break;
         default:
            result = 0;
            break;
      }
      return result;
   }
}
