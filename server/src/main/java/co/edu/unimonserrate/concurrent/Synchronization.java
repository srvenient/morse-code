package co.edu.unimonserrate.concurrent;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class Synchronization {
  private Synchronization() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static void notify(final @NotNull JTextArea textArea, final @NotNull String text) {
    SwingUtilities.invokeLater(() -> textArea.append(text));
  }
}
