package co.edu.unimonserrate;

import co.edu.unimonserrate.ui.MainFrame;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class Bootstrap {
  private Bootstrap() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static void main(final @NotNull String[] args) {
    final Configuration configuration = new Configuration();
    final int port = configuration.port();
    SwingUtilities.invokeLater(() -> {
      final var mainFrame = new MainFrame(port);
      mainFrame.setVisible(true);
    });
  }
}
