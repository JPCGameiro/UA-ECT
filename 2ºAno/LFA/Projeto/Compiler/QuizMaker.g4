grammar QuizMaker;

@parser::header {
import types.*;
}
@parser::members {
static protected ContextStack symbolTable = new ContextStack(); 
}
//Parser
main: statList EOF
    ;

//Statements
statList: (stat)* 
    ;
stat:   declaration ';'         #StatDeclaration
    |   assignment ';'          #StatAssign
    |   ifStat                  #StatIf
    |   loop                    #StatLoop
    |   arrayOps ';'            #StatArrayOps 
    |   display ';'             #StatDisplay
    |   saveFile ';'            #StatSaveFile
    |   breakStat ';'           #StatBreak
    |   'kuestover' ';'         #StatKuestOver   
    ;
loop:   forStat         #LoopFor
    |   whileStat       #LoopWhile
    |   doStat          #LoopDo
    ;


//Declarations e tipos
declaration returns[String var]:
        type VAR                    #DeclarationVar
    |   'array' type VAR            #DeclarationArray
    ;
type returns[Type res]:   
        'string'    {$res = new StringType();}
    |   'int'       {$res = new IntegerType();}
    |   'boolean'   {$res = new BooleanType();}
    |   'kuest'     {$res = new QuestionType();}
    ;
values returns[Type t, Type s]:
        VAR             #ValueVar
    |   arrget          #ValueArrGet
    |   kuestget        #ValueKuestGet
    |   choose          #ValueChoose
    |   display         #ValueDisplay
    |   STRING          #ValueString
    |   expr            #ValueExpr
    |   BOOLEAN         #ValueBoolean
    |   KUEST           #ValueKuest
    ;
    
//Método para o tipo kuest
kuestget returns [Type t]: 
        VAR '.get(' getType=('dificulty'|'theme'|'ID'|'numberOfanswers') ')'
    ;


//Arrays e as suas operações
arrayValues returns[Type arrType]:   
        '{' (values) (','(values))* '}' #ArrValues                 
    ;
arrayOps:   VAR add                #ArrayAdd
    |       VAR remove             #ArrayRemove
    |       VAR '.shuffle'         #ArrayShuffle
    |       VAR '.clear'           #ArrayClear
    ;
length: VAR'.length'
    ;
add returns [Type vType]:'.add(' values (',' values)* ')'
    ;
remove returns [Type vType]:'.remove(' values (',' values)* ')'
    ;
arrget returns [Type vType]: 
        VAR'['INT']'        #ArrGetInt
    |   VAR '['VAR']'       #ArrGetVar    
    ; 



//Escolha de perguntas da memória
choose: 'choose' (quantidade=INT)? 'from''memory' '('(filterFields (',' filterFields)*)?')'
    ;
filterFields returns [String filter]:
         'theme' cond=EQUALS (values) ('OR' (values))*                       #FilterTheme
    |    'ID' cond=EQUALS (values) ('OR' (values))*                          #FilterID
    |    'dificulty' cond=(EQUALS|COMPARE) (INT) ('OR' (INT))*               #FilterDificulty
    |    'numberOfanswers'cond=(EQUALS|COMPARE) (INT) ('OR' (INT))*          #FilterNumOfAnswers     
    ;



//Assignments
assignment: declaration '->' arrayValues  #AssignmentArrayDeclaration 
    |   declaration '->' values           #AssignmentDeclaration   
    |   VAR '->' arrayValues              #AssignmentArrayVar    
    |   VAR '->' values                   #AssignmentVar
    |   VAR op=('++'|'--')                #AssignmentIncrementDecrement
    ;


//Operações aritméticas
expr returns[Type eType,String varName]:   
        signal=('-'|'+') expr                 #ExprSignal
    |   e0=expr op=('/'|'*'|'%') e1=expr      #ExprMultDivMod
    |   e0=expr op=('+'|'-') e1=expr          #ExprAddSub
    |   '(' expr ')'                          #ExprParent
    |   length                                #ExprLength
    |   arrget                                #ExprArrGet
    |   kuestget                              #ExprKuestGet
    |   'random('expr ','expr')'              #ExprRandom
    |   VAR                                   #ExprVar 
    |   INT                                   #ExprNumber  
    ;

//Instruções condicionais
condition:  c0=condition LOGICAL c1=condition               #CondLogical
    |       v0=values op=EQUALS v1=values                   #CondEquals
    |       e0=expr COMPARE e1=expr                         #CondValues   
    |       '(' condition ')'                               #CondParent  
    ;
ifStat: 'if' condition '{' s0=statList '}' (el0=elseIfStat)* (el1=elseStat)?
    ;
elseIfStat: 'elseif' condition '{' s0=statList '}'
    ;
elseStat: 'else' '{'s0=statList'}'
    ;


//Ciclos
whileStat: 'while' condition '{' statList '}'
    ;
doStat: 'do' '{' statList '}' 'while' condition
    ;
forStat: forHeader '{' statList '}' 
    ;
forHeader:  
        'for' (declaration) 'in' p0=('['|']') i0=values ',' i1=values p1=('['|']')  #ForHeaderInterval
    |   'for' (declaration) 'in'  values                                            #ForHeaderVar
    ;
breakStat:  'break'
    ;

//diplay
display:    'display(' v0=values (',' op=('mc'|'tf'|'txt'))? (',' e0=expr)? ')'
    ;

//saveFile
saveFile:   'saveFile(' (v0=values)?')'
    ;


//Lexer
BOOLEAN: 'true' | 'false';
LOGICAL:  'or' | 'and';
EQUALS: '=' | '!=';
COMPARE: '<' | '>' |'<=' | '>='; 
VAR: [a-zA-Z][a-zA-Z_0-9]*;
KUEST: '/*'.*? '*/';
STRING:  '"'.*? '"';
INT: [0-9]+;
WS: [ \t\r\n]+ -> skip;
COMMENT: '#' .*? '\n' -> skip;
ERROR: .;