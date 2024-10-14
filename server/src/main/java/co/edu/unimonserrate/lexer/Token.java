package co.edu.unimonserrate.lexer;

import org.jetbrains.annotations.NotNull;

public record Token(char value, @NotNull Token.Type type) {
  @Override
  public String toString() {
    return this.value + " " + this.type;
  }

  public enum Type {
    DOT,
    DASH,
    SPACE,
    WORD_SEPARATOR;
  }
}
