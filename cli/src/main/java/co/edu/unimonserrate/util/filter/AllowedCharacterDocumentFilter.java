package co.edu.unimonserrate.util.filter;

import co.edu.unimonserrate.util.sound.SoundReproduce;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class AllowedCharacterDocumentFilter extends DocumentFilter {
  @Override
  public void insertString(final @NotNull FilterBypass fb, final int offset, final @NotNull String string, final @NotNull AttributeSet attr) throws BadLocationException {
    if (this.isAllowed(string)) {
      this.playSound(string);
      super.insertString(fb, offset, string, attr);
    }
  }

  @Override
  public void replace(final @NotNull FilterBypass fb, final int offset, final int length, final @NotNull String text, final @NotNull AttributeSet attrs) throws BadLocationException {
    if (this.isAllowed(text)) {
      this.playSound(text);
      super.replace(fb, offset, length, text, attrs);
    }
  }

  private void playSound(String text) {
    for (char c : text.toCharArray()) {
      if (c == '.' || c == '-') {
        try {
          SoundReproduce.reproduce(c);
        } catch (final LineUnavailableException | InterruptedException e) {
          throw new IllegalStateException("Client: An error occurred while reproducing the sound", e);
        }
      }
    }
  }

  private boolean isAllowed(final @NotNull String text) {
    for (char c : text.toCharArray()) {
      if (c != '.' && c != '-' && c != ' ' && c != '/') {
        return false;
      }
    }
    return true;
  }
}
