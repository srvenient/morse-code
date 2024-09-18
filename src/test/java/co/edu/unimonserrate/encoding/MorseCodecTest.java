package co.edu.unimonserrate.encoding;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MorseCodecTest {
  @Test
  void encode() {
    final String encoded = MorseCodec.encode("HELLO");
    if (encoded == null) {
      Assertions.fail("The encoded text is null");
    }
    Assertions.assertEquals(".... . .-.. .-.. ---", encoded);
  }

  @Test
  void encodeEmpty() {
    final String encoded = MorseCodec.encode("");
    if (encoded == null) {
      Assertions.fail("The encoded text is null");
    }
    Assertions.assertEquals("", encoded);
  }

  @Test
  void encodeWithNumbers() {
    final String encoded = MorseCodec.encode("HELLO 123");
    if (encoded == null) {
      Assertions.fail("The encoded text is null");
    }
    Assertions.assertEquals(".... . .-.. .-.. --- / .---- ..--- ...--", encoded);
  }

  @Test
  void decode() {
    final String decoded = MorseCodec.decode(".... . .-.. .-.. ---");
    if (decoded == null) {
      Assertions.fail("The decoded text is null");
    }
    Assertions.assertEquals("HELLO", decoded);
  }

  @Test
  void decodeEmpty() {
    final String decoded = MorseCodec.decode("");
    if (decoded == null) {
      Assertions.fail("The decoded text is null");
    }
    Assertions.assertEquals("", decoded);
  }

  @Test
  void decodeWithNumbers() {
    final String decoded = MorseCodec.decode(".... . .-.. .-.. --- / .---- ..--- ...--");
    if (decoded == null) {
      Assertions.fail("The decoded text is null");
    }
    Assertions.assertEquals("HELLO 123", decoded);
  }
}
