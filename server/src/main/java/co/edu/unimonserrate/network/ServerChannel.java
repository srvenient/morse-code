package co.edu.unimonserrate.network;

import co.edu.unimonserrate.network.client.ClientRunnable;
import co.edu.unimonserrate.network.exception.ConnectionFailedException;
import co.edu.unimonserrate.util.PrintHelper;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ServerChannel implements Channel{
  private final static int PORT = 1313;
  private final CopyOnWriteArrayList<Connection> clients = new CopyOnWriteArrayList<>();
  private ServerSocket serverSocket;
  private int connectedClients = 0;

  public ServerChannel() {
  }

  @Override
  public void onEnable(final @NotNull JTextArea textArea) throws IOException {
    this.serverSocket = new ServerSocket(PORT);

    PrintHelper.show("Server started on port " + PORT + "\n", textArea);
    PrintHelper.show("Waiting for clients...\n", textArea);

    new Thread(() -> {
      while (true) {
        final Socket clientSocket;
        try {
          clientSocket = this.serverSocket.accept();
        } catch (final IOException e) {
          throw new ConnectionFailedException("An error occurred while accepting a client", e);
        }

        final var id = clientSocket.getLocalAddress().getHostAddress() + ":" + clientSocket.getPort();
        final var connection = new Connection(id, clientSocket);
        this.addClient(connection);

        PrintHelper.show("Client connected from " + id + "\n", textArea);

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
      throw new RuntimeException("An error occurred while closing the server", e);
    }
  }

  public @NotNull CopyOnWriteArrayList<Connection> clients() {
    return this.clients;
  }

  public void addClient(final @NotNull Connection connection) {
    this.clients.add(this.connectedClients, connection);
    this.connectedClients++;
  }

  public void removeClient(final @NotNull Connection connection) {
    this.clients.remove(connection);
    this.connectedClients--;
  }
}
