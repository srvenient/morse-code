package co.edu.unimonserrate.ui;

import co.edu.unimonserrate.logger.Logger;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class OutputPanel extends JPanel {
  private final Logger logger;

  public OutputPanel() {
    super(new BorderLayout());
    super.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    super.setSize(800, 600);

    final var textArea = new JTextArea();
    textArea.setEditable(false);
    textArea.setBackground(new Color(240, 240, 240));
    textArea.setForeground(new Color(33, 33, 33));
    textArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
      BorderFactory.createEmptyBorder(15, 15, 15, 15)));

    final var scrollPane = new JScrollPane(textArea);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());

    super.add(scrollPane, BorderLayout.CENTER);

    this.logger = Logger.newInstance(textArea);
  }

  public @NotNull Logger logger() {
    return this.logger;
  }
}
