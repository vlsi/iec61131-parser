package com.github.vlsi.iec61131.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParserTest {
  @Test
  public void simple() {
    IEC61131Parser p = getIec61131Parser("FUNCTION abc : BOOL VAR_TEMP temp: INT; END_VAR");
    IEC61131Parser.FunctionContext function = p.function();

    Assert.assertEquals(function.name.getText(), "abc", "function name");
    Assert.assertEquals(function.var_block().size(), 1, "1 block with variables");
    IEC61131Parser.Var_blockContext varBlock = function.var_block(0);

    Assert.assertEquals(varBlock.temp, true, "var_temp -> temp.true");
    Assert.assertEquals(varBlock.variable_declaration().size(), 1, "1 variable");

    IEC61131Parser.Variable_declarationContext var =
        varBlock.variable_declaration(0);
    Assert.assertEquals(var.names.size(), 1, "there should be single variable");
    Assert.assertEquals(var.names.get(0).getText(), "temp", "variable name");
    Assert.assertEquals(var.type.getText(), "INT", "variable type");
  }

  @Test
  public void arrayVariable() {
    IEC61131Parser p = getIec61131Parser("temp: ARRAY[1..2, 4..6] OF BOOL;");
    IEC61131Parser.Variable_declarationContext var = p.variable_declaration();
    Assert.assertEquals(var.type.getClass(), IEC61131Parser.ArrayTypeContext.class,
        "type should be array");
    IEC61131Parser.ArrayTypeContext ary = (IEC61131Parser.ArrayTypeContext) var.type;
    Assert.assertEquals(ary.array.ranges.size(), 2, "1..2, 4..6 -> two ranges");
  }

  private IEC61131Parser getIec61131Parser(String input) {
    ANTLRInputStream is = new ANTLRInputStream(input);
    IEC61131Lexer lex = new IEC61131Lexer(is);
    CommonTokenStream ts = new CommonTokenStream(lex);
    return new IEC61131Parser(ts);
  }
}
