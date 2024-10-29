package co.edu.unimonserrate.network;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public final class Connection {
  private final String id;
  private final BufferedReader reader;
  private final PrintWriter writer;

  public Connection(final @NotNull String id, final @NotNull Socket socket) throws IOException {
    this.id = id;
    this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.writer = new PrintWriter(socket.getOutputStream(), true);
  }

  public @NotNull String id() {
    return this.id;
  }

  public @Nullable String read() throws RuntimeException {
    try {
      return this.reader.readLine();
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void write(final @NotNull String message) {
    this.writer.println(message);
  }

  public void close() {
    try {
      this.reader.close();
      this.writer.close();
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean isClosed() {
    return this.reader == null || this.writer == null;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    final var connection = (Connection) obj;
    return this.id.equals(connection.id);
  }
}
