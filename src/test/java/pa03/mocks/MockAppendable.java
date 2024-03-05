package pa03.mocks;

import java.io.IOException;

/**
 * Mocks appendable class to throw IOException for testing
 */
public class MockAppendable implements Appendable {
  /**
   * throws IOException for exception testing
   *
   * @param csq
   *         The character sequence to append.  If {@code csq} is
   *         {@code null}, then the four characters {@code "null"} are
   *         appended to this Appendable.
   *
   * @return null
   * @throws IOException always
   */
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throwInOut();
    return null;
  }

  /**
   * throws IOException for exception testing
   *
   * @param csq
   *         The character sequence from which a subsequence will be
   *         appended.  If {@code csq} is {@code null}, then characters
   *         will be appended as if {@code csq} contained the four
   *         characters {@code "null"}.
   *
   * @param start
   *         The index of the first character in the subsequence
   *
   * @param end
   *         The index of the character following the last character in the
   *         subsequence
   *
   * @return null
   * @throws IOException always
   */
  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throwInOut();
    return null;
  }

  /**
   * throws IOException for exception testing
   *
   * @param c
   *         The character to append
   *
   * @return null
   * @throws IOException always
   */
  @Override
  public Appendable append(char c) throws IOException {
    throwInOut();
    return null;
  }

  /**
   * throwing an exception for testing purposes
   *
   * @throws IOException always
   */
  private void throwInOut() throws IOException {
    throw new IOException("Mock throwing an error");
  }
}
