package co.edu.unimonserrate.network;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface Channel {
  void onEnable(final @NotNull String address, final int port) throws IOException;

  default void onEnable(final int port) throws IOException {
    this.onEnable("", port);
  }

  void onDisable();
}
