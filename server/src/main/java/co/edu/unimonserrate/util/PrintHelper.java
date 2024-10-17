package co.edu.unimonserrate.util;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class PrintHelper {
  private PrintHelper() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static void show(final @NotNull String message, final @NotNull JTextArea textArea) {
    SwingUtilities.invokeLater(() -> textArea.append(message));
  }
}
