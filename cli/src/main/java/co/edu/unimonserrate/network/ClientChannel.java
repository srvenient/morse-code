package co.edu.unimonserrate.network;

import co.edu.unimonserrate.network.exception.ConnectionFailedException;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.UUID;

public final class ClientChannel implements Channel {
  private Socket socket;

  private final String host;
  private final int port;

  private Connection connection;

  public ClientChannel(final @NotNull String host, final int port) {
    this.host = host;
    this.port = port;
  }

  @Override
  public void onEnable(final @NotNull JTextArea textArea) {
    try {
      textArea.append("Client: Connecting to the server...\n");
      this.socket = new Socket(this.host, this.port);
      textArea.append("Client: Connected to the server on " + this.host + ":" + this.port + "\n");
      this.connection = new Connection(UUID.randomUUID().toString(), this.socket);

      new Thread(() -> {
        while (true) {
          try {
            final var message = this.connection.read();
            if (message == null || message.isEmpty()) {
              return;
            }
            textArea.append(message + "\n");
          } catch (final RuntimeException e) {
            textArea.append("Client: An error occurred while reading the message\n");
          }
        }
      }).start();
    } catch (final IOException e) {
      textArea.append("Client: An error occurred while connecting to the server\n");
      throw new ConnectionFailedException("Client: An error occurred while connecting to the server", e);
    }
  }

  @Override
  public void onDisable() {
    try {
      if (!this.socket.isClosed()) {
        this.socket.close();
        this.connection.close();
      }
    } catch (final IOException e) {
      throw new RuntimeException("Client: An error occurred while closing the client socket", e);
    }
  }

  public @NotNull Connection connection() {
    return this.connection;
  }
}
