import java.util.HashMap;

public class Interpreter extends SetCalcBaseVisitor<Set> {

   HashMap<String, Set> map = new HashMap<String, Set>();

   @Override public Set visitProgram(SetCalcParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Set visitStatExpr(SetCalcParser.StatExprContext ctx) {
      try{ System.out.println("result: "+visit(ctx.expr())); }
      catch(NullPointerException e) { return null; }
      return null;
   }

   @Override public Set visitStatAssign(SetCalcParser.StatAssignContext ctx) {
      return visit(ctx.assign());
   }

   @Override public Set visitStatError(SetCalcParser.StatErrorContext ctx) {
      System.out.println("ERROR: invalid expression");
      return visitChildren(ctx);
   }

   @Override public Set visitAssign(SetCalcParser.AssignContext ctx) {
      Set set = visit(ctx.expr());
      String var = ctx.VAR().getText();
      map.put(var, set);
      System.out.println(var+ " -> "+map.get(var));
      return null;
   }

   @Override public Set visitExprVar(SetCalcParser.ExprVarContext ctx) {
      String var = ctx.VAR().getText();
      if(map.containsKey(var))
         return map.get(var);
      else
         System.err.println("ERROR: variable "+var+" wasn't initialized yet");
      return new Set();
   }

   @Override public Set visitExprUnion(SetCalcParser.ExprUnionContext ctx) {
      Set op0 = visit(ctx.expr(0));
      Set op1 = visit(ctx.expr(1));
      return op0.union(op1);
   }

   @Override public Set visitExprDifference(SetCalcParser.ExprDifferenceContext ctx) {
      Set op0 = visit(ctx.expr(0));
      Set op1 = visit(ctx.expr(1));
      return op0.difference(op1);
   }

   @Override public Set visitExprIntersection(SetCalcParser.ExprIntersectionContext ctx) {
      Set op0 = visit(ctx.expr(0));
      Set op1 = visit(ctx.expr(1));
      return op0.intersection(op1);
   }

   @Override public Set visitExprset(SetCalcParser.ExprsetContext ctx) {
      return visit(ctx.set());
   }

   @Override public Set visitSetWord(SetCalcParser.SetWordContext ctx) {
      Set result = new Set();
      int i=0;
      try{
         while(true){
            result.add(ctx.WORD(i).getText());
            i++;
         }
      }
      catch(NullPointerException e) { return result; }
   }

   @Override public Set visitSetInt(SetCalcParser.SetIntContext ctx) {
         Set result = new Set();
      int i=0;
      try{
         while(true){
            result.add(ctx.INT(i).getText());
            i++;
         }
      }
      catch(NullPointerException e) { return result; }
   }
}

