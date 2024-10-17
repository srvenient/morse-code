package co.edu.unimonserrate.parser;

import co.edu.unimonserrate.lexer.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public final class ExpressionParser {
  private static final Map<String, Character> DECODING_TABLE = new HashMap<>();

  static {
    DECODING_TABLE.put(".-", 'A');
    DECODING_TABLE.put("-...", 'B');
    DECODING_TABLE.put("-.-.", 'C');
    DECODING_TABLE.put("-..", 'D');
    DECODING_TABLE.put(".", 'E');
    DECODING_TABLE.put("..-.", 'F');
    DECODING_TABLE.put("--.", 'G');
    DECODING_TABLE.put("....", 'H');
    DECODING_TABLE.put("..", 'I');
    DECODING_TABLE.put(".---", 'J');
    DECODING_TABLE.put("-.-", 'K');
    DECODING_TABLE.put(".-..", 'L');
    DECODING_TABLE.put("--", 'M');
    DECODING_TABLE.put("-.", 'N');
    DECODING_TABLE.put("---", 'O');
    DECODING_TABLE.put(".--.", 'P');
    DECODING_TABLE.put("--.-", 'Q');
    DECODING_TABLE.put(".-.", 'R');
    DECODING_TABLE.put("...", 'S');
    DECODING_TABLE.put("-", 'T');
    DECODING_TABLE.put("..-", 'U');
    DECODING_TABLE.put("...-", 'V');
    DECODING_TABLE.put(".--", 'W');
    DECODING_TABLE.put("-..-", 'X');
    DECODING_TABLE.put("-.--", 'Y');
    DECODING_TABLE.put("--..", 'Z');
    DECODING_TABLE.put("-----", '0');
    DECODING_TABLE.put(".----", '1');
    DECODING_TABLE.put("..---", '2');
    DECODING_TABLE.put("...--", '3');
    DECODING_TABLE.put("....-", '4');
    DECODING_TABLE.put(".....", '5');
    DECODING_TABLE.put("-....", '6');
    DECODING_TABLE.put("--...", '7');
    DECODING_TABLE.put("---..", '8');
    DECODING_TABLE.put("----.", '9');
    DECODING_TABLE.put(".-.-.-", '.');
    DECODING_TABLE.put("--..--", ',');
    DECODING_TABLE.put("..--..", '?');
    DECODING_TABLE.put(".----.", '\'');
    DECODING_TABLE.put("-.-.--", '!');
    DECODING_TABLE.put("-..-.", '/');
    DECODING_TABLE.put("-.--.", '(');
    DECODING_TABLE.put("-.--.-", ')');
    DECODING_TABLE.put(".-...", '&');
    DECODING_TABLE.put("---...", ':');
    DECODING_TABLE.put("-.-.-.", ';');
    DECODING_TABLE.put("-...-", '=');
    DECODING_TABLE.put(".-.-.", '+');
    DECODING_TABLE.put("-....-", '-');
    DECODING_TABLE.put("..--.-", '_');
    DECODING_TABLE.put(".-..-.", '"');
    DECODING_TABLE.put(".--.-.", '@');
  }

  private ExpressionParser() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static @NotNull String parse(final @NotNull Deque<Token> tokens) throws IllegalArgumentException {
    if (tokens.isEmpty()) {
      throw new IllegalArgumentException("Empty tokens");
    }
    final StringBuilder builder = new StringBuilder();
    while (!tokens.isEmpty()) {
      final Token token = tokens.poll();
      if (token.type() == Token.Type.SPACE) {
        if (!tokens.isEmpty() && tokens.peek().type() == Token.Type.WORD_SEPARATOR) {
          builder.append(' ');
          tokens.poll();
        }
      } else {
        final StringBuilder morse = new StringBuilder();
        morse.append(token.value());
        while (!tokens.isEmpty() && tokens.peek().type() != Token.Type.SPACE) {
          morse.append(tokens.poll().value());
        }
        final Character character = DECODING_TABLE.get(morse.toString());
        if (character == null) {
          throw new IllegalArgumentException("Invalid morse code: " + morse);
        }
        builder.append(character);
      }
    }
    return builder.toString();
  }
}
