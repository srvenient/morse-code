package co.edu.unimonserrate.network;

import co.edu.unimonserrate.logger.Logger;
import co.edu.unimonserrate.network.client.ClientRunnable;
import co.edu.unimonserrate.network.exception.ConnectionFailedException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ServerChannel implements Channel {
  private final static int PORT = 1313;
  private final CopyOnWriteArrayList<Connection> clients = new CopyOnWriteArrayList<>();
  private ServerSocket serverSocket;
  private int connectedClients = 0;

  private final Logger logger;

  public ServerChannel(final @NotNull Logger logger) {
    this.logger = logger;
  }

  @Override
  public void onEnable(final @NotNull String address, final int port) throws IOException {
    this.serverSocket = new ServerSocket(port);

    this.logger.info("[Server] Started on " + this.serverSocket.getInetAddress().getHostAddress() + ":" + port);
    this.logger.info("[Server] Waiting for clients...");

    // Listen for incoming connections
    new Thread(() -> {
      while (true) {
        final Socket clientSocket;
        try {
          clientSocket = this.serverSocket.accept();
        } catch (final IOException e) {
          throw new ConnectionFailedException("An error occurred while accepting a client", e);
        }

        final var id = clientSocket.getLocalAddress().getHostAddress() + ":" + clientSocket.getPort();
        try {
          final Connection connection = new Connection(id, clientSocket);
          this.add(connection);
          this.logger.info("[Server] Client connected from " + id);
          new Thread(new ClientRunnable(this, connection, this.logger)).start();
        } catch (final IOException e) {
          this.logger.error("[Server] An error occurred while connecting to the client");
        }
      }
    })
      .start();
  }

  @Override
  public void onDisable() {
    try {
      this.serverSocket.close();
    } catch (final IOException e) {
      throw new RuntimeException("Server: An error occurred while closing the server", e);
    }
  }

  public @NotNull CopyOnWriteArrayList<Connection> clients() {
    return this.clients;
  }

  public void add(final @NotNull Connection connection) {
    this.clients.add(this.connectedClients, connection);
    this.connectedClients++;
  }

  public void remove(final @NotNull Connection connection) {
    this.clients.remove(connection);
    this.connectedClients--;
  }
}
