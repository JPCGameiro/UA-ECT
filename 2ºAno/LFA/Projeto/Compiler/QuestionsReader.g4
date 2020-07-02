grammar QuestionsReader;

program: stat
    ;

stat: question+ 
    ;
//A gramática está construida de modo a que  quando fizeres o visitQuestion seja devolvido uma Question
//ou seja o visitor é suposto devolver questions ao longo da árvore
question: IDENTIFIER '-' IDENTIFIER '-' INT '[' STRING ']' '{' answer* '}'
    ;

answer:		(STRING '-' INT ';')
	;

STRING:  '"'.*? '"';
INT: [0-9]+;
IDENTIFIER: ([a-zA-Z] | [0-9])+;
WS: [ \t\n]+ -> skip;
COMMENT: '//' .*? '\n' -> skip;
ERROR: .;
