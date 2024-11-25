package co.edu.unimonserrate.ui;

import co.edu.unimonserrate.network.Channel;
import co.edu.unimonserrate.network.ClientChannel;
import co.edu.unimonserrate.network.Connection;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MainFrame extends JFrame {
  private InputPanel inputPanel;

  public MainFrame() {
    super.setTitle("Chat");
    super.setSize(800, 600);
    super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    super.setLocationRelativeTo(null);

    final var outputPanel = new OutputPanel();

    // Connection this server
    final ClientChannel clientChannel = new ClientChannel(outputPanel.logger());
    try {
      clientChannel.onEnable("localhost", 8080);
    } catch (final IOException e) {
      outputPanel.logger().error("[Server] An error occurred while connecting to the server");
      outputPanel.logger().error("[Server] Check if the address or port is correct.");
    }

    final Connection connection = clientChannel.connection();

    super.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(final @NotNull WindowEvent e) {
        final int result = JOptionPane.showConfirmDialog(
          MainFrame.this, "¿Are you sure you want to leave?", "Confirm closing", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
          System.exit(0);
        }
      }
    });

    final var logger = outputPanel.logger();

    this.inputPanel = new InputPanel(connection, logger);
    final var menuBar = new MenuBar(this, clientChannel);

    super.setJMenuBar(menuBar);
    super.setLayout(new BorderLayout());
    super.add(outputPanel, BorderLayout.CENTER);
    super.add(inputPanel, BorderLayout.SOUTH);
  }

  public void inputPanel(final @NotNull InputPanel inputPanel) {
    super.remove(this.inputPanel);

    this.inputPanel = inputPanel;

    super.add(this.inputPanel, BorderLayout.SOUTH);

    super.revalidate();
    super.repaint();
  }
}
