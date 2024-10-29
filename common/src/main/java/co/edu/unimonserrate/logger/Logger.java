package co.edu.unimonserrate.logger;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public interface Logger {
  static @NotNull Logger newInstance(final @NotNull JTextArea textArea) {
    return new LoggerImpl(textArea);
  }

  void info(final @NotNull String message);

  void error(final @NotNull String message);

  void warning(final @NotNull String message);
}
