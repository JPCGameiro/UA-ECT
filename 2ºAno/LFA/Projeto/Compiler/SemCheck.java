import types.*;
import java.util.Stack;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

public class SemCheck extends QuizMakerBaseVisitor<Boolean> {

   private final IntegerType integerType = new IntegerType();
   private final BooleanType booleanType = new BooleanType();
   private final StringType stringType = new StringType();
   private final QuestionType questionType = new QuestionType();
   private final ArrayType arrayType = new ArrayType();

   private boolean saveFileAppeared = false;

   //verificar tipo numérico
   private Boolean checkNumericType(ParserRuleContext ctx, Type t) {
      Boolean res = true;
      if (!t.isNumeric()) {
         ErrorHandling.printError(ctx, "Numeric operator applied to a non-numeric operand!");
         res = false;
      }
      return res;
   }

   //verificar se é um array
   private Boolean checkIsArray(ParserRuleContext ctx, String id){
      Boolean res = true;
      if (!QuizMakerParser.symbolTable.contains(id)) {
         ErrorHandling.printError(ctx, "Variable \""+id+"\" does not exists!");
         res = false;
      }
      else {
         Symbol sym = QuizMakerParser.symbolTable.get(id);
         if (!sym.isDefined()) {
            ErrorHandling.printError(ctx, "Variable \""+id+"\" not defined!");
            res = false;
         }
         else{
            if(sym.getsubType() == null){
               ErrorHandling.printError(ctx, "Method can only be applied to arrays and \""+id+"\" is not an array!");
               res = false;
            }
         }
      }
      return res;
   }

   //verificar se uma variável existe e foi inicializada
   public boolean checkVariable(ParserRuleContext ctx, String id){
      Boolean res = true;
      if (!QuizMakerParser.symbolTable.contains(id)) {
         ErrorHandling.printError(ctx, "Variable \""+id+"\" does not exists!");
         res = false;
      }
      else {
         Symbol sym = QuizMakerParser.symbolTable.get(id);
         if (!sym.isDefined()) {
            ErrorHandling.printError(ctx, "Variable \""+id+"\" not defined!");
            res = false;
         }
      }
      return res;     
   } 


//Statements--------------------------------------------------
   @Override public Boolean visitStatList(QuizMakerParser.StatListContext ctx) {
      int i=0, check=0;
      Boolean res = true;
      //se o contexto é um ciclo
      if(QuizMakerParser.symbolTable.getCurrentContext().inCycle()){
         while(ctx.stat(i)!=null){
            visit(ctx.stat(i));     //se o contexto tem um break
            if(QuizMakerParser.symbolTable.getCurrentContext().hasBreak()){
               check++;
               if(check>1){      //se aparecer alguma instrução depois do break
                  ErrorHandling.printError(ctx.stat(i), "Unreachable Statement!");
                  return false;
               }
            }
            i++;
         }
      }
      else
         return visitChildren(ctx);
      return res;
   }

//Declarations---------------------------------------------------
   @Override public Boolean visitDeclarationVar(QuizMakerParser.DeclarationVarContext ctx) {
      Boolean res = true;
      String var = ctx.VAR().getText();
      if (QuizMakerParser.symbolTable.contains(var)) {
         ErrorHandling.printError(ctx, "Variable \""+var+"\" already declared!");
         res = false;
      }
      else{
         QuizMakerParser.symbolTable.put(var, new VariableSymbol(ctx.type().res, var));
      }
      ctx.var = var;
      return res;
   }

   @Override public Boolean visitDeclarationArray(QuizMakerParser.DeclarationArrayContext ctx) {
     Boolean res = true;
      String var = ctx.VAR().getText();
      if (QuizMakerParser.symbolTable.contains(var)) {
            ErrorHandling.printError(ctx, "Variable \""+var+"\" already declared!");
         res = false;
      }
      else{
         VariableSymbol sb = new VariableSymbol(arrayType, var);
         sb.setsubType(ctx.type().res);
         QuizMakerParser.symbolTable.put(var, sb);
      }
      ctx.var = var;
      return res;
   }

//values-----------------------------------------------------------
   @Override public Boolean visitValueVar(QuizMakerParser.ValueVarContext ctx) {
      Boolean res = checkVariable(ctx, ctx.VAR().getText());
      if(res){
         Symbol sym = QuizMakerParser.symbolTable.get(ctx.VAR().getText());
         ctx.t = sym.getType();
         ctx.s = sym.getsubType();
      }
      return res;
   }

   @Override public Boolean visitValueArrGet(QuizMakerParser.ValueArrGetContext ctx) {
      Boolean res = visit(ctx.arrget());
      if(res)
         ctx.t = ctx.arrget().vType;
      return res;
   }

   @Override public Boolean visitValueKuestGet(QuizMakerParser.ValueKuestGetContext ctx) {
      Boolean res = visit(ctx.kuestget());
      if(res)
         ctx.t = ctx.kuestget().t;
      return res;
   }

   @Override public Boolean visitValueChoose(QuizMakerParser.ValueChooseContext ctx) {
      Boolean res = visit(ctx.choose());
      if(res) {
         ctx.t = arrayType;
         ctx.s = questionType;
      }
      return res;
   }

   @Override public Boolean visitValueDisplay(QuizMakerParser.ValueDisplayContext ctx) {
      Boolean res = visit(ctx.display());
      if(res)
         ctx.t = integerType;
      return res;
   }

   @Override public Boolean visitValueString(QuizMakerParser.ValueStringContext ctx) {
      ctx.t = stringType;
      return true;
   }

   @Override public Boolean visitValueExpr(QuizMakerParser.ValueExprContext ctx) {
      visit(ctx.expr());
      ctx.t = ctx.expr().eType;
      return true;
   }

   @Override public Boolean visitValueBoolean(QuizMakerParser.ValueBooleanContext ctx) {
      ctx.t = booleanType;
      return true;
   }

   @Override public Boolean visitValueKuest(QuizMakerParser.ValueKuestContext ctx) {
      String aux = ctx.KUEST().getText();
      int check = 0;
      for(int i=0;i<aux.length();i++){
         char charaux = aux.charAt(i);
         if(charaux == '}')
            check++;
      }
      if(check!=1){
         ErrorHandling.printError(ctx, "More than one question assigned two one variable!");
         return false;
      }
      ctx.t = questionType;
      return true;
   }

   @Override public Boolean visitArrValues(QuizMakerParser.ArrValuesContext ctx) {
      Boolean res = visit(ctx.values(0));
      Type tp = ctx.values(0).t;
      int i = 1;
      try{
         while(res){
            res = visit(ctx.values(i));
            if(!tp.conformsTo(ctx.values(i).t)){
               ErrorHandling.printError(ctx, "Incoherent types defined in array values!");
               res = false;
            }
            i++;
         }
         return res;
      }
      catch(NullPointerException e){
         res = true;
      }
      ctx.arrType = tp;
      return res;
   }

//Kuest Get-----------------------------------------------------
   @Override public Boolean visitKuestget(QuizMakerParser.KuestgetContext ctx) {
      String id = ctx.VAR().getText();
      String tp = ctx.getType.getText();
      Boolean res = checkVariable(ctx, id);

      if(res){
         Symbol sym = QuizMakerParser.symbolTable.get(id);
         if(!questionType.conformsTo(sym.getType())){
            ErrorHandling.printError(ctx, "Method get cannot be applied to variable \""+id+"\" type!");
            res = false;
         }
         else{
            switch(tp){
               case "dificulty": case "numberOfanswers":
                  ctx.t = integerType;
                  break;
               default:
                  ctx.t = stringType;
                  break;
            }
         }
      }
      return res;
   }


//Array Operations-----------------------------------------------------
   @Override public Boolean visitArrayAdd(QuizMakerParser.ArrayAddContext ctx) {
      String var = ctx.VAR().getText();
      Boolean res = visit(ctx.add());

      if (res) {
         if (!QuizMakerParser.symbolTable.contains(var)) {
            ErrorHandling.printError(ctx, "Variable \""+var+"\" does not exists!");
            res = false;
         }
         else {
            Symbol sym = QuizMakerParser.symbolTable.get(var);
            if(sym.getsubType()==null){
               ErrorHandling.printError(ctx, "Method can only be applied to arrays and \""+var+"\" is not an array!");
               res = false;
            }
            else if(ctx.add().vType!=null && !ctx.add().vType.conformsTo(sym.getsubType())) {
               ErrorHandling.printError(ctx, "Expression type does not conform to variable \""+var+"\" type!");
               res = false;
            }
            else
               sym.setValueDefined();
         }
      }     
      return res;
   }

   @Override public Boolean visitArrayRemove(QuizMakerParser.ArrayRemoveContext ctx) {
      String var = ctx.VAR().getText();
      Boolean res = visit(ctx.remove());

      if (res) {
         if (!QuizMakerParser.symbolTable.contains(var)) {
            ErrorHandling.printError(ctx, "Variable \""+var+"\" does not exists!");
            res = false;
         }
         else {
            Symbol sym = QuizMakerParser.symbolTable.get(var);
            if(sym.getsubType()==null){
               ErrorHandling.printError(ctx, "Method can only be applied to arrays and \""+var+"\" is not an array!");
               res = false;
            }
            else if(ctx.remove().vType!=null && !ctx.remove().vType.conformsTo(sym.getsubType())) {
               ErrorHandling.printError(ctx, "Expression type does not conform to variable \""+var+"\" type!");
               res = false;
            }
            else
               sym.setValueDefined();
         }
      }     
      return res;
   }

   @Override public Boolean visitArrayShuffle(QuizMakerParser.ArrayShuffleContext ctx) {
      return checkIsArray(ctx, ctx.VAR().getText());
   }

   @Override public Boolean visitArrayClear(QuizMakerParser.ArrayClearContext ctx) {
      return checkIsArray(ctx, ctx.VAR().getText());
   }

   @Override public Boolean visitLength(QuizMakerParser.LengthContext ctx) {
      return checkIsArray(ctx, ctx.VAR().getText());
   }

   @Override public Boolean visitAdd(QuizMakerParser.AddContext ctx) {
      Boolean res = visit(ctx.values(0));
      Type tp = ctx.values(0).t;
      int i = 1;
      while(res && ctx.values(i)!=null){
         res = visit(ctx.values(i));
         if(ctx.values(i).t!=null && !tp.conformsTo(ctx.values(i).t)){
            ErrorHandling.printError(ctx, "Incoherent types defined in array values!");
            res = false;
         }
        i++;
      }
      ctx.vType = tp;
      return res;
   }

   @Override public Boolean visitRemove(QuizMakerParser.RemoveContext ctx) {
      Boolean res = visit(ctx.values(0));
      Type tp = ctx.values(0).t;
      int i = 1;
      while(res && ctx.values(i)!=null){
         res = visit(ctx.values(i));
         if(ctx.values(i).t!=null && !tp.conformsTo(ctx.values(i).t)){
            ErrorHandling.printError(ctx, "Incoherent types defined in array values!");
            res = false;
         }
        i++;
      }
      ctx.vType = tp;
      return res;
   }

   @Override public Boolean visitArrGetInt(QuizMakerParser.ArrGetIntContext ctx) {
      Boolean res = checkIsArray(ctx, ctx.VAR().getText());
      if(res){
         Symbol sym = QuizMakerParser.symbolTable.get(ctx.VAR().getText());
         ctx.vType = sym.getsubType();
      }
      return res;
   }

   @Override public Boolean visitArrGetVar(QuizMakerParser.ArrGetVarContext ctx) {
      String id = ctx.VAR(0).getText();
      String id0 = ctx.VAR(1).getText();

      Boolean res = checkIsArray(ctx, id);
      if(res){
         Symbol sym = QuizMakerParser.symbolTable.get(id);
         ctx.vType = sym.getsubType();
         if (!QuizMakerParser.symbolTable.contains(id0)) {
            ErrorHandling.printError(ctx, "Variable \""+id0+"\" does not exists!");
            res = false;
         }
         else {
            Symbol sym0 = QuizMakerParser.symbolTable.get(id0);
            if (!sym0.isDefined()) {
               ErrorHandling.printError(ctx, "Variable \""+id0+"\" not defined!");
               res = false;
            }
            if(sym0.getType().isNumeric())
               ctx.vType = sym.getsubType();
            else{
               ErrorHandling.printError(ctx, "Variable type is invalid for method get!");
               res = false;
            }
         }
      }
      return res;
   }

//Choose-------------------------------------------------------------
   @Override public Boolean visitChoose(QuizMakerParser.ChooseContext ctx) {
      Stack<String> filtersUsed = new Stack<String>();
      Boolean res = true;

      if(ctx.quantidade!=null && Integer.parseInt(ctx.quantidade.getText())==0){
         ErrorHandling.printError(ctx, "Cannot choose 0 questions from memory!");
         res=false;
      }

      if(ctx.filterFields(0)!=null && res){
         if(visit(ctx.filterFields(0)))
            filtersUsed.push(ctx.filterFields(0).filter);
         int i=1;
         while(ctx.filterFields(i)!=null){
            if(visit(ctx.filterFields(i))){
               if(filtersUsed.search(ctx.filterFields(i).filter) != -1){
                  ErrorHandling.printError(ctx, "Filter "+ctx.filterFields(i).filter+" was already applied!");
                  res = false;
                  break;
               }
               else
                  filtersUsed.push(ctx.filterFields(i).filter);
            }
            i++;
         }
      }
      filtersUsed.clear();
      return res;
   }

//Filter------------------------------------------------------------
   @Override public Boolean visitFilterTheme(QuizMakerParser.FilterThemeContext ctx) {
      Boolean res = visit(ctx.values(0));
      int i=1;
      if(res){
         if(!stringType.conformsTo(ctx.values(0).t)){
            if((ctx.values(0).s!=null && !stringType.conformsTo(ctx.values(0).s)) || ctx.values(0).s==null){
               ErrorHandling.printError(ctx, "Invalid type, theme must be string or array string!");
               res = false;
            }
         }
         if(res){
            while(ctx.values(i)!=null){
               res = visit(ctx.values(i));
               if(res){
                  if(!stringType.conformsTo(ctx.values(i).t)){
                     if((ctx.values(i).s!=null && !stringType.conformsTo(ctx.values(i).s)) || ctx.values(i).s==null){
                        ErrorHandling.printError(ctx, "Invalid type, theme must be string or array string!");
                        res = false;
                        break;
                     }
                  }
               }
               i++;
            }
         }
      }
      ctx.filter = "theme";
      return res;
   }

   @Override public Boolean visitFilterID(QuizMakerParser.FilterIDContext ctx) {
      Boolean res = visit(ctx.values(0));
      int i=1;
      if(res){
         if(!stringType.conformsTo(ctx.values(0).t)){
            if((ctx.values(0).s!=null && !stringType.conformsTo(ctx.values(0).s)) || ctx.values(0).s==null){
               ErrorHandling.printError(ctx, "Invalid type, ID must be string or array string!");
               res = false;
            }
         }
         if(res){
            while(ctx.values(i)!=null){
               res = visit(ctx.values(i));
               if(res){
                  if(!stringType.conformsTo(ctx.values(i).t)){
                     if((ctx.values(i).s!=null && !stringType.conformsTo(ctx.values(i).s)) || ctx.values(i).s==null){
                        ErrorHandling.printError(ctx, "Invalid type, ID must be string or array string!");
                        res = false;
                        break;
                     }
                  }
               }
               i++;
            }
         }
      }
      ctx.filter = "ID";
      return res;
   }
   @Override public Boolean visitFilterDificulty(QuizMakerParser.FilterDificultyContext ctx) {
      Boolean res = true;
      int i=1;
      int aux = Integer.parseInt(ctx.INT(0).getText());
      if(aux <= 0 || aux >5){
         ErrorHandling.printError(ctx, "Dificulty must be in [1, 5]!");
         res = false;
      }
      else{
         while(ctx.INT(i)!=null){
            if(res){
               aux = Integer.parseInt(ctx.INT(i).getText());
               if(aux <= 0 || aux >5){
                  ErrorHandling.printError(ctx, "Dificulty must be in [1, 5]!");
                  res = false;
                  break;
               }
            }
            i++;
         }
      }
      ctx.filter = "dificulty";
      return res;
   }

   @Override public Boolean visitFilterNumOfAnswers(QuizMakerParser.FilterNumOfAnswersContext ctx) {
      Boolean res = true;
      int i=1;
      if(Integer.parseInt(ctx.INT(0).getText()) <= 0){
         ErrorHandling.printError(ctx, "Number of Answers must be greater than 0!");
         res = false;
      }
      else{
         while(ctx.INT(i)!=null){
            if(Integer.parseInt(ctx.INT(i).getText()) <= 0){
               ErrorHandling.printError(ctx, "Number of Answers must be bigger than 0!");
               res = false;
               break;
            }
            i++;
         }
      }
      ctx.filter = "numberOfanswers";
      return res;
   }

//Assignments-----------------------------------------------------------------------
   @Override public Boolean visitAssignmentArrayDeclaration(QuizMakerParser.AssignmentArrayDeclarationContext ctx) {
      Boolean res = visit(ctx.arrayValues());

      Boolean res0 = visit(ctx.declaration()); 
      String var = ctx.declaration().var;

      if (res && res0) {
         if (!QuizMakerParser.symbolTable.contains(var)) {
            ErrorHandling.printError(ctx, "Variable \""+var+"\" does not exists!");
            res = false;
         }
         else {
            Symbol sym = QuizMakerParser.symbolTable.get(var);
            if (ctx.arrayValues().arrType!=null && !ctx.arrayValues().arrType.conformsTo(sym.getsubType())) {
               ErrorHandling.printError(ctx, "Expression type does not conform to variable \""+var+"\" type!");
               res = false;
            }
            else
               sym.setValueDefined();
         }
      }     
      return res;
   }

   @Override public Boolean visitAssignmentDeclaration(QuizMakerParser.AssignmentDeclarationContext ctx) {
      Boolean res = visit(ctx.values());

      Boolean res0 = visit(ctx.declaration()); 
      String var = ctx.declaration().var;

      if (res && res0) {
         if (!QuizMakerParser.symbolTable.contains(var)) {
            ErrorHandling.printError(ctx, "Variable \""+var+"\" does not exists!");
            res = false;
         }
         else {
            Symbol sym = QuizMakerParser.symbolTable.get(var);
            if (ctx.values().t!=null && !ctx.values().t.conformsTo(sym.getType())) {
               ErrorHandling.printError(ctx, "Expression type does not conform to variable \""+var+"\" type!");
               res = false;
            }
            else{
               if(ctx.values().s!=null && !ctx.values().s.conformsTo(sym.getsubType())){
                  ErrorHandling.printError(ctx, "Expression type does not conform to variable \""+var+"\" type!");
                  res = false;
               }
               else
                  sym.setValueDefined();         
            }
         }
      }     
      return res;
   }

   @Override public Boolean visitAssignmentArrayVar(QuizMakerParser.AssignmentArrayVarContext ctx) {
      String var = ctx.VAR().getText();
      Boolean res = visit(ctx.arrayValues());

      if (res) {
         if (!QuizMakerParser.symbolTable.contains(var)) {
            ErrorHandling.printError(ctx, "Variable \""+var+"\" does not exists!");
            res = false;
         }
         else {
            Symbol sym = QuizMakerParser.symbolTable.get(var);
            if (ctx.arrayValues().arrType!=null && !ctx.arrayValues().arrType.conformsTo(sym.getsubType())) {
               ErrorHandling.printError(ctx, "Expression type does not conform to variable \""+var+"\" type!");
               res = false;
            }
            else
               sym.setValueDefined();
         }
      }     
      return res;
   }

   @Override public Boolean visitAssignmentVar(QuizMakerParser.AssignmentVarContext ctx) {
      String var = ctx.VAR().getText();
      Boolean res = visit(ctx.values());

      if (res) {
         if (!QuizMakerParser.symbolTable.contains(var)) {
            ErrorHandling.printError(ctx, "Variable \""+var+"\" does not exists!");
            res = false;
         }
         else {
            Symbol sym = QuizMakerParser.symbolTable.get(var);
            if (ctx.values().t!=null && !ctx.values().t.conformsTo(sym.getType())) {
               ErrorHandling.printError(ctx, "Expression type does not conform to variable \""+var+"\" type!");
               res = false;
            }
            else{
               if(ctx.values().s!=null && !ctx.values().s.conformsTo(sym.getsubType())){
                  ErrorHandling.printError(ctx, "Expression type does not conform to variable \""+var+"\" type!");
                  res = false;
               }
               else
                  sym.setValueDefined(); 
            }
         }
      }     
      return res;
   }

   @Override public Boolean visitAssignmentIncrementDecrement(QuizMakerParser.AssignmentIncrementDecrementContext ctx) {
      String id = ctx.VAR().getText();
      Boolean res = checkVariable(ctx, id);
      if(res){
         Symbol sym = QuizMakerParser.symbolTable.get(id);
         if(!integerType.conformsTo(sym.getType())){
            ErrorHandling.printError(ctx, "Expression type does not conform to variable \""+id+"\" type!");
            res = false;
         }
      }
      return res;
   }

//EXPR--------------------------------------------------------------------
   @Override public Boolean visitExprVar(QuizMakerParser.ExprVarContext ctx) {
      String id = ctx.VAR().getText();
      Boolean res = checkVariable(ctx, id);
      if(res) {
         Symbol sym = QuizMakerParser.symbolTable.get(id);
         ctx.eType = sym.getType();
      }
      return res;
   }

   @Override public Boolean visitExprAddSub(QuizMakerParser.ExprAddSubContext ctx) {
      Boolean res = visit(ctx.e0) && checkNumericType(ctx, ctx.e0.eType) &&
                    visit(ctx.e1) && checkNumericType(ctx, ctx.e1.eType);
      if (res)
         ctx.eType = integerType;
      return res;
   }

   @Override public Boolean visitExprArrGet(QuizMakerParser.ExprArrGetContext ctx) {
      Boolean res = visit(ctx.arrget()) && checkNumericType(ctx, ctx.arrget().vType);
      if(res){
         ctx.eType = integerType;
      }
      return res;
   }

   @Override public Boolean visitExprParent(QuizMakerParser.ExprParentContext ctx) {
      Boolean res = visit(ctx.expr());
      if (res)
         ctx.eType = ctx.expr().eType;
      return res;
   }

   @Override public Boolean visitExprLength(QuizMakerParser.ExprLengthContext ctx) {
      Boolean res = visit(ctx.length());
      if(res)
         ctx.eType = integerType;
      return res;
   }

   @Override public Boolean visitExprNumber(QuizMakerParser.ExprNumberContext ctx) {
      ctx.eType = integerType;
      return true;
   }

   @Override public Boolean visitExprKuestGet(QuizMakerParser.ExprKuestGetContext ctx) {
      Boolean res = visit(ctx.kuestget());
      if(res)
         ctx.eType = ctx.kuestget().t;
      return res;
   }
   @Override public Boolean visitExprSignal(QuizMakerParser.ExprSignalContext ctx) {
      Boolean res = visit(ctx.expr()) && checkNumericType(ctx, ctx.expr().eType);
      if (res)
         ctx.eType = ctx.expr().eType;
      return res;
   }

   @Override public Boolean visitExprRandom(QuizMakerParser.ExprRandomContext ctx) {
      Boolean res = visit(ctx.expr(0)) && checkNumericType(ctx, ctx.expr(0).eType);
      if(res) res = visit(ctx.expr(1)) && checkNumericType(ctx, ctx.expr(1).eType);
      if(res) ctx.eType = ctx.expr(1).eType;
      return res;
   }

   @Override public Boolean visitExprMultDivMod(QuizMakerParser.ExprMultDivModContext ctx) {
      Boolean res = visit(ctx.e0) && checkNumericType(ctx, ctx.e0.eType) &&
                    visit(ctx.e1) && checkNumericType(ctx, ctx.e1.eType);
      if (res)
         ctx.eType = integerType;
      return res;
   }



//Conditionals----------------------------------------------------------------
   @Override public Boolean visitCondLogical(QuizMakerParser.CondLogicalContext ctx) {
      return (visit(ctx.c0) && visit(ctx.c1));
   }

   @Override public Boolean visitCondEquals(QuizMakerParser.CondEqualsContext ctx) {
      Boolean res = visit(ctx.v0) && visit(ctx.v1);
      if(res){
         if(ctx.v0.s!=null || ctx.v1.s!=null){
            ErrorHandling.printError(ctx, "Arrays cannot be compared!");
            return false;
         }
         if(ctx.v0.t!=null && ctx.v1.t!=null &&!ctx.v0.t.conformsTo(ctx.v1.t)){
            ErrorHandling.printError(ctx, "Inconsistent types in operation equals!");
            return false;
         }
      }
      return true;
   }

   @Override public Boolean visitCondParent(QuizMakerParser.CondParentContext ctx) {
      return visit(ctx.condition());
         
   }

   @Override public Boolean visitCondValues(QuizMakerParser.CondValuesContext ctx) {
      Boolean res = (visit(ctx.e0) && visit(ctx.e1)) && 
                           (checkNumericType(ctx, ctx.e0.eType) && checkNumericType(ctx, ctx.e1.eType));
      return res;
   }

//Conditional Statements-----------------------------------------------
   @Override public Boolean visitIfStat(QuizMakerParser.IfStatContext ctx) {
      QuizMakerParser.symbolTable.goUpContext();

      Boolean res = visit(ctx.condition());
      int i=0;
      visit(ctx.s0);

      QuizMakerParser.symbolTable.goDownContext();

      if(res){
         while(ctx.elseIfStat(i)!=null && res){
            res=visit(ctx.elseIfStat(i));
            i++;
         }
         if(ctx.elseStat()!=null && res)
            visit(ctx.elseStat());
      }

      return res;
   }

   @Override public Boolean visitElseIfStat(QuizMakerParser.ElseIfStatContext ctx) {
      QuizMakerParser.symbolTable.goUpContext();
      
      Boolean res = visit(ctx.condition());
      if(res)
         visit(ctx.s0);

      QuizMakerParser.symbolTable.goDownContext();
      return res;
   }

   @Override public Boolean visitElseStat(QuizMakerParser.ElseStatContext ctx) {
      QuizMakerParser.symbolTable.goUpContext();
      Boolean res = visit(ctx.s0);
      QuizMakerParser.symbolTable.goDownContext();

      return res;
   }



//LOOPS----------------------------------------------------------------------
   @Override public Boolean visitWhileStat(QuizMakerParser.WhileStatContext ctx) {
      Boolean res = visit(ctx.condition());
      QuizMakerParser.symbolTable.goUpContext();
      QuizMakerParser.symbolTable.getCurrentContext().setInCycle(true);

      if(res) res = visit(ctx.statList());

      QuizMakerParser.symbolTable.getCurrentContext().setHasBreak(false);
      QuizMakerParser.symbolTable.goDownContext();
      return res;
   }

   @Override public Boolean visitDoStat(QuizMakerParser.DoStatContext ctx) {
      QuizMakerParser.symbolTable.goUpContext();
      QuizMakerParser.symbolTable.getCurrentContext().setInCycle(true);

      Boolean res = visit(ctx.statList());

      QuizMakerParser.symbolTable.getCurrentContext().setHasBreak(false);
      QuizMakerParser.symbolTable.goDownContext();
      if(res) visit(ctx.condition());
      return res;
   }

//For (Stat e header)----------------------------------------------------
   @Override public Boolean visitForStat(QuizMakerParser.ForStatContext ctx) {
      QuizMakerParser.symbolTable.goUpContext();
      QuizMakerParser.symbolTable.getCurrentContext().setInCycle(true);

      Boolean res = visit(ctx.forHeader());
      if(res) visit(ctx.statList());

      QuizMakerParser.symbolTable.getCurrentContext().setHasBreak(false);
      QuizMakerParser.symbolTable.goDownContext();
      return res;
   }

   @Override public Boolean visitForHeaderInterval(QuizMakerParser.ForHeaderIntervalContext ctx) {
      Boolean res = visit(ctx.declaration());
      String id = ctx.declaration().var;

      if(res){
         Symbol sym = QuizMakerParser.symbolTable.get(id);
         if(!integerType.conformsTo(sym.getType())){  //verificar se a variável é tipo numérico
            ErrorHandling.printError(ctx, "Variable \""+id+"\" is not numeric!");
            res = false;
         }
         else{    //visitar primeiro values definidos no intervalo
            res = visit(ctx.values(0));
            sym.setValueDefined();
         } 
         if(res){
            if(!integerType.conformsTo(ctx.values(0).t)){   //verificação se os valores dos intervalos são numéricos
               ErrorHandling.printError(ctx, "Values in interval must be integer type!");
               res = false;
            }
            if(res){    //visitar segundo values definido no intervalo
               res = visit(ctx.values(1));
               if(res){
                  if(!integerType.conformsTo(ctx.values(1).t)){ //verificação se os valores dos intervalos são numéricos
                     ErrorHandling.printError(ctx, "Values in interval must be integer type!");
                     res = false;
                  }
               }
            }
         }
      }

      return res;
   }

   @Override public Boolean visitForHeaderVar(QuizMakerParser.ForHeaderVarContext ctx) {
      Boolean res = visit(ctx.declaration());
      String id = ctx.declaration().var;

      if(res){
         res = visit(ctx.values());
         if(res){
            Symbol sym = QuizMakerParser.symbolTable.get(id);
            sym.setValueDefined();
            if(ctx.values().s==null){
               ErrorHandling.printError(ctx, "Iteration is only possible over arrays in for Statement!");
               return false;
            }
            else if(!ctx.values().s.conformsTo(sym.getType())){
               ErrorHandling.printError(ctx, "Variable \""+id+"\" type does not conform with expression type!");
               return false;
            }
         }
      }
      return res;
   }

   @Override public Boolean visitBreakStat(QuizMakerParser.BreakStatContext ctx) {
      if(!QuizMakerParser.symbolTable.getCurrentContext().inCycle()){
         ErrorHandling.printError(ctx, "Invalid context, break can only be use in cycles!");
         return false;
      }
      QuizMakerParser.symbolTable.getCurrentContext().setHasBreak(true);
      return true;
   }

//DISPLAY & SAVEFILE-------------------------------------------------------
   @Override public Boolean visitDisplay(QuizMakerParser.DisplayContext ctx) {
      Boolean res = visit(ctx.values());
      if(res){
         if(!questionType.conformsTo(ctx.values().t)){
            ErrorHandling.printError(ctx, "Variable must be kuest type to be displayed!");
            res = false;
         }
      }
      if(res){
         if(ctx.e0!=null){
            res = visit(ctx.e0);
            if(!checkNumericType(ctx, ctx.e0.eType)){
               ErrorHandling.printError(ctx, "Time for display must be numeric!");
               res = false;
            }
         }
      }
      return res;
   }

   @Override public Boolean visitSaveFile(QuizMakerParser.SaveFileContext ctx) {
      Boolean res = true;
      if(saveFileAppeared){
         ErrorHandling.printError(ctx, "saveFile can only appear once in a program!");
         res = false;
      }
      else{
         if(ctx.v0!=null){
            res = visit(ctx.v0);
            if(!stringType.conformsTo(ctx.v0.t)){
               ErrorHandling.printError(ctx, "saveFile argument must be a string!");
               res = false;
            }
         }
      }
      saveFileAppeared = true;
      return res;
   }
}


