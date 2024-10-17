package co.edu.unimonserrate.network.client;

import co.edu.unimonserrate.lexer.LogicalTokenizer;
import co.edu.unimonserrate.network.Connection;
import co.edu.unimonserrate.network.ServerChannel;
import co.edu.unimonserrate.parser.ExpressionParser;
import co.edu.unimonserrate.util.PrintHelper;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientRunnable implements Runnable {
  private final ServerChannel serverChannel;
  private final Connection connection;
  private final JTextArea textArea;

  public ClientRunnable(
    final @NotNull ServerChannel serverChannel,
    final @NotNull Connection connection,
    final @NotNull JTextArea textArea
  ) {
    this.serverChannel = serverChannel;
    this.textArea = textArea;
    this.connection = connection;
  }

  @Override
  public void run() {
    while (true) {
      try {
        final var message = this.connection.read();
        if (message == null || message.isEmpty()) {
          return;
        }

        this.textArea.append("Client " + this.connection.id() + ": " + message + "\n");

        try {
          final var tokens = LogicalTokenizer.tokenize(message);
          final var parsedMessage = ExpressionParser.parse(tokens);

          this.textArea.append("The server processes the message sent from the client " + this.connection.id() + "\n");
          this.textArea.append("Message analyzed: " + parsedMessage + "\n");
          this.textArea.append("The message was broadcasted to all clients\n");

          this.broadcast(parsedMessage);
        } catch (final IllegalArgumentException e) {
          this.connection.write("Server: The message you sent could not be processed. Please try again.");
        }
      } catch (final RuntimeException e) {
        this.textArea.append("The client " + this.connection.id() + " has disconnected\n");
        this.serverChannel.removeClient(this.connection);

        break;
      }
    }
  }

  private void broadcast(final @NotNull String message) {
    for (final var client : this.serverChannel.clients()) {
      if (client == this.connection) {
        continue;
      }

      client.write("Client " + this.connection.id() + ": " + message);
    }
  }
}
