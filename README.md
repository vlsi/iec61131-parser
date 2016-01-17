[![Build Status](https://travis-ci.org/vlsi/iec61131-parser.svg?branch=master)](https://travis-ci.org/vlsi/iec61131-parser)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.vlsi.iec61131/parser/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.vlsi.iec61131/parser)

IEC61131 parsers
================

About
-----
This is a ANTLRv4 grammar for IEC61131-3 languages.
The main aim is to parse ST grammar.

Usage
-----

Add maven dependency:
```xml
<dependency>
    <groupId>com.github.vlsi.iec61131</groupId>
    <artifactId>parser</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

Sample
------

```java
ANTLRInputStream is = new ANTLRInputStream("FUNCTION abc : BOOL VAR_TEMP temp: INT; END_VAR");
IEC61131Lexer lex = new IEC61131Lexer(is);
CommonTokenStream ts = new CommonTokenStream(lex);
IEC61131Parser p = new IEC61131Parser(ts);

IEC61131Parser.FunctionContext function = p.function();

Assert.assertEquals(function.name.getText(), "abc", "function name");
Assert.assertEquals(function.var_block().size(), 1, "1 block with variables");
IEC61131Parser.Var_blockContext varBlock = function.var_block(0);

Assert.assertEquals(varBlock.temp, true, "var_temp -> temp.true");
Assert.assertEquals(varBlock.variable_declaration().size(), 1, "1 variable");

IEC61131Parser.Variable_declarationContext var =
    varBlock.variable_declaration(0);
Assert.assertEquals(var.name.getText(), "temp", "variable name");
Assert.assertEquals(var.type.getText(), "INT", "variable type");
```

License
-------

This library is distributed under terms of MIT license.

Changelog
---------

v1.0.0
* Initial version

Author
------
Vladimir Sitnikov <sitnikov.vladimir@gmail.com>