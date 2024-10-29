package co.edu.unimonserrate;

import co.edu.unimonserrate.network.Channel;
import co.edu.unimonserrate.network.ServerChannel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public final class MonitorViewer extends JFrame {
  public MonitorViewer(final @NotNull Channel channel) {
    super.setTitle("Monitor Viewer");
    super.setSize(800, 600);
    super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    super.setLocationRelativeTo(null);
    super.setVisible(true);

    final var textArea = new JTextArea();
    textArea.setEditable(false);
    textArea.setBackground(new Color(240, 240, 240));
    textArea.setForeground(new Color(33, 33, 33));
    textArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setBorder(
      BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
        BorderFactory.createEmptyBorder(15, 15, 15, 15)
      )
    );

    final var scrollPane = new JScrollPane(textArea);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());

    super.add(scrollPane, BorderLayout.CENTER);

    try {
      channel.onEnable(textArea);
    } catch (final Exception e) {
      throw new RuntimeException("An error occurred while starting the server", e);
    }
  }
}
