package co.edu.unimonserrate.network.exception;

import org.jetbrains.annotations.NotNull;

public class ConnectionFailedException extends RuntimeException {
  public ConnectionFailedException(final @NotNull String message) {
    super(message);
  }

  public ConnectionFailedException(final @NotNull String message, final @NotNull Throwable cause) {
    super(message, cause);
  }
}
