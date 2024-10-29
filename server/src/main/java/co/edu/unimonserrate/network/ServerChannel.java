package co.edu.unimonserrate.network;

import co.edu.unimonserrate.concurrent.Synchronization;
import co.edu.unimonserrate.network.client.ClientRunnable;
import co.edu.unimonserrate.network.exception.ConnectionFailedException;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ServerChannel implements Channel {
  private final static int PORT = 1313;
  private final CopyOnWriteArrayList<Connection> clients = new CopyOnWriteArrayList<>();
  private ServerSocket serverSocket;
  private int connectedClients = 0;

  public ServerChannel() {
  }

  @Override
  public void onEnable(final @NotNull JTextArea textArea) throws IOException {
    this.serverSocket = new ServerSocket(ServerChannel.PORT);

    Synchronization.notify(textArea, "Server: Started on " + this.serverSocket.getInetAddress().getHostAddress() + ":" + ServerChannel.PORT + "\n");
    Synchronization.notify(textArea, "Server: Waiting for clients...\n");

    new Thread(() -> {
      while (true) {
        final Socket clientSocket;
        try {
          clientSocket = this.serverSocket.accept();
        } catch (final IOException e) {
          throw new ConnectionFailedException("Server: An error occurred while accepting a client", e);
        }

        final var id = clientSocket.getLocalAddress().getHostAddress() + ":" + clientSocket.getPort();
        final var connection = new Connection(id, clientSocket);
        this.add(connection);

        Synchronization.notify(textArea, "Server: Client connected from " + id + "\n");

        new Thread(new ClientRunnable(this, connection, textArea)).start();
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
