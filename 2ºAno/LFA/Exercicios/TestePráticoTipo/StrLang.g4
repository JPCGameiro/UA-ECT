grammar StrLang;

main: (stat)* EOF;

stat:   print 
    |   declaration
    ;

print:  'print' (STRING|VAR|op)
    ;

declaration: 
        VAR ':' STRING      #DeclarationString
    |   VAR ':' input       #DeclarationInput
    ;

input:  'input' '('STRING')'
    ;

op: '(' op '/' op '/' STRING ')' #OpReplace
    |    op '+' op               #OpAdd
    |   STRING                   #OpString
    |   VAR                      #OpVar
    ;

VAR: (INT|TXT)+;
STRING: '"' .*? '"';
INT: [0-9]+;
TXT: [a-zA-Z]+;
LINECOMMENT: '//' .*? '\n' -> skip;
WS: [ \t\r\n]+ -> skip;
ERROR: .;
