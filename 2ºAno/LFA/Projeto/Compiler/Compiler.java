import java.io.File;
import org.stringtemplate.v4.*;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.ParserRuleContext;
import types.*;


public class Compiler extends QuizMakerBaseVisitor<ST> {
   public STGroup stg = null;
   protected String target = "java"; // default
   protected int varCount = 0;

   public boolean validTarget(String target) {
      File f = new File(target+".stg");

      return ("java".equalsIgnoreCase(target) || "c".equalsIgnoreCase(target)) &&
             f.exists() && f.isFile() && f.canRead();
   }

   protected String newVarName() {
      varCount++;
      return "v"+varCount;
   }

   @Override public ST visitMain(QuizMakerParser.MainContext ctx) {
      assert validTarget(target);

      stg = new STGroupFile(target+".stg");
      ST res = stg.getInstanceOf("module");
      res.add("stat", visit(ctx.statList()));
      return res;
   }

   // stats
   @Override public ST visitStatList(QuizMakerParser.StatListContext ctx) {
      ST res = stg.getInstanceOf("stats");
      for(QuizMakerParser.StatContext sc: ctx.stat())
         res.add("stat", visit(sc));
      return res;
   }

   @Override public ST visitStatDeclaration(QuizMakerParser.StatDeclarationContext ctx) {
      return visit(ctx.declaration());
   }

   @Override public ST visitStatAssign(QuizMakerParser.StatAssignContext ctx) {
      return visit(ctx.assignment());
   }

   @Override public ST visitStatIf(QuizMakerParser.StatIfContext ctx) {
      return visit(ctx.ifStat());
   }

   @Override public ST visitStatLoop(QuizMakerParser.StatLoopContext ctx) {
      return visit(ctx.loop());
   }

   @Override public ST visitStatArrayOps(QuizMakerParser.StatArrayOpsContext ctx) {
      return visit(ctx.arrayOps());
   }

   @Override public ST visitStatDisplay(QuizMakerParser.StatDisplayContext ctx) {
      return visit(ctx.display());
   }

   @Override public ST visitStatSaveFile(QuizMakerParser.StatSaveFileContext ctx) {
      return visit(ctx.saveFile());
   }

   @Override public ST visitStatBreak(QuizMakerParser.StatBreakContext ctx) {
      return visit(ctx.breakStat());
   }

   // kuestover
   @Override public ST visitStatKuestOver(QuizMakerParser.StatKuestOverContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      res.add("text", "System.exit(0);");
      return res;
   }

   // loop
   @Override public ST visitLoopFor(QuizMakerParser.LoopForContext ctx) {
      return visit(ctx.forStat());
   }

   @Override public ST visitLoopWhile(QuizMakerParser.LoopWhileContext ctx) {
      return visit(ctx.whileStat());
   }

   @Override public ST visitLoopDo(QuizMakerParser.LoopDoContext ctx) {
      return visit(ctx.doStat());
   }

   // declaration
   @Override public ST visitDeclarationVar(QuizMakerParser.DeclarationVarContext ctx) {
      ST res = stg.getInstanceOf("declaration");
      //if(QuizMakerParser.symbolTable == null) System.out.println("tabela de simbolos é nula!");
      String id = ctx.VAR().getText();
      //System.out.println(id);
      Symbol s = QuizMakerParser.symbolTable.get(id);
      if(s == null) System.out.println(s.getName());
      s.setVarName(newVarName());
      res.add("type", s.getType().name());
      res.add("var", s.getVarName());
      return res;
   }

   @Override public ST visitDeclarationArray(QuizMakerParser.DeclarationArrayContext ctx) {
      ST res = stg.getInstanceOf("declaration_array");
      String id = ctx.VAR().getText();
      Symbol s = QuizMakerParser.symbolTable.get(id);
      s.setVarName(newVarName());
      String type_toString = s.getsubType().name();
      switch(type_toString){
         case "int":
            type_toString = "Integer";
            break;
         case "boolean":
            type_toString = "Boolean";
            break;
         default:
            break;
      }
      res.add("type", type_toString);
      res.add("var", s.getVarName());

      // -aqui, experiencia
      ST res2 = stg.getInstanceOf("common_text");
      String content = res.render();
      if(type_toString.equals("Question")) content = content.replace("ArrayList<Question>", "List<Question>");
      res2.add("text", content);
      return res2;
   }

   @Override public ST visitType(QuizMakerParser.TypeContext ctx) {
      return visitChildren(ctx);
   }

   // Values
   @Override public ST visitValueVar(QuizMakerParser.ValueVarContext ctx) {
      //System.out.println("estou no ValueVar!");
      ST res = stg.getInstanceOf("common_text");
      Symbol s = QuizMakerParser.symbolTable.get(ctx.VAR().getText());
      res.add("text", s.getVarName());
      return res;
   }

   @Override public ST visitValueArrGet(QuizMakerParser.ValueArrGetContext ctx) {
      return visit(ctx.arrget());
   }

   @Override public ST visitValueKuestGet(QuizMakerParser.ValueKuestGetContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      res.add("text", visit(ctx.kuestget()).render());
      return res;
   }

   @Override public ST visitValueChoose(QuizMakerParser.ValueChooseContext ctx) {
      return visit(ctx.choose());
   }

   @Override public ST visitValueDisplay(QuizMakerParser.ValueDisplayContext ctx) {
      return visit(ctx.display());
   }

   @Override public ST visitValueString(QuizMakerParser.ValueStringContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      res.add("text", ctx.STRING().getText());
      return res;
   }

   @Override public ST visitValueExpr(QuizMakerParser.ValueExprContext ctx) {
      return visit(ctx.expr());
   }

   @Override public ST visitValueBoolean(QuizMakerParser.ValueBooleanContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      res.add("text", ctx.BOOLEAN().getText());
      return res;
   }

   @Override public ST visitValueKuest(QuizMakerParser.ValueKuestContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      // meter quesão programada em comentário por cima
      String k = ctx.KUEST().getText();
      k = k.replace("/*", ""); k = k.replace("*/", ""); k = k.replace("\n", "");
      k = k.replace("\"", "\\\"");
      res.add("text", String.format("QuestionsReaderMain.parseSingleKuest(\"%s\")", k));
      return res;
   }

   @Override public ST visitKuestget(QuizMakerParser.KuestgetContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      Symbol s = QuizMakerParser.symbolTable.get(ctx.VAR().getText());
      String text = ctx.getType.getText();
      switch(text){
         case "dificulty":
            text = "getDificuldade()";
            break;
         case "theme":
            text = "getTema()";
            break;
         case "ID":
            text = "getId()";
            break;
         case "numberOfanswers":
            text = "numRespostas()";
            break;
         default:
            break;
      }
      res.add("text", s.getVarName()+"."+text);
      return res;
   }

   @Override public ST visitArrValues(QuizMakerParser.ArrValuesContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      String values = "";
      String sep = "";
      for(QuizMakerParser.ValuesContext val : ctx.values()){
         values += sep + visit(val).render();
         sep = "--";
      }
      res.add("text", values);
      return res;
   }

   // ArrayOps
   @Override public ST visitArrayAdd(QuizMakerParser.ArrayAddContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      Symbol s = QuizMakerParser.symbolTable.get(ctx.VAR().getText());
      String values = visit(ctx.add()).render();
      res.add("text", format_arrayAdd(values, s.getVarName()));
      return res;
   }

   @Override public ST visitArrayRemove(QuizMakerParser.ArrayRemoveContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      Symbol s = QuizMakerParser.symbolTable.get(ctx.VAR().getText());
      String values = visit(ctx.remove()).render();
      res.add("text", format_arrayRemove(values, s.getVarName()));
      return res;
   }

   @Override public ST visitArrayShuffle(QuizMakerParser.ArrayShuffleContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      Symbol s = QuizMakerParser.symbolTable.get(ctx.VAR().getText());
      res.add("text", "Collections.shuffle("+s.getVarName()+");");
      return res;
   }

   @Override public ST visitArrayClear(QuizMakerParser.ArrayClearContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      Symbol s = QuizMakerParser.symbolTable.get(ctx.VAR().getText());
      res.add("text", s.getVarName()+".clear();");
      return res;
   }

   @Override public ST visitLength(QuizMakerParser.LengthContext ctx) {
      ST res = stg.getInstanceOf("array_length");
      Symbol s = QuizMakerParser.symbolTable.get(ctx.VAR().getText());
      res.add("var", s.getVarName());
      return res;
   }

   @Override public ST visitAdd(QuizMakerParser.AddContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      String values = "";
      String sep = "";
      for(QuizMakerParser.ValuesContext val : ctx.values()){
         values += sep + visit(val).render();
         sep = "--";
      }
      res.add("text", values);
      return res;
   }

   @Override public ST visitRemove(QuizMakerParser.RemoveContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      String values = "";
      String sep = "";
      for(QuizMakerParser.ValuesContext val : ctx.values()){
         values += sep + visit(val).render();
         sep = "--";
      }
      res.add("text", values);
      return res;
   }

   @Override public ST visitArrGetInt(QuizMakerParser.ArrGetIntContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      Symbol s = QuizMakerParser.symbolTable.get(ctx.VAR().getText());
      res.add("text", s.getVarName()+".get("+ctx.INT().getText()+")");
      return res;
   }


   @Override public ST visitArrGetVar(QuizMakerParser.ArrGetVarContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      Symbol s1 = QuizMakerParser.symbolTable.get(ctx.VAR(0).getText());
      Symbol s2 = QuizMakerParser.symbolTable.get(ctx.VAR(1).getText());
      res.add("text", s1.getVarName()+".get("+s2.getVarName()+")");
      return res;
   }

   @Override public ST visitChoose(QuizMakerParser.ChooseContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      String filters = "";
      for(QuizMakerParser.FilterFieldsContext filter : ctx.filterFields()){
         filters += visit(filter).render();
      }
      res.add("text", "memory.stream()"+filters);

      if(ctx.quantidade != null){
         int quantidade = Integer.parseInt(ctx.quantidade.getText());
         res.add("text", ".limit("+quantidade+")");
      } 
      res.add("text", ".collect(Collectors.toList())");
      return res;
   }

   @Override public ST visitFilterTheme(QuizMakerParser.FilterThemeContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      String cond = ctx.cond.getText();
      if(cond.equals("=")) cond = "";
      if(cond.equals("!=")) cond = "!";
      String filters = "";
      String sep = "";
      for(QuizMakerParser.ValuesContext val : ctx.values()){
         String value = visit(val).render();
         if(value.contains("\"")){  // é uma STRING
            filters += sep + "q.getTema().equals("+value+")";
         }else{   // é uma variável
            //System.out.println(value);
            //Symbol s = QuizMakerParser.symbolTable.get(value);
            //if(s == null) System.out.println("s é nulo!");
            filters += sep + value+".contains(q.getTema())";
         }
         sep = "||";
      }
      // String filters = "";
      // String sep = "";
      // for(QuizMakerParser.ValuesContext val : ctx.values()){
      //    filters += sep + "q.getTema().equals("+visit(val).render()+")";
      //    sep = " || ";
      // }
      res.add("text", ".filter(q -> "+cond+"("+filters+"))");
      return res;
   }

   @Override public ST visitFilterID(QuizMakerParser.FilterIDContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      String cond = ctx.cond.getText();
      if(cond.equals("=")) cond = "";
      if(cond.equals("!=")) cond = "!";
      String filters = "";
      String sep = "";
      for(QuizMakerParser.ValuesContext val : ctx.values()){
         String value = visit(val).render();
         if(value.contains("\"")){  // é uma STRING
            filters += sep + "q.getId().equals("+value+")";
         }else{   // é uma variável
            //System.out.println(value);
            //Symbol s = QuizMakerParser.symbolTable.get(value);
            //if(s == null) System.out.println("s é nulo!");
            filters += sep + value+".contains(q.getId())";
         }
         sep = "||";
      }
      //    filters += sep + "q.getId().equals("+visit(val).render()+")";
      //    sep = " || ";
      // }
      res.add("text", ".filter(q -> "+cond+"("+filters+"))");
      return res;
   }

   @Override public ST visitFilterDificulty(QuizMakerParser.FilterDificultyContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      String cond = ctx.cond.getText();
      if(cond.equals("=")) cond = "==";
      String filter = "";
      String sep = "";
      for(TerminalNode t : ctx.INT()){
         filter += sep + "q.getDificuldade()"+cond+t.getText();
         sep = " || ";
      }
      res.add("text", ".filter(q -> "+filter+")");
      return res;
   }

   @Override public ST visitFilterNumOfAnswers(QuizMakerParser.FilterNumOfAnswersContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      String cond = ctx.cond.getText();
      if(cond.equals("=")) cond = "==";
      String filter = "";
      String sep = "";
      for(TerminalNode t : ctx.INT()){
         filter += sep + "q.getDificuldade()"+cond+t.getText();
         sep = " || ";
      }
      res.add("text", ".filter(q -> "+filter+")");
      return res;
   }

   // Assignment
   @Override public ST visitAssignmentArrayDeclaration(QuizMakerParser.AssignmentArrayDeclarationContext ctx) {
      ST res = stg.getInstanceOf("assignment_array");
      Symbol s = QuizMakerParser.symbolTable.get(ctx.declaration().var);
      res.add("stat", visit(ctx.declaration()).render());
      String values = visit(ctx.arrayValues()).render();
      res.add("value", format_arrayAdd(values, s.getVarName()));
      return res;
   }

   @Override public ST visitAssignmentDeclaration(QuizMakerParser.AssignmentDeclarationContext ctx) {
      ST res = stg.getInstanceOf("assignment");
      Symbol s = QuizMakerParser.symbolTable.get(ctx.declaration().var);
      res.add("stat", visit(ctx.declaration()).render());
      res.add("var", s.getVarName());
      // experiencia
      String value = visit(ctx.values()).render();
      if(value.contains("ki = new KuestInterface(") || value.contains("while(!ki.respondeu())System.out.print(\"\")")){
         res.add("stat", value);
         res.add("value", "ki.points();");
      }else{
         res.add("value", value);
      }
      return res;
   }

   @Override public ST visitAssignmentArrayVar(QuizMakerParser.AssignmentArrayVarContext ctx) {
      ST res = stg.getInstanceOf("assignment_array");
      Symbol s = QuizMakerParser.symbolTable.get(ctx.VAR().getText());
      res.add("stat", "");
      String values = visit(ctx.arrayValues()).render();
      res.add("value", format_arrayAdd(values, s.getVarName()));
      return res;
   }

   @Override public ST visitAssignmentVar(QuizMakerParser.AssignmentVarContext ctx) {
      ST res = stg.getInstanceOf("assignment");
      Symbol s = QuizMakerParser.symbolTable.get(ctx.VAR().getText());
      res.add("stat", "");
      res.add("var", s.getVarName());
      // experiencia
      String value = visit(ctx.values()).render();
      if(value.contains("ki = new KuestInterface(") || value.contains("while(!ki.respondeu())System.out.print(\"\")")){
         res.add("stat", value);
         res.add("value", "ki.points();");

      }else if(value.contains(".collect(Collectors.toList())")){
         res.add("stat", "aux = "+ value+";"+"\naux.addAll("+s.getVarName()+");");
         res.add("value", "aux");
      }else{
         res.add("value", value);
      }
      return res;
   }

   @Override public ST visitAssignmentIncrementDecrement(QuizMakerParser.AssignmentIncrementDecrementContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      Symbol s = QuizMakerParser.symbolTable.get(ctx.VAR().getText());
      res.add("text", s.getVarName()+ctx.op.getText()+";");
      return res;
   }

   // Exprs
   @Override public ST visitExprVar(QuizMakerParser.ExprVarContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      Symbol s = QuizMakerParser.symbolTable.get(ctx.VAR().getText());
      if(s == null){
         res.add("text", "esta_var_é_nula!");
      }else{
         res.add("text", s.getVarName());
      }

      return res;
   }

   @Override public ST visitExprAddSub(QuizMakerParser.ExprAddSubContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      res.add("text", visit(ctx.e0).render()+ctx.op.getText()+visit(ctx.e1).render());
      return res;
   }

   @Override public ST visitExprArrGet(QuizMakerParser.ExprArrGetContext ctx) {
      return visit(ctx.arrget());
   }

   @Override public ST visitExprParent(QuizMakerParser.ExprParentContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      res.add("text", "("+visit(ctx.expr()).render()+")");
      return res;
   }

   @Override public ST visitExprLength(QuizMakerParser.ExprLengthContext ctx) {
      //System.out.println("estou no ExprLength!");
      return visit(ctx.length());
   }

   @Override public ST visitExprRandom(QuizMakerParser.ExprRandomContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      ST res1 = visit(ctx.expr(0));
      ST res2 = visit(ctx.expr(1));
      res.add("text", "(int) (Math.random()*(("+res1.render()+")-("+res2.render()+"))+"+res1.render()+")");
      return res;
   }

   @Override public ST visitExprNumber(QuizMakerParser.ExprNumberContext ctx) {
      // terá que ser mudado acho
      ST res = stg.getInstanceOf("common_text");
      res.add("text", ctx.INT().getText());
      return res;
   }

   @Override public ST visitExprKuestGet(QuizMakerParser.ExprKuestGetContext ctx) {
      return visit(ctx.kuestget());
   }

   @Override public ST visitExprSignal(QuizMakerParser.ExprSignalContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      String signal = ctx.signal.getText();
      ST expr = visit(ctx.expr());
      if(signal.equals("-")){
         res.add("text", "-"+expr.render());
      }else{
         res.add("text", expr.render());
      }
      return res;
   }

   @Override public ST visitExprMultDivMod(QuizMakerParser.ExprMultDivModContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      res.add("text", visit(ctx.e0).render()+ctx.op.getText()+visit(ctx.e1).render());
      return res;
   }

   @Override public ST visitCondEquals(QuizMakerParser.CondEqualsContext ctx) {
      //System.out.println("estou no condEquals!");
      ST res = stg.getInstanceOf("common_text");
      String op = ctx.op.getText();
      if(op.equals("!=")) op = "!";
      if(op.equals("=")) op = "";
      String v0 = visit(ctx.v0).render();
      String v1 = visit(ctx.v1).render();
      boolean v0_string = false;
      boolean v1_string = false;

      try {
         int v0i = Integer.parseInt(v0);
      } catch (NumberFormatException e) {
         v0_string = true;
      }

      try {
         int v1i = Integer.parseInt(v1);
      } catch (NumberFormatException e) {
         v1_string = true;
      }

      if(v0_string && v1_string && !(v0.equals("true")) && !v0.equals("false") && !v1.equals("true") && !v1.equals("false")){   // comparação feita com equals
         res.add("text", op+"("+v0+" "+".equals("+" "+v1+"))");
         return res;
      }
      // comparação feita com "==", tipos primitivos
      if(op.equals("!")) op = "!=";
      if(op.equals("")) op = "==";

      res.add("text", "("+v0+" "+op+" "+v1+")");
      return res;
   }

   @Override public ST visitCondLogical(QuizMakerParser.CondLogicalContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      String logical = ctx.LOGICAL().getText();
      if(logical.equals("and")) logical = "&&";
      else logical = "|";
      res.add("text", visit(ctx.c0).render()+" "+logical+" "+visit(ctx.c1).render());
      return res;
   }

   @Override public ST visitCondParent(QuizMakerParser.CondParentContext ctx) {
      return visit(ctx.condition());
   }

   @Override public ST visitCondValues(QuizMakerParser.CondValuesContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      res.add("text", visit(ctx.e0).render()+" "+ctx.COMPARE().getText()+" "+visit(ctx.e1).render());
      return res;
   }

   // if stat
   @Override public ST visitIfStat(QuizMakerParser.IfStatContext ctx) {
      QuizMakerParser.symbolTable.UpContext();
      ST res = stg.getInstanceOf("conditional");
      res.add("cond_true", visit(ctx.condition()).render());
      res.add("stat_true", visit(ctx.s0).render());
      QuizMakerParser.symbolTable.DownContext();
      for(QuizMakerParser.ElseIfStatContext elseif : ctx.elseIfStat()){
         res.add("elseif_struct", visit(elseif).render());
      }
      if(ctx.el1 != null){
         res.add("stat_false", visit(ctx.el1).render());
      }
      return res;
   }

   @Override public ST visitElseIfStat(QuizMakerParser.ElseIfStatContext ctx) {
      QuizMakerParser.symbolTable.UpContext();
      ST res = stg.getInstanceOf("conditional_elseif");
      res.add("cond", visit(ctx.condition()).render());
      res.add("stat", visit(ctx.s0));
      QuizMakerParser.symbolTable.DownContext();
      return res;
   }

   @Override public ST visitElseStat(QuizMakerParser.ElseStatContext ctx) {
      QuizMakerParser.symbolTable.UpContext();
      ST res = visit(ctx.s0);
      QuizMakerParser.symbolTable.DownContext();
      return res;
   }

   // while stat
   @Override public ST visitWhileStat(QuizMakerParser.WhileStatContext ctx) {
      QuizMakerParser.symbolTable.UpContext();
      ST res = stg.getInstanceOf("while");
      res.add("cond", visit(ctx.condition()).render());
      res.add("stat", visit(ctx.statList()).render());
      QuizMakerParser.symbolTable.DownContext();
      return res;
   }

   @Override public ST visitDoStat(QuizMakerParser.DoStatContext ctx) {
      QuizMakerParser.symbolTable.UpContext();
      ST res = stg.getInstanceOf("do_while");
      res.add("cond", visit(ctx.condition()).render());
      res.add("stat", visit(ctx.statList()).render());
      QuizMakerParser.symbolTable.DownContext();
      return res;
   }

   // for stat
   @Override public ST visitForStat(QuizMakerParser.ForStatContext ctx) {
      QuizMakerParser.symbolTable.UpContext();
      ST res = stg.getInstanceOf("for");
      res.add("header", visit(ctx.forHeader()).render());
      res.add("statList", visit(ctx.statList()).render());
      QuizMakerParser.symbolTable.DownContext();
      return res;
   }

    @Override public ST visitForHeaderInterval(QuizMakerParser.ForHeaderIntervalContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      visit(ctx.declaration());
      Symbol s = QuizMakerParser.symbolTable.get(ctx.declaration().var);
      String forHeader = "";
      String begin = "("+visit(ctx.i0).render()+")";
      String end = "("+visit(ctx.i1).render()+")";
      if(ctx.p0.getText().equals("]")){
         begin += "+1";
      }
      if(ctx.p1.getText().equals("]")){
         end += "+1";
      }
      forHeader = "int "+s.getVarName()+"="+begin+"; "+s.getVarName()+" < "+end+";"+s.getVarName()+"++";
      res.add("text", forHeader);
      return res;

   }

   // @Override public ST visitForHeaderStat(QuizMakerParser.ForHeaderStatContext ctx) {
   //    ST res = stg.getInstanceOf("common_text");
   //    if(ctx.declaration != null){  // é uma declaração
   //       res.add("text", visit(ctx.declaration()).render());
   //    }else{
   //       ST res = stg.getInstanceOf("common_text");
   //       Symbol s = QuizMakerParser.symbolTable.get(ctx.VAR().getText());
   //       res.add("text", s.getVarName());
   //    }
   //    return res;
   // }



   @Override public ST visitForHeaderVar(QuizMakerParser.ForHeaderVarContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      String forHeader = "";
      String decl = visit(ctx.declaration()).render();
      decl = decl.replace(";", "");
      forHeader = decl + ": "+visit(ctx.values()).render();
      res.add("text", forHeader);
      return res;
   }

   @Override public ST visitBreakStat(QuizMakerParser.BreakStatContext ctx) {
      ST res = stg.getInstanceOf("common_text");
      res.add("text", "break;\n");
      return res;
   }

   // display

   @Override public ST visitDisplay(QuizMakerParser.DisplayContext ctx) {
      ST res = stg.getInstanceOf("display");
      String type = "mc";     // default display type
      if(ctx.op != null){
         type = ctx.op.getText();
         if(type.equals("tf")){
            type = "vf";
         }
      }
      String time = null;
      if(ctx.e0 != null){
         time = visit(ctx.e0).render();
      }
      res.add("question_object", visit(ctx.v0).render());
      res.add("display_type", String.format("\"%s\"", type));
      if(time != null){
         res.add("display_time", time);
      }
      return res;
   }
   // savefile

   @Override public ST visitSaveFile(QuizMakerParser.SaveFileContext ctx) {
      ST res = stg.getInstanceOf("savefile");
      res.add("stat", "int i = 0; \nwhile(i < 100000000){i++;}");
      if(ctx.v0 != null){
         res.add("filename", visit(ctx.v0).render());
      }else{
         res.add("filename", "\"report\"");
      }
      return res;
   }

   // funções auxiliares
   private String format_arrayAdd(String values, String var_name){
      String[] value_array = values.split("--");
      String val = "";
      for(String g : value_array){
         val += var_name+".add("+g+");\n";
      }
      return val;
   }

   private String format_arrayRemove(String values, String var_name){
      String[] value_array = values.split("--");
      String val = "";
      for(String g : value_array){
         val += var_name+".remove("+g+");\n";
      }
      return val;
   }
}