package co.edu.unimonserrate;

import co.edu.unimonserrate.network.Channel;
import co.edu.unimonserrate.network.ServerChannel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class MonitorViewer extends JFrame {

  public MonitorViewer(final @NotNull Channel channel) {
    this.setTitle("Monitor Viewer");
    this.setSize(800, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

    final var textArea = new JTextArea();
    this.add(new JScrollPane(textArea));

    // Initialize a client with the server channel
    try {
      channel.onEnable(textArea);
    } catch (final Exception e) {
      throw new RuntimeException("An error occurred while starting the server", e);
    }
  }
}
