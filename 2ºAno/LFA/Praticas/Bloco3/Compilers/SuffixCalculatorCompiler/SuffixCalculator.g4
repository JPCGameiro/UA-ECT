grammar SuffixCalculator;
program:
        stat* EOF
    ;
stat:
        expr
    ;
expr returns [String res = null]:
        e1=expr e2=expr op=('*'|'/'|'-'|'+')      #ExprSuffix
    |   Number                                    #ExprNumber
    ;
Number: [0-9]+('.'[0-9]+)?;
WS: [ \t\r\n]+ -> skip;
ERROR: .;