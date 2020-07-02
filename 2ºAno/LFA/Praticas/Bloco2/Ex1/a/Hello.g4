grammar Hello;
greetings : 'hello' ID;
ID : [a-z]+;
WS  : [ \t\r\n]+ -> skip;