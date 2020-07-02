grammar NumberTranslator;

program: assignment* EOF;

assignment: INT '-' ID;

INT : [0-9]+;
ID : [a-zA-Z]+;
WS : [ \t\r\n] -> skip;