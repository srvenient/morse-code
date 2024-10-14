package co.edu.unimonserrate.lexer;

import co.edu.unimonserrate.parser.ExpressionParser;
import org.junit.jupiter.api.Test;

public final class LogicalTokenizerTest {
  @Test
  void tokenizer() {
    final String input = ".... --- .-.. .- / .-- --- .-. .-.. -..";

    LogicalTokenizer.tokenize(input)
      .forEach(System.out::println);
  }

  @Test
  void parser() {
    final String input = ".-.. . .- .-. -. .. -. --. / .--- .- ...- .- / .. ... / ..-. ..- -.";

    final var tokes = LogicalTokenizer.tokenize(input);
    System.out.println(ExpressionParser.parse(tokes));
  }
}
