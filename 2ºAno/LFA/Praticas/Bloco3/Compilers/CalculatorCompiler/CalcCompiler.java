import org.stringtemplate.v4.*;
import java.util.Iterator;

public class CalcCompiler extends CalculatorBaseVisitor<ST> {

   private int numVars = 0;
   private STGroup templates = new STGroupFile("java.stg");
   private String newVar() { numVars++; return "v"+numVars; }

   @Override public ST visitProgram(CalculatorParser.ProgramContext ctx) {
      ST result = templates.getInstanceOf("module");
      Iterator<CalculatorParser.StatContext> list = ctx.stat().iterator();
      while(list.hasNext()){
         result.add("stat", visit(list.next()).render());
      }
      return result;
   }

   @Override public ST visitStat(CalculatorParser.StatContext ctx) {
      ST result = templates.getInstanceOf("print");
      result.add("stat", visit(ctx.expr()).render());
      result.add("value", ctx.expr().var);
      return result;
   }

   @Override public ST visitExprAddSub(CalculatorParser.ExprAddSubContext ctx) {
      ST result = templates.getInstanceOf("binaryOperation");
      ctx.var = newVar();
      result.add("stat", visit(ctx.e1).render());
      result.add("stat", visit(ctx.e2).render());
      result.add("type", "double");
      result.add("var", ctx.var);
      result.add("e1", ctx.e1.var);
      result.add("op", ctx.op.getText());
      result.add("e2", ctx.e2.var);
      return result;
   }

   @Override public ST visitExprDivMult(CalculatorParser.ExprDivMultContext ctx) {
      ST result = templates.getInstanceOf("binaryOperation");
      ctx.var = newVar();
      result.add("stat", visit(ctx.e1).render());
      result.add("stat", visit(ctx.e2).render());
      result.add("type", "double");
      result.add("var", ctx.var);
      result.add("e1", ctx.e1.var);
      result.add("op", ctx.op.getText());
      result.add("e2", ctx.e2.var);
      return result;
   }

   @Override public ST visitExprParent(CalculatorParser.ExprParentContext ctx) {
      ST result = visit(ctx.expr());
      ctx.var = ctx.expr().var;
      return result;
   }

   @Override public ST visitExprNumber(CalculatorParser.ExprNumberContext ctx) {
      ST result = templates.getInstanceOf("decl");
      ctx.var = newVar();
      result.add("type", "double");
      result.add("var", ctx.var);
      result.add("value", ctx.Number().getText());
      return result;
   }
}
