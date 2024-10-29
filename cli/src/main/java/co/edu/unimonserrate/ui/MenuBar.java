package co.edu.unimonserrate.ui;

import co.edu.unimonserrate.logger.Logger;
import co.edu.unimonserrate.network.ClientChannel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;

public class MenuBar extends JMenuBar {
  private final ClientChannel clientChannel;
  private final MainFrame mainFrame;
  private final Logger logger;

  public MenuBar(final @NotNull Logger logger, final @NotNull MainFrame mainFrame, final @NotNull ClientChannel clientChannel) {
    this.clientChannel = clientChannel;
    this.mainFrame = mainFrame;
    this.logger = logger;

    final var settingsMenu = new JMenu("settings");
    final var ipConfigurationItem = new JMenuItem("IP Configuration");
    final var disconnectItem = new JMenuItem("Disconnect");

    ipConfigurationItem.addActionListener(e -> {
      final var address = JOptionPane.showInputDialog("Enter the IP address");
      var port = JOptionPane.showInputDialog("Enter the port");
      try {
        final var portNumber = Integer.parseInt(port);
        if (portNumber < 0 || portNumber > 65535) {
          throw new NumberFormatException();
        }
        this.reconnect(address, portNumber);
      } catch (final NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "The port number must be between 0 and 65535", "Error", JOptionPane.ERROR_MESSAGE);
        port = JOptionPane.showInputDialog("Enter the port");
        final var portNumber = Integer.parseInt(port);
        if (portNumber < 0 || portNumber > 65535) {
          throw new NumberFormatException();
        }
        this.reconnect(address, portNumber);
      }
    });

    disconnectItem.addActionListener(e -> clientChannel.onDisable());

    settingsMenu.add(ipConfigurationItem);
    settingsMenu.add(disconnectItem);
    super.add(settingsMenu);
  }

  public void reconnect(final @NotNull String address, final int port) {
    try {
      this.clientChannel.onEnable(address, port);
      this.mainFrame.inputPanel(new InputPanel(this.clientChannel.connection(), this.logger));
    } catch (final IOException e) {
      this.logger.error("[Server] An error occurred while connecting to the server");
      this.logger.error("[Server] Check if the address or port is correct.");
    }
  }
}
