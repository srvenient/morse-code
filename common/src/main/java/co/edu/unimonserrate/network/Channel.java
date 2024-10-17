package co.edu.unimonserrate.network;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;

public interface Channel {
  void onEnable(final @NotNull JTextArea textArea) throws IOException;

  void onDisable();
}
