package co.edu.unimonserrate.network;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public final class ClientChannelImpl {
  private final Socket socket;

  private PrintWriter printWriter;
  private BufferedReader bufferedReader;

  public ClientChannelImpl(final @NotNull String host, final int port) {
    try {
      this.socket = new Socket(host, port);
    } catch (final IOException e) {
      throw new RuntimeException("An error occurred while creating the client socket", e);
    }
  }

  public void connect() throws IOException {
    this.printWriter = new PrintWriter(this.socket.getOutputStream(), true);
    this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
  }

  public void close() {
    try {
      if (!this.socket.isClosed()) {
        this.socket.close();
      }
    } catch (final IOException e) {
      throw new RuntimeException("An error occurred while closing the client socket", e);
    }
  }

  public @Nullable String read() {
    try {
      return this.bufferedReader.readLine();
    } catch (IOException e) {
      throw new RuntimeException("An error occurred while reading from the server", e);
    }
  }

  public void write(final @NotNull String message) {
    this.printWriter.println(message);
  }
}
