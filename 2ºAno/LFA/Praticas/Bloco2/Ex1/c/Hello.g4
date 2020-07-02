grammar Hello;
top : greetings | bye;
greetings   : 'hello' ID;
bye         : 'goodbye' ID;
ID : [a-z]+;
WS  : [ \t\r\n]+ -> skip;