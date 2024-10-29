package co.edu.unimonserrate.network;

import co.edu.unimonserrate.logger.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public final class ClientChannel implements Channel {
  private Socket socket;
  private Connection connection;

  private final Logger logger;

  public ClientChannel(final @NotNull Logger logger) {
    this.logger = logger;
  }

  @Override
  public void onEnable(final @NotNull String address, final int port) throws IOException {
    this.logger.info("[Server] Connecting to the server on " + address + ":" + port + "...");
    this.socket = new Socket(address, port);
    this.logger.info("[Server] Connection established with the server");
    this.connection = new Connection(UUID.randomUUID().toString(), this.socket);

    new Thread(() -> {
      while (true) {
        try {
          final var message = this.connection.read();
          if (message == null || message.isEmpty()) {
            return;
          }
          this.logger.info(message);
        } catch (final RuntimeException e) {
          Thread.currentThread().interrupt();
          this.logger.error("[Server] Connection to the server has been lost");
        }
      }
    }).start();
  }

  @Override
  public void onDisable() {
    try {
      if (!this.socket.isClosed()) {
        this.socket = null;
        this.connection.write("exit");
        this.connection = null;
        this.logger.info("[Server] Disconnecting from the server...");
      }
    } catch (final NullPointerException e) {
      this.logger.info("[Server] Not connected to the server");
    }
  }

  public @Nullable Connection connection() {
    return this.connection;
  }
}
