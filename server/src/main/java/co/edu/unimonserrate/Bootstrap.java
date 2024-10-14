package co.edu.unimonserrate;

import co.edu.unimonserrate.lexer.LogicalTokenizer;
import co.edu.unimonserrate.lexer.Token;
import co.edu.unimonserrate.network.ServerChannelImpl;
import co.edu.unimonserrate.parser.ExpressionParser;
import org.jetbrains.annotations.NotNull;

import java.util.Deque;

public final class Bootstrap {
  private Bootstrap() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static void main(final @NotNull String[] args) {
    final ServerChannelImpl channel = new ServerChannelImpl(1313);

    try {
      channel.connect();
    } catch (final Exception e) {
      throw new RuntimeException("An error occurred while connecting to the server", e);
    }

    while (true) {
      final String message = channel.read();
      if (message == null) {
        continue;
      }
      final Deque<Token> tokens = LogicalTokenizer.tokenize(message);
      final String decodedMessage = ExpressionParser.parse(tokens);
      if (decodedMessage.isEmpty()) {
        channel.write("No se pudo decodificar el mensaje.");
        continue;
      }
      channel.write(decodedMessage);
    }
  }
}
