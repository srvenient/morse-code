package co.edu.unimonserrate;

import co.edu.unimonserrate.network.Channel;
import co.edu.unimonserrate.network.ClientChannel;
import co.edu.unimonserrate.network.Connection;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public final class ApplicationViewer extends JFrame {
  public ApplicationViewer(final @NotNull Channel channel) {
    this.setTitle("Application Viewer");
    this.setSize(800, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

    final var textArea = new JTextArea();
    this.add(new JScrollPane(textArea), BorderLayout.CENTER);

    try {
      channel.onEnable(textArea);
    } catch (final Exception e) {
      throw new RuntimeException("An error occurred while starting the client", e);
    }

    final JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BorderLayout());

    final JTextField inputField = new JTextField();
    inputField.setPreferredSize(new Dimension(0, 30));
    inputField.setFont(new Font("Arial", Font.PLAIN, 16));
    inputPanel.add(inputField, BorderLayout.CENTER);

    final JButton sendButton = new JButton("Enviar");
    inputPanel.add(sendButton, BorderLayout.EAST);

    this.add(inputPanel, BorderLayout.SOUTH);

    final ClientChannel clientChannel = (ClientChannel) channel;
    final Connection connection = clientChannel.connection();
    sendButton.addActionListener(event -> {
      final var text = inputField.getText();
      if (text.isEmpty()) {
        return;
      }

      textArea.append("You: " + text + "\n");
      inputField.setText("");

      connection.write(text);
    });
  }
}
