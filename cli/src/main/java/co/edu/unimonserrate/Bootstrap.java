package co.edu.unimonserrate;

import co.edu.unimonserrate.network.ClientChannelImpl;

import javax.swing.*;

public final class Bootstrap {
  private Bootstrap() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static void main(String[] args) {
    final ClientChannelImpl channel = new ClientChannelImpl("172.19.55.255", 1313);

    try {
      channel.connect();
    } catch (Exception e) {
      throw new RuntimeException("An error occurred while connecting to the server", e);
    }

    SwingUtilities.invokeLater(() -> new ApplicationViewer(channel).setVisible(true));
  }
}
