package co.edu.unimonserrate.logger;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class LoggerImpl implements Logger {
  private final JTextArea textArea;

  public LoggerImpl(final @NotNull JTextArea textArea) {
    this.textArea = textArea;
  }

  @Override
  public void info(final @NotNull String message) {
    this.textArea.append("[INFO]: " + message + "\n");
  }

  @Override
  public void error(final @NotNull String message) {
    this.textArea.append("[ERROR]: " + message + "\n");
  }

  @Override
  public void warning(final @NotNull String message) {
    this.textArea.append("[WARNING]: " + message + "\n");
  }
}
