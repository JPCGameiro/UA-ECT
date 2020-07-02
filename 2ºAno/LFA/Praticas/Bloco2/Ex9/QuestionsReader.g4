grammar QuestionsReader;

program: stat* EOF
    ;

stat:   question+ 
    ;

question:  IDENTIFIER '.' IDENTIFIER '(' STRING ')' '{' (STRING ':' INT ';')+ '}'
    ;

STRING: '"' .*? '"';
INT: [0-9]+;
IDENTIFIER: ([a-zA-Z] | [0-9])+;
WS: [ \t\n]+ -> skip;
COMMENT: '#' .*? '\n' -> skip;