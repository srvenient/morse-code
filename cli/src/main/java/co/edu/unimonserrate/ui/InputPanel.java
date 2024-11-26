package co.edu.unimonserrate.ui;

import co.edu.unimonserrate.logger.Logger;
import co.edu.unimonserrate.network.Connection;
import co.edu.unimonserrate.sound.SoundReproduce;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class InputPanel extends JPanel {
  public InputPanel(final @Nullable Connection connection, final @NotNull Logger logger) {
    super.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    super.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    final var jTextField = new JTextField();
    jTextField.setPreferredSize(new Dimension(0, 30));
    jTextField.setFont(new Font("Arial", Font.PLAIN, 25));
    ((AbstractDocument) jTextField.getDocument()).setDocumentFilter(new DocumentFilterImpl());

    final var jButton = new JButton("Send");
    jButton.addActionListener(new ButtonActionListener(connection, jTextField, logger));

    jTextField.addActionListener(e -> jButton.doClick());

    super.add(jTextField, BorderLayout.CENTER);
    super.add(jButton, BorderLayout.EAST);
  }

  public record ButtonActionListener(@Nullable Connection connection, @NotNull JTextField jTextField, @NotNull Logger logger) implements ActionListener {
    @Override
    public void actionPerformed(final @NotNull ActionEvent e) {
      if (this.connection == null || this.connection.isClosed()) {
        this.logger.error("[Server] You have no connection to the server");
        return;
      }
      final var text = this.jTextField.getText();
      if (text.isEmpty()) {
        return;
      }
      this.logger.info("You: " + text);
      this.jTextField.setText("");
      this.connection.write(text);
    }
  }

  public static class DocumentFilterImpl extends DocumentFilter {
    @Override
    public void insertString(final @NotNull FilterBypass fb, final int offset, final @NotNull String string, final @NotNull AttributeSet attr) throws BadLocationException {
      if (this.isAllowed(string)) {
        this.playSound(string);
        super.insertString(fb, offset, string, attr);
      }
    }

    @Override
    public void replace(final @NotNull FilterBypass fb, final int offset, final int length, final @NotNull String text, final @NotNull AttributeSet attrs) throws BadLocationException {
      if (this.isAllowed(text)) {
        this.playSound(text);
        super.replace(fb, offset, length, text, attrs);
      }
    }

    private void playSound(final @NotNull String text) {
      for (char c : text.toCharArray()) {
        if (c == '.' || c == '-') {
          try {
            SoundReproduce.reproduce(c);
          } catch (final LineUnavailableException | InterruptedException e) {
            throw new IllegalStateException("Client: An error occurred while reproducing the sound", e);
          }
        }
      }
    }

    private boolean isAllowed(final @NotNull String text) {
      for (char c : text.toCharArray()) {
        if (c != '.' && c != '-' && c != ' ' && c != '/') {
          return false;
        }
      }
      return true;
    }
  }
}
