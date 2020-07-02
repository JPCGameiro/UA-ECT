grammar SetCalc;

program: stat* EOF
    ;

stat:   expr? NEWLINE         #StatExpr
    |   assign? NEWLINE       #StatAssign
    |   .? NEWLINE            #StatError
    ;

assign: VAR '=' expr
    ;

expr:   expr '\\' expr      #ExprDifference
    |   expr '&' expr       #ExprIntersection
    |   expr '+' expr       #ExprUnion
    |   set                 #Exprset
    |   VAR                 #ExprVar
    ;

set:    '{' WORD (',' WORD)*? '}'      #SetWord
    |   '{' INT (',' INT)*? '}'        #SetInt
    ;
    
WORD: [a-z]+;
VAR: [A-Z]+;
INT: [0-9]+;
NEWLINE: ('\r'? '\n') | COMMENT;
COMMENT: '--' .*? '\n' -> skip;
WS: [ \t]+ -> skip;
