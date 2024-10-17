package co.edu.unimonserrate.lexer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Deque;

public final class LogicalTokenizer {
  private LogicalTokenizer() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static @NotNull Deque<Token> tokenize(final @NotNull String input) {
    final var tokens = new ArrayDeque<Token>();
    int index = 0;
    while (index < input.length()) {
      final var val = input.charAt(index);
      switch (val) {
        case '.':
          tokens.add(new Token(val, Token.Type.DOT));
          break;
        case '-':
          tokens.add(new Token(val, Token.Type.DASH));
          break;
        case ' ':
          tokens.add(new Token(val, Token.Type.SPACE));
          break;
        case '/':
          tokens.add(new Token(val, Token.Type.WORD_SEPARATOR));
          break;
        default:
          throw new IllegalArgumentException("Invalid character: " + val);
      }
      index++;
    }
    return tokens;
  }
}
