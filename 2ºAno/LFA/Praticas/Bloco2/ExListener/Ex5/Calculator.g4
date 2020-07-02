grammar Calculator;

program:
        stat * EOF
    ;
stat:
        expr? NEWLINE
    |   assignment? NEWLINE
    ;
assignment returns [String result = null]: ID '=' expr
    ;
expr returns [Double result = null]:
        expr op=('*'|'/'|'%') expr      #ExprMultDivMod
    |   expr op=('+'|'-') expr          #ExprAddSub
    |   signal=('+'|'-')? Integer       #ExprInteger
    |   signal=('+'|'-')? '(' expr ')'  #ExprParent
    |   ID                              #ExprId
    ;

ID: [a-zA-Z_]+;
Integer:    [0-9]+;
NEWLINE: '\r'? '\n';
WS: [ \t]+ -> skip;
COMMENT: '#' .*? '\n' -> skip;