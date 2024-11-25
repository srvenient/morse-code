package co.edu.unimonserrate.ui;

import co.edu.unimonserrate.logger.Logger;
import co.edu.unimonserrate.network.ClientChannel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class MenuBar extends JMenuBar {
  public MenuBar(final @NotNull Logger logger, final @NotNull MainFrame mainFrame, final @NotNull ClientChannel clientChannel) {
    final var settingsMenu = new JMenu("settings");
    final var ipConfigurationItem = new JMenuItem("IP Configuration");
    final var disconnectItem = new JMenuItem("Disconnect");

    ipConfigurationItem.addActionListener(e -> {
      final JPanel panel = new JPanel(new GridBagLayout());
      final GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(5, 5, 5, 5);
      gbc.fill = GridBagConstraints.HORIZONTAL;

      final JLabel ipLabel = new JLabel("IP Address:");
      gbc.gridx = 0;
      gbc.gridy = 0;
      panel.add(ipLabel, gbc);

      final JTextField ipField = new JTextField(15);
      gbc.gridx = 1;
      panel.add(ipField, gbc);

      final JLabel portLabel = new JLabel("Port:");
      gbc.gridx = 0;
      gbc.gridy = 1;
      panel.add(portLabel, gbc);

      final JTextField portField = new JTextField(6);
      gbc.gridx = 1;
      panel.add(portField, gbc);

      final int result = JOptionPane.showConfirmDialog(
        null,
        panel,
        "Configure IP and Port",
        JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE
      );

      if (result == JOptionPane.OK_OPTION) {
        try {
          final String address = ipField.getText().trim();
          final String portText = portField.getText().trim();
          final int portNumber = Integer.parseInt(portText);
          if (!this.validateIp(address)) {
            throw new IllegalArgumentException("Invalid IP Address.");
          }
          if (portNumber < 0 || portNumber > 65535) {
            throw new NumberFormatException();
          }
          clientChannel.reload(mainFrame, address, portNumber);
        } catch (final NumberFormatException ex) {
          JOptionPane.showMessageDialog(
            null,
            "The port number must be a valid number between 0 and 65535.",
            "Error",
            JOptionPane.ERROR_MESSAGE
          );
        } catch (final IllegalArgumentException ex) {
          JOptionPane.showMessageDialog(
            null,
            ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
          );
        }
      }
    });

    disconnectItem.addActionListener(e -> clientChannel.onDisable());

    settingsMenu.add(ipConfigurationItem);
    settingsMenu.add(disconnectItem);
    super.add(settingsMenu);
  }

  private boolean validateIp(final @NotNull String ip) {
    final String[] parts = ip.split("\\.");
    if (parts.length != 4) {
      return false;
    }
    for (final String part : parts) {
      try {
        final int value = Integer.parseInt(part);
        if (value < 0 || value > 255) {
          return false;
        }
      } catch (final NumberFormatException e) {
        return false;
      }
    }
    return ip.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b");
  }
}
