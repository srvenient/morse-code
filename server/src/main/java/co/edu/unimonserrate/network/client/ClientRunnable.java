package co.edu.unimonserrate.network.client;

import co.edu.unimonserrate.lexer.LogicalTokenizer;
import co.edu.unimonserrate.lexer.Token;
import co.edu.unimonserrate.logger.Logger;
import co.edu.unimonserrate.network.Connection;
import co.edu.unimonserrate.network.ServerChannel;
import co.edu.unimonserrate.parser.ExpressionParser;
import org.jetbrains.annotations.NotNull;

import java.util.Deque;

public class ClientRunnable implements Runnable {
  private final ServerChannel serverChannel;
  private final Connection connection;
  private final Logger logger;

  public ClientRunnable(
    final @NotNull ServerChannel serverChannel,
    final @NotNull Connection connection,
    final @NotNull Logger logger
  ) {
    this.serverChannel = serverChannel;
    this.connection = connection;
    this.logger = logger;
  }

  @Override
  public void run() {
    while (true) {
      try {
        final var message = this.connection.read();
        if (message == null || message.isEmpty()) {
          return;
        }
        if (message.equals("exit")) {
          this.logger.info("[Server] The client " + this.connection.id() + " has disconnected");
          this.serverChannel.remove(this.connection);
          break;
        }
        this.logger.info("[Client]/" + this.connection.id() + "] " + message);
        try {
          final Deque<Token> tokens = LogicalTokenizer.tokenize(message);
          final String parsedMessage = ExpressionParser.parse(tokens);

          this.logger.info("[Server] The server processes the message sent from the client " + this.connection.id());
          this.logger.info("[Server] The message already analyzed : " + parsedMessage);

          for (final var client : this.serverChannel.clients()) {
            if (client == this.connection) {
              continue;
            }
            client.write("[Client/" + this.connection.id() + "] " + parsedMessage + "\n");
          }
        } catch (final IllegalArgumentException e) {
          this.connection.write("[Server] The message you sent could not be processed. Please try again.");
        }
      } catch (final RuntimeException e) {
        this.logger.info("[Server] The client " + this.connection.id() + " has disconnected");
        this.serverChannel.remove(this.connection);
        break;
      }
    }
  }
}
