package co.edu.unimonserrate.sound;

import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.*;

public final class SoundReproduce {
  private SoundReproduce() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static void reproduce(final char note) throws LineUnavailableException, InterruptedException {
    final AudioFormat audioFormat = new AudioFormat(44100, 16, 1, true, false);
    final DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
    final SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);

    sourceDataLine.open(audioFormat);
    sourceDataLine.start();

    final int ditDuration = 100;
    final int dahDuration = ditDuration * 3;

    switch (note) {
      case '.' -> {
        SoundReproduce.beep(sourceDataLine, ditDuration);
        Thread.sleep(ditDuration);
      }
      case '-' -> {
        SoundReproduce.beep(sourceDataLine, dahDuration);
        Thread.sleep(dahDuration);
      }
      default -> throw new IllegalArgumentException("Invalid note: " + note);
    }

    sourceDataLine.drain();
    sourceDataLine.stop();
    sourceDataLine.close();
  }

  private static void beep(final @NotNull SourceDataLine sourceDataLine, int duration) {
    final float sampleRate = 44100.0f;
    final double frequency = 440.0;
    final int frameSize = 2;
    final int numFrames = (int) (sampleRate * duration / 1000.0);
    final byte[] buffer = new byte[numFrames * frameSize];

    for (int i = 0; i < numFrames; i++) {
      final double angle = i / (sampleRate / frequency) * 2.0 * Math.PI;
      final short sample = (short) (Math.sin(angle) * Short.MAX_VALUE);
      buffer[2 * i] = (byte) (sample & 0xFF);
      buffer[2 * i + 1] = (byte) ((sample >> 8) & 0xFF);
    }

    sourceDataLine.write(buffer, 0, buffer.length);
  }
}
