package co.edu.unimonserrate.encoding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides utility methods to encode and decode morse code.
 */
public final class MorseCodec {
  /**
   * The encoding table for morse code.
   */
  private static final Map<Character, String> ENCODING_TABLE = new HashMap<>();

  static {
    ENCODING_TABLE.put('A', ".-");
    ENCODING_TABLE.put('B', "-...");
    ENCODING_TABLE.put('C', "-.-.");
    ENCODING_TABLE.put('D', "-..");
    ENCODING_TABLE.put('E', ".");
    ENCODING_TABLE.put('F', "..-.");
    ENCODING_TABLE.put('G', "--.");
    ENCODING_TABLE.put('H', "....");
    ENCODING_TABLE.put('I', "..");
    ENCODING_TABLE.put('J', ".---");
    ENCODING_TABLE.put('K', "-.-");
    ENCODING_TABLE.put('L', ".-..");
    ENCODING_TABLE.put('M', "--");
    ENCODING_TABLE.put('N', "-.");
    ENCODING_TABLE.put('O', "---");
    ENCODING_TABLE.put('P', ".--.");
    ENCODING_TABLE.put('Q', "--.-");
    ENCODING_TABLE.put('R', ".-.");
    ENCODING_TABLE.put('S', "...");
    ENCODING_TABLE.put('T', "-");
    ENCODING_TABLE.put('U', "..-");
    ENCODING_TABLE.put('V', "...-");
    ENCODING_TABLE.put('W', ".--");
    ENCODING_TABLE.put('X', "-..-");
    ENCODING_TABLE.put('Y', "-.--");
    ENCODING_TABLE.put('Z', "--..");
    ENCODING_TABLE.put('0', "-----");
    ENCODING_TABLE.put('1', ".----");
    ENCODING_TABLE.put('2', "..---");
    ENCODING_TABLE.put('3', "...--");
    ENCODING_TABLE.put('4', "....-");
    ENCODING_TABLE.put('5', ".....");
    ENCODING_TABLE.put('6', "-....");
    ENCODING_TABLE.put('7', "--...");
    ENCODING_TABLE.put('8', "---..");
    ENCODING_TABLE.put('9', "----.");
  }

  private MorseCodec() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Encode a text string into a morse code string.
   *
   * @param text the text string to encode
   * @return {@code null} if the text string is empty, otherwise the encoded morse code string
   */
  public static @Nullable String encode(final @NotNull String text) {
    if (text.isEmpty()) {
      return null;
    }

    final StringBuilder morse = new StringBuilder();
    for (final char c : text.toUpperCase().toCharArray()) {
      if (Character.isWhitespace(c)) {
        morse.append("/ ");
        continue;
      }

      final String code = ENCODING_TABLE.get(c);
      if (code != null) {
        morse.append(code).append(" ");
      }
    }
    return morse.toString()
      .trim();
  }

  /**
   * Decode a morse code string into a text string.
   *
   * @param morse the morse code string to decode
   * @return {@code null} if the morse code string is empty, otherwise the decoded text string
   */
  public static @Nullable String decode(final @NotNull String morse) {
    if (morse.isEmpty()) {
      return null;
    }

    final StringBuilder text = new StringBuilder();
    for (final String code : morse.split(" ")) {
      for (final Map.Entry<Character, String> entry : ENCODING_TABLE.entrySet()) {
        if (code.equals("/")) {
          text.append(" ");
          break;
        }

        if (entry.getValue().equals(code)) {
          text.append(entry.getKey());
          break;
        }
      }
    }
    return text.toString();
  }
}
