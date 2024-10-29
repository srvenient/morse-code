package co.edu.unimonserrate.ui;

import co.edu.unimonserrate.network.ServerChannel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame {
  public MainFrame(final int port) {
    super.setTitle("Sever");
    super.setSize(800, 600);
    super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    super.setLocationRelativeTo(null);

    final var outputPanel = new OutputPanel();

    // Connection this server
    final var channel = new ServerChannel(outputPanel.logger());
    try {
      channel.onEnable(port);
    } catch (final IOException e) {
      outputPanel.logger().error("[Server] An error occurred while connecting to the server");
      outputPanel.logger().error("[Server] Check if the address or port is correct.");
    }

    super.setLayout(new BorderLayout());
    super.add(outputPanel, BorderLayout.CENTER);
  }
}
