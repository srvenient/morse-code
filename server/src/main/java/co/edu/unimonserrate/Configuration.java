package co.edu.unimonserrate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Configuration {
  private final static String FILE_NAME = "server.properties";
  private final static Path DEFAULT_PATH = Path.of(FILE_NAME);

  public Configuration() {
    if (!Files.exists(DEFAULT_PATH)) {
      try (final BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
        writer.write("port=8080");
        writer.newLine();
        System.out.println("Configuration file created with default settings.");
      } catch (final IOException e) {
        System.err.println("Error creating configuration file: " + e.getMessage());
      }
    }
  }

  public int port() {
    try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        if (line.startsWith("port=")) {
          final var port = Integer.parseInt(line.substring(5));
          if (port < 0 || port > 65535) {
            throw new NumberFormatException();
          }
          return port;
        }
      }
    } catch (final IOException e) {
      System.err.println("An error occurred while reading the configuration file");
      System.exit(1);
    } catch (final NumberFormatException e) {
      System.err.println("The port number must be between 0 and 65535");
      System.exit(1);
    }

    return 8080;
  }
}
