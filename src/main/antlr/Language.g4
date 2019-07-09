grammar Language;

top_level: ( lclass | lenum | function | rules )* ;

lclass: 'class' IDENTIFIER ( ':' expression )? '{' class_fields? '}' ;
class_fields: class_field+ ;
class_field: IDENTIFIER? class_statement ;
class_statement: assignment_statement | lclass | lenum | function | rules ;

lenum: 'enum' IDENTIFIER '{' enum_fields? '}' ;
enum_fields: enum_field ( ',' enum_field )* ','? ;
enum_field: IDENTIFIER ( '(' expression ')' )? ;

function: 'fn' ( '(' function_left ')' | '(' ')' )? IDENTIFIER ( '(' function_right ')' | '(' ')' )? type_specifier? '{' statements? '}' ;
function_left: expression ;
function_right: expression ;

rules: 'rules' IDENTIFIER '{' rules_fields? '}' ;
rules_fields: rules_field+ ;
rules_field: '(' expression ')' '=>' expression ';' ;

statements: statement+ ;
statement: assignment_statement | expression_statement | match_statement | whilematch_statement ;

assignment_statement: assignment_let | assignment_mut ;
assignment_let: 'let' assignment_left type_specifier? '=' assignment_right ';' ;
assignment_mut: 'mut' assignment_left type_specifier? ( '=' assignment_right )? ';' ;
assignment_left: expression ;
assignment_right: expression ;

type_specifier: ':' expression ;

expression_statement: expression ';' ;

match_statement: 'match' match_matchee '{' match_fields? '}' ;
match_matchee: expression ;
match_fields: match_field ( ',' match_field )* ','? ;
match_field: expression '=>' expression ;

whilematch_statement: 'whilematch' whilematch_matchee '{' whilematch_fields? '}' '{' statements? '}' ;
whilematch_matchee: expression ;
whilematch_fields: whilematch_field ( ',' whilematch_field )* ','? ;
whilematch_field: expression '=>' expression ;

expression: literals ( ',' literals )* ','? ;

literals: literal+ ;

literal: quote_literal | number | IDENTIFIER | CHAR_LITERAL | STRING_LITERAL ;

quote_literal: '\'{' ( quote_literal | .*? ) '}' ;

number
    : NUMBER_BINARY
    | NUMBER_OCTAL
    | NUMBER_DECIMAL
    | NUMBER_HEXIDECIMAL
    | NUMBER_FLOAT_DECIMAL
    | NUMBER_FLOAT_HEXIDECIMAL ;

NUMBER_BINARY: '0' [bB] DigitBinary+ ;
NUMBER_OCTAL: '0' [oO] DigitOctal+ ;
NUMBER_DECIMAL: DigitDecimal+ ;
NUMBER_HEXIDECIMAL: '0' [xX] DigitHexidecimal+ ;

NUMBER_FLOAT_DECIMAL
    : DigitDecimal+ '.' DigitDecimal* FloatDecimalExponent?
    | '.' DigitDecimal+ FloatDecimalExponent?
    | DigitDecimal+ FloatDecimalExponent
    ;

NUMBER_FLOAT_HEXIDECIMAL
    : '0' [xX] DigitHexidecimal+ '.' DigitHexidecimal+ FloatHexidecimalExponent?
    | '0' [xX] '.' DigitHexidecimal+ FloatHexidecimalExponent?
    | '0' [xX] DigitHexidecimal+ FloatHexidecimalExponent
    ;

IDENTIFIER: [a-zA-Z`\-=~!@#$%^&*_+\\|./<>?] [a-zA-Z0-9`\-=~!@#$%^&*_+\\|./<>?]* ;

CHAR_LITERAL
    : '\'' Character '\''
    | '\'' EscapeSequence '\''
    ;

STRING_LITERAL: '"' ( Character | EscapeSequence )* '"' ;

fragment Character: ~['\\\r\n] ;
fragment EscapeSequence
    : '\\' [btnfr"'\\]
    | '\\' DigitOctal
    | '\\' DigitOctal DigitOctal
    | '\\' [0-3] DigitOctal DigitOctal
    | '\\' 'u'+ DigitHexidecimal DigitHexidecimal DigitHexidecimal DigitHexidecimal
    ;

fragment FloatDecimalExponent: [eE] [+-]? DigitDecimal+ ;
fragment FloatHexidecimalExponent: [pP] [+-]? DigitDecimal+ ;

fragment DigitBinary: [0-1] ;
fragment DigitOctal: [0-7] ;
fragment DigitDecimal: [0-9] ;
fragment DigitHexidecimal: [0-9a-fA-F] ;

WS: [ \t\r\n]+ -> skip ;

SHEBANG: '#' '!' ~( '\n' | 'r' )* -> channel(HIDDEN) ;