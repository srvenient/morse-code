package co.edu.unimonserrate;

import co.edu.unimonserrate.network.ServerChannel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class Bootstrap {
  private Bootstrap() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static void main(final @NotNull String[] args) {
    SwingUtilities.invokeLater(() -> new MonitorViewer(new ServerChannel()));
  }
}
