package co.edu.unimonserrate.ui;

import co.edu.unimonserrate.network.Channel;
import co.edu.unimonserrate.network.ServerChannel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MainFrame extends JFrame {
  public MainFrame(final int port) {
    super.setTitle("Sever");
    super.setSize(800, 600);
    super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    super.setLocationRelativeTo(null);

    final var outputPanel = new OutputPanel();

    // Connection this server
    final ServerChannel serverChannel = new ServerChannel(outputPanel.logger());
    try {
      serverChannel.onEnable(port);
    } catch (final IOException e) {
      outputPanel.logger().error("[Server] An error occurred while connecting to the server");
      outputPanel.logger().error("[Server] Check if the address or port is correct.");
    }

    super.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(final @NotNull WindowEvent e) {
        final int result = JOptionPane.showConfirmDialog(
          MainFrame.this, "¿Are you sure you want to leave?", "Confirm closing", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
          for (final var client : serverChannel.clients()) {
            client.write(Channel.MESSAGE_CLOSE);
          }
          System.exit(0);
        }
      }
    });

    super.setLayout(new BorderLayout());
    super.add(outputPanel, BorderLayout.CENTER);
  }
}
