package co.edu.unimonserrate;

import co.edu.unimonserrate.network.ClientChannelImpl;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class ApplicationViewer extends JFrame {
  private final ClientChannelImpl channel;

  public ApplicationViewer(final @NotNull ClientChannelImpl channel) {
    this.channel = channel;
  }
}
