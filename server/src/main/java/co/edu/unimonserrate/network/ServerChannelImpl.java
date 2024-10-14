package co.edu.unimonserrate.network;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.*;

public final class ServerChannelImpl {
  private final ServerSocket serverSocket;

  private PrintWriter printWriter;
  private BufferedReader bufferedReader;

  public ServerChannelImpl(final int port) {
    try {
      System.out.println("Server is starting...");
      this.serverSocket = new ServerSocket(port);
      System.out.println("Server is running on " + this.serverSocket.getInetAddress().getHostAddress() + ":" + port);
    } catch (final IOException e) {
      throw new RuntimeException("An error occurred while creating the server socket", e);
    }
  }

  public void connect() throws IOException {
    final Socket socket = this.serverSocket.accept();
    socket.setReuseAddress(true);

    this.printWriter = new PrintWriter(socket.getOutputStream(), true);
    this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  public void close() {
    try {
      if (this.serverSocket != null && !this.serverSocket.isClosed()) {
        this.serverSocket.close();
      }
    } catch (final IOException e) {
      throw new RuntimeException("An error occurred while closing the server socket", e);
    }
  }

  public @Nullable String read() {
    try {
      return this.bufferedReader.readLine();
    } catch (IOException e) {
      throw new RuntimeException("An error occurred while reading from the client", e);
    }
  }

  public void write(final @NotNull String message) {
    this.printWriter.println(message);
  }
}
